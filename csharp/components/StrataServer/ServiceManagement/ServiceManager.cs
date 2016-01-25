using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Reflection;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.ServiceModel.Description;
using Strata.Common.ExceptionManagement;
using Strata.Common.Exceptions;
using Strata.Common.Logging;
using Strata.Common.Provisioning;
using Strata.Common.Unity;
using Strata.Common.Utility;
using Strata.WcfIntegration;
using Microsoft.Practices.ServiceLocation;
using Microsoft.Practices.Unity;
using Microsoft.Practices.Unity.InterceptionExtension;

namespace Strata.Server.ServiceManagement
{
    /// <summary>
    /// Manages the services hosted by the service host.
    /// </summary>
    public class ServiceManager : IServiceManager
    {
        private const string ExceptionPolicy = "ExceptionPolicy";
        public const string ServiceLocalPath = "ServicesLocalPath";
        public const string ServiceLocalPathDefault = @"";
        public const string ServiceRepositoryPath = "ServicesRepositoryPath";
        public const string ServiceRepositoryPathDefault = @"..\..\..\..\Services\LocalRepository\";

        private readonly Dictionary<string, ServiceHost> serviceHosts = new Dictionary<string, ServiceHost>();
        private readonly Dictionary<string, IService> services = new Dictionary<string, IService>();

        /// <summary>
        /// A collection of <see cref="IService"/>s keyed by service name which are currently recognized by the <see cref="ServiceManager"/>.
        /// </summary>
        public Dictionary<string, IService> Services { get { return services; } }

        /// <summary>
        /// The library provisioner for the service host.  Used for provisioning libraries from the repository.
        /// </summary>
        [Dependency]
        public LibraryProvisioner Provisioner { get; set; }

        /// <summary>
        /// The application <see cref="ILogger"/> provided by the container.
        /// </summary>
        [Dependency]
        public ILogger Log { get; set; }

        /// <summary>
        /// The application <see cref="IExceptionManager"/> provided by the container.
        /// </summary>
        [Dependency]
        public IExceptionManager ExceptionManager { get; set; }

        /// <summary>
        /// Creates a new <see cref="ServiceManager"/> instance.
        /// </summary>
        public ServiceManager()
        {
            RegisterTypeMappings();
        }
               
        /// <summary>
        /// Performs the initial startup of the <see cref="ServiceManager"/>.  The paths and filter of the <see cref="LibraryProvisioner"/> are
        /// configured and Refresh is called.  All recognized services are started, and then a handler for library updates is attached.
        /// </summary>
        public void Start()
        {
            ThreadTools.SetThreadName(Properties.Resources.ServiceManagerThread);

            // Configure the provisioner.
            Provisioner.LocalPath = ConfigurationManager.AppSettings[ServiceLocalPath] ?? ServiceLocalPathDefault;
            Provisioner.RepositoryPath = ConfigurationManager.AppSettings[ServiceRepositoryPath] ?? ServiceRepositoryPathDefault;

            // Initial scanning of the directory and provisioning of new/updated services.
            Refresh();

            // Start services.  This uses the initialized list of all services here so we start everything up once.
            InitializeServices(Provisioner.Libraries);

            // Listen to changes to services in the service provisioner.
            Provisioner.LibrariesChanged += Provisioner_LibrariesChanged;
        }

        /// <summary>
        /// Attempts to Stop(), then ForceStop() all executing services.
        /// </summary>
        public void Stop()
        {
            Log.Stop(Properties.Resources.ServiceManagerStoppingServices);

            foreach (IService service in services.Values)
            {
                StopService(service);
            }

            Log.Stop(Properties.Resources.ServiceManagerStoppedServices);
        }

        /// <summary>
        /// Refreshes services against the repository.
        /// </summary>
        public void Refresh()
        {
            Provisioner.Refresh();
        }

        private void RegisterTypeMappings()
        {
        }

        /// <summary>
        /// Handles the ServiceProvisioner's ServiceFilesChanged event.  Sends the ChangedFiles to the StartServices method.
        /// </summary>
        /// <param name="args"></param>
        private void Provisioner_LibrariesChanged(LibrariesChangedArgs args)
        {
            InitializeServices(args.ChangedLibraries);
        }

        /// <summary>
        /// Starts each service in the services collection given.  Each service is started in an ExceptionManager protected process.
        /// </summary>
        /// <param name="libraries">The collection of libraries to examine and start, if possible.</param>
        private void InitializeServices(IEnumerable<LibraryInfo> libraries)
        {
            List<IService> newServices = new List<IService>();

            foreach (var libraryInfo in libraries
                // Only look in binaries for services.
                .Where(l => (l.FileName.ToLowerInvariant().EndsWith(".dll") || l.FileName.ToLowerInvariant().EndsWith(".exe"))))
            {
                string version = libraryInfo.FileVersion;
                foreach (Type serviceType in Assembly.LoadFrom(libraryInfo.LocalPath).GetTypes()
                    .Where(type => type.GetInterfaces()
                                       .Contains(typeof (IService))))
                {
                    var service = ServiceLocator.Current.GetInstance(serviceType) as IService;
                    if (service == null)
                    {
                        Log.Error(string.Format(Properties.Resources.ErrorCannotCastService, serviceType.Name));
                        continue;
                    }

                    service.Version = version;

                    // If the new service already exists and is either running or hosting a WCF endpoint, stop it and replace it.
                    if (services.ContainsKey(service.Name))
                    {
                        if (serviceHosts[service.Name].State == CommunicationState.Opened ||
                            serviceHosts[service.Name].State == CommunicationState.Opening)
                        {
                            Log.Verbose(string.Format(Properties.Resources.WarnStoppingRunningService, service.Name));
                            StopService(services[service.Name]);
                        }
                    }

                    ExceptionManager.Process(() =>
                                             {
                                                 service.Initialize();
                                                 services[service.Name] = service;
                                                 newServices.Add(service);
                                             }, ExceptionPolicy);
                }
            }

            foreach (IService service in newServices)
            {
                IService localService = service;
                ExceptionManager.Process(() => StartService(localService), ExceptionPolicy);
            }
        }

