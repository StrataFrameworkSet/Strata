using System;
using System.ServiceProcess;
using Strata.Common.ExceptionManagement;
using Strata.Common.Logging;
using Strata.Common.Provisioning.Providers;
using Strata.Server.ServiceManagement;
using Microsoft.Practices.ServiceLocation;
using Microsoft.Practices.Unity;

namespace Strata.Server
{
    /// <summary>
    /// This class serves to act as the Windows service execution, and also handles coordinating the bootstrapping 
    /// of IDS services and then initializing and hosting them via the ServiceManager.
    /// </summary>
    public partial class ServerService : ServiceBase
    {
        private const string ExceptionPolicy = "ExceptionPolicy";

        /// <summary>
        /// The <see cref="IUnityContainer"/> dependency, providing dependency injection and inversion of control for the service manager.
        /// </summary>
        [Dependency]
        public IUnityContainer UnityContainer { get; set; }

        /// <summary>
        /// The exception manager dependency, providing standard exception handling features.
        /// </summary>
        [Dependency]
        public IExceptionManager ExceptionManager { get; set; }

        /// <summary>
        /// The logger dependency, providing standard logging features.
        /// </summary>
        [Dependency]
        public ILogger Log { get; set; }

        /// <summary>
        /// Creates an new <see cref="ServerService"/> instance.
        /// </summary>
        public ServerService()
        {
            InitializeComponent();
        }

        #region Starting the Service

        #region Debugging Aide

#if DEBUG

        #region Console Commands

        /// <summary>
        /// The command for exiting the application.
        /// </summary>
        public const string EXIT = "EXIT";

        /// <summary>
        /// The command to test refreshing services.
        /// </summary>
        public const string REFRESH = "REFRESH";

        #endregion

        /// <summary>
        /// Provides access to the OnStart() method (which cannot be made public due to its inheritance) for use by the
        /// Program class when run in Debug mode only.  Provided for debugging purposes.
        /// </summary>
        public void Debug()
        {
            OnStart(null);

            Log.Warn(Properties.Resources.ConsoleWarnDebugMode);

            // Command prompt loop.
            bool keepLooping = true;
            while (keepLooping)
            {
                Console.Write(Properties.Resources.ConsoleCommandPrompt);

                string command = Console.ReadLine();

                if (!string.IsNullOrEmpty(command))
                {
                    Console.WriteLine(string.Format(Properties.Resources.ConsoleEnteredCommand, command));
                }
                else
                {
                    continue;
                }

                switch (command.ToUpperInvariant())
                {
                    case REFRESH:
                        var serviceManager = ServiceLocator.Current.GetInstance<IServiceManager>();
                        if (serviceManager == null)
                        {
                            Console.WriteLine(Properties.Resources.ConsoleErrorServiceManagerNull);
                            continue;
                        }

                        Console.WriteLine(Properties.Resources.ConsoleRefreshingServices);
                        ExceptionManager.Process(serviceManager.Refresh, ExceptionPolicy);
                        Console.WriteLine(Properties.Resources.ConsoleRefreshedServices);
                        break;
                    case EXIT:
                        Console.WriteLine(Properties.Resources.ConsoleExitting);
                        keepLooping = false;
                        break;
                    default:
                        Console.WriteLine(string.Format(Properties.Resources.ConsoleHelp, command));
                        continue;
                }
            }

            OnStop();
        }

#endif

        #endregion

        #region Unit Test Aide

        /// <summary>
        /// Calls OnStart with null arguments.
        /// </summary>
        /// <remarks>
        /// To be used only by the Unit Test project because OnStart where our Windows
        /// Service starts our <c> is a protected method.
        /// </remarks>
        public void TestStart()
        {
            OnStart(null);
        }

        #endregion

        /// <summary>
        /// The primary entry point; called by the Debugging assistance <c> as well as when Start is called for the Windows Service.
        /// </summary>
        /// <param name="args">An array of string arguments passed when running the service/console.</param>
        protected override void OnStart(string[] args)
        {
            Log.Start(Properties.Resources.SystemStarted);

            try
            {
                // Show the ASCII splash with copyright info for the log.
                var copyright = ServiceLocator.Current.GetInstance<Copyright>();
                copyright.Display(true);

                // Register the server types.
                RegisterTypeMappings();

                var serviceManager = ServiceLocator.Current.GetInstance<IServiceManager>();
                serviceManager.Start();
            }
            catch (Exception ex)
            {
                ExceptionManager.Process(() => { throw ex; }, ExceptionPolicy);
            }
        }

        private void RegisterTypeMappings()
        {
            UnityContainer.RegisterType<ITargetFoldersProvider, DbServicesTargetFoldersProvider>();
            UnityContainer.RegisterType<IServiceManager, ServiceManager>(new ContainerControlledLifetimeManager());
            //UnityContainer.RegisterType<IServiceMessageFactory, ServiceMessageFactory>();
            //UnityContainer.RegisterType<ISecurityProvider, ActiveDirectorySecurityProvider>();
            //UnityContainer.RegisterType<IUnitOfWork<ApplicationEntities>, UnitOfWork<ApplicationEntities>>();
            //UnityContainer.RegisterType<IRepository<ApplicationEntities>, Repository<ApplicationEntities>>();
        }

        #endregion

        #region Stopping the Service

        #region Unit Test Aide

        /// <summary>
        /// Calls OnStop with no arguments.
        /// </summary>
        /// <remarks>
        /// To be used only by the Unit Test project because OnStop where our Windows
        /// Service stops our <c> is a protected method.
        /// </remarks>
        public void TestStop()
        {
            OnStop();
        }

        #endregion

        /// <summary>
        /// Attempts to gracefully shut down the bootstrapper.
        /// </summary>
        protected override void OnStop()
        {
            Log.Stop(Properties.Resources.SystemStopped);
        }

        #endregion

    }
}
