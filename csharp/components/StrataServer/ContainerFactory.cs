using System;
using System.Diagnostics;
using Strata.Common.ExceptionManagement;
using Strata.Common.Logging;
using Microsoft.Practices.EnterpriseLibrary.Common.Configuration;
//using Microsoft.Practices.EnterpriseLibrary.Common.Configuration.ContainerModel.Unity;
using Microsoft.Practices.ServiceLocation;
using Microsoft.Practices.Unity;
using Microsoft.Practices.Unity.InterceptionExtension;

namespace Strata.Server
{
    /// <summary>
    /// A factory class for creating and configuring the core features of the <see cref="UnityContainer"/> specific to X-Wing.
    /// </summary>
    public static class ContainerFactory
    {
        /// <summary>
        /// Provides static access to the existing <see cref="IUnityContainer"/> provided by this factory.
        /// </summary>
        public static IUnityContainer Current { get; private set; }

        private static object syncRoot = new object();
        private static bool isConfigured;

        /// <summary>
        /// Creates the core <see cref="UnityContainer"/> and configures it with any Enterprise Library settings contained in 
        /// the applications configuration file.
        /// </summary>
        /// <returns>An <see cref="IUnityContainer"/> configured for use with the Enterprise Library.</returns>
        public static IUnityContainer CreateContainer()
        {
            ValidateFirstRun();

            Trace.WriteLine(Properties.Resources.UnityCreatingContainer);

            var container = new UnityContainer();

            Trace.WriteLine(Properties.Resources.UnityCreatedContainer);
            Trace.WriteLine(Properties.Resources.UnityConfiguringContainer);

            Trace.WriteLine(Properties.Resources.UnityConfiguringEntLib);

            /* 
             * Does not exist in Enterprise Libary 6: need to refactor
            EnterpriseLibraryContainer.ConfigureContainer(new UnityContainerConfigurator(container), ConfigurationSourceFactory.Create());
            */
            Trace.WriteLine(Properties.Resources.UnityConfiguredEntLib);

            RegisterTypeMappings(container);

            Trace.WriteLine(Properties.Resources.UnityAddingInterceptionExtension);

            container.AddNewExtension<Interception>();

            Trace.WriteLine(Properties.Resources.UnityAddedExtension);
            Trace.WriteLine(Properties.Resources.UnityConfiguringInterception);

            ////container.Configure<Interception>()
            //    .AddPolicy(InterceptionPolicies.Metrics)
            //    .AddMatchingRule<TagAttributeMatchingRule>(
            //        new InjectionConstructor(InterceptionPolicies.Metrics))
            //    .AddCallHandler<MetricsCallHandler>(
            //        new InjectionProperty("Log", container.Resolve<ILogger>()),
            //        new InjectionProperty("Order", (int)InterceptionOrder.Metrics));

            //Trace.WriteLine(string.Format(Resources.UnityAddedPolicy, InterceptionPolicies.Metrics));

            //container.Configure<Interception>()
            //    .AddPolicy(InterceptionPolicies.Audit)
            //    .AddMatchingRule<TagAttributeMatchingRule>(
            //        new InjectionConstructor(InterceptionPolicies.Audit))
            //    .AddCallHandler<AuditCallHandler>(
            //        new InjectionProperty("Log", container.Resolve<ILogger>()),
            //        new InjectionProperty("Order", (int)InterceptionOrder.Audit));

            //Trace.WriteLine(string.Format(Resources.UnityAddedPolicy, InterceptionPolicies.Audit));

            Trace.WriteLine(Properties.Resources.UnityConfiguredInterception);
            Trace.WriteLine(Properties.Resources.UnityConfiguringContainer);

            Current = container;
            ServiceLocator.SetLocatorProvider(() => new UnityServiceLocator(container));
            return container;
        }

        /// <summary>
        /// The custom type mapping strategy this factory should apply when creating the container.  Any mappings contained
        /// within this strategy will be applied AFTER the default mappings included by the container and this factory.
        /// </summary>
        public static Action<IUnityContainer> CustomTypeMappingStrategy { get; set; }

        private static void RegisterTypeMappings(IUnityContainer container)
        {
            container.RegisterType<ILogger, DefaultLogger>();
            Trace.WriteLine(Properties.Resources.InterfaceMappedLogger);

            container.RegisterType<IExceptionManager, ExceptionManagerFacade>();
            Trace.WriteLine(Properties.Resources.InterfaceMappedExceptionManager);

            //container.RegisterType<IServiceMessageFactory, ServiceMessageFactory>();
            //Trace.WriteLine(Resources.InterfaceMappedServiceMessageFactory);
 
            //container.RegisterType<ISecurityProvider, ActiveDirectorySecurityProvider>();
            //Trace.WriteLine(Resources.InterfaceMappedSecurityProvider);

            if (CustomTypeMappingStrategy != null)
            {
                CustomTypeMappingStrategy(container);
            }
        }

        private static void ValidateFirstRun()
        {
            if (isConfigured) throw new InvalidOperationException(Properties.Resources.ErrorContainerFactoryCreateOnlyOnce); ;

            lock (syncRoot)
            {
                if (isConfigured)
                {
                    throw new InvalidOperationException(Properties.Resources.ErrorContainerFactoryCreateOnlyOnce);
                }

                isConfigured = true;
            }
        }
    }
}
