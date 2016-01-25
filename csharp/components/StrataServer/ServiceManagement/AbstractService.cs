using System.Threading;
using Strata.Common.ExceptionManagement;
using Strata.Common.Logging;
using Microsoft.Practices.Unity;

namespace Strata.Server.ServiceManagement
{
    /// <summary>
    /// An abstract, basic implementation of IService to facilitate getting started on the service <c>.
    /// </summary>
    public abstract class AbstractService : IService
    {
        /// <summary>
        /// The main execution thread for this service.  Started when Start() is called, and can be Abort()ed by ForceStop().
        /// </summary>
        protected Thread mainThread;

        /// <summary>
        /// The <see cref="ILogger"/> provided by the container during resolution.
        /// </summary>
        [Dependency]
        public ILogger Log { get; set; }

        /// <summary>
        /// The <see cref="IExceptionManager"/> provided by the container during resolution.
        /// </summary>
        [Dependency]
        public IExceptionManager ExceptionManager { get; set; }

        /// <summary>
        /// The <see cref="IUnityContainer"/> reference to the application container.
        /// </summary>
        [Dependency]
        public IUnityContainer Container { get; set; }

        /// <summary>
        /// Starts the service on a background thread.  If the service is already running, this method does nothing.  The name of the
        /// service will be the name of the background thread.
        /// </summary>
        public void Start()
        {
            // Don't do anything if it's already running.
            if (mainThread != null && mainThread.IsAlive)
            {
                Log.Verbose("Service already running.");
                return;
            }

            mainThread = new Thread(Run) { Name = Name };
            mainThread.Start();
        }

        /// <summary>
        /// The main execution point of the service.
        /// </summary>
        protected abstract void Run();

        /// <summary>
        /// Abstract placeholder for the interface method.
        /// </summary>
        public abstract void Initialize();

        /// <summary>
        /// Gracefully stops the service.
        /// </summary>
        /// <returns></returns>
        public virtual bool Stop()
        {
            return true;
        }

        /// <summary>
        /// Forces exection of this service's mainthread to end by calling Abort() on the thread if it IsAlive.
        /// </summary>
        public void ForceStop()
        {
            if (mainThread.IsAlive)
            {
                mainThread.Abort();
            }
        }

        /// <summary>
        /// The name of the service.
        /// </summary>
        /// <remarks>
        /// This name is used as the key in the ServiceManager's dictionary of services, for ease in finding it to start, stop, etc.
        /// </remarks>
        public abstract string Name { get; }

        /// <summary>
        /// Service's version
        /// </summary>
        public string Version { get; set; }
    }
}