        /// <summary>
        /// Instantiates the service from the container and runs the service's Initialize method.  Any 
        /// non-service-oriented activities the service will perform should be implemented here.  The service will also be hosted 
        /// via WCF if possible.
        /// </summary>
        /// <param name="service">The <see cref="IService"/> to start.</param>
        private void StartService(IService service)
        {
            // Activate the service.
            service.Start();

            // Hosting the service via WCF Endpoint, if necessary.
            if (service.GetType().GetCustomAttributes(typeof(WcfEndpointAttribute), false).Length > 0)
            {
                HostWcfEndpoint(service);
            }
        }


        /// <summary>
        /// Hosts the service as a WCF endpoint using the service's provided WCF details.
        /// </summary>
        /// <param name="service">The service to host.</param>
        private void HostWcfEndpoint(IService service)
        {
            if (service == null) return;

            // Retrieve the custom WcfEndpoingAttribute from the service's Type.
            WcfEndpointAttribute wcfAttribute = service.GetType().GetCustomAttributes(false).OfType<WcfEndpointAttribute>().FirstOrDefault();
            if (wcfAttribute == null)
            {
                throw new AttributeNotFoundException("Capgroup.IDS.Infrastructure.Services.WcfEndpointAttribute");
            }

            ContainerFactory.Current.TryRegisterType(wcfAttribute.ContractType, service.GetType());

            var tcpUri = new Uri(string.Format(wcfAttribute.BaseAddress, "net.tcp", "9000") + service.Name);
            var httpUri = new Uri(string.Format(wcfAttribute.BaseAddress, "http", "8080") + service.Name);
            
            ServiceHost host = null;
            
            if (wcfAttribute.CustomTransferMode == TransferMode.Buffered)
            {
                host = new ServiceHost(service.GetType(), httpUri, tcpUri);
                host.AddServiceEndpoint(wcfAttribute.ContractType, new WSHttpBinding(), string.Empty);
                host.AddServiceEndpoint(wcfAttribute.ContractType, new NetTcpBinding(), string.Empty);

                // Allows automated configuration of service client via Visual Studio.
                var metadataBehavior = new ServiceMetadataBehavior
                {
                    HttpGetEnabled = true
                };
                host.Description.Behaviors.Add(metadataBehavior);
                host.AddServiceEndpoint(ServiceMetadataBehavior.MexContractName, MetadataExchangeBindings.CreateMexTcpBinding(), tcpUri + "/mex");
            }
            else
            {
                host = new ServiceHost(service.GetType(), httpUri, tcpUri);
                var tcpBinding = new CustomBinding(new NetTcpBinding());
                var tcpTransportElement = tcpBinding.Elements.Find<TcpTransportBindingElement>();
                tcpTransportElement.TransferMode = wcfAttribute.CustomTransferMode;
                tcpTransportElement.MaxReceivedMessageSize = 104857600; //100 MB

                // Allows automated configuration of service client via Visual Studio.
                var metadataBehavior = new ServiceMetadataBehavior
                {
                    HttpGetEnabled = true
                };
                host.Description.Behaviors.Add(metadataBehavior);
                host.AddServiceEndpoint(wcfAttribute.ContractType, tcpBinding, tcpUri);
            }

            // Return exception details to the client.
            var debugBehavior = host.Description.Behaviors.Find<ServiceDebugBehavior>();
            debugBehavior.IncludeExceptionDetailInFaults = true;

            // Register the service contract for interception.
            ContainerFactory.Current.RegisterType(wcfAttribute.ContractType,
                                                  new Interceptor<TransparentProxyInterceptor>(),
                                                  new InterceptionBehavior<PolicyInjectionBehavior>());

            host.Open();

            serviceHosts[service.Name] = host;

            Log.Info(string.Format(Properties.Resources.ServiceManagerWCFHosted, httpUri));
            Log.Info(string.Format(Properties.Resources.ServiceManagerWCFHosted, tcpUri));
        }

        /// <summary>
        /// Attempts to Stop(), then ForceStop() the given service.
        /// </summary>
        /// <param name="service">The service to kill.</param>
        private void StopService(IService service)
        {
            if (!service.Stop())
            {
                service.ForceStop();
            }

            if (!serviceHosts.ContainsKey(service.Name)) return;

            ServiceHost host = serviceHosts[service.Name];
            if (host.State != CommunicationState.Opened && host.State != CommunicationState.Opening) return;

            Log.Verbose(string.Format(Properties.Resources.ServiceManagerWCFClosing, service.Name));

            host.Close();

            Log.Verbose(string.Format(Properties.Resources.ServiceManagerWCFClosed, service.Name));
        }
    }
}
