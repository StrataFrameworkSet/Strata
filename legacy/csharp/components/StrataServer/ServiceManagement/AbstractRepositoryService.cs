namespace Strata.Server.ServiceManagement
{
    /// <summary>
    /// An abstract service
    /// </summary>
    public class AbstractRepositoryService : IService
    {
        #region Implementation of IService

        /// <summary>
        /// This method is called to invoke any pre-processing logic the service might require.  Things like interface mappings for the container
        /// that other services may need during their own startup would go here.
        /// </summary>
        /// <example>
        /// The AutoSys service is an implementation of the IJobLauncher API and Tracking Error implements IScheduledJob.  In order for Tracking Error
        /// to attach to AutoSys at startup using IJobLauncher, AutoSys must map its IJobLauncher implementation in the container first.  This mapping is
        /// done in <see cref="IService.Initialize"/> while Tracking Error attaches inside the <see cref="IService.Start"/> implementation.
        /// </example>
        public void Initialize()
        {
            
        }

        /// <summary>
        /// Starts any background threads this service executes.
        /// </summary>
        public void Start()
        {
        }

        /// <summary>
        /// Stops any background threads the service is running, and shuts down any endpoints the service may be hosting.
        /// </summary>
        /// <returns></returns>
        public bool Stop()
        {
            return true;
        }

        /// <summary>
        /// Forcibly stops the service if the Stop does not complete in a timely fashion or returns an error.
        /// </summary>
        public void ForceStop()
        {
        }

        /// <summary>
        /// The name of the service.
        /// </summary>
        public string Name { get; private set; }

        /// <summary>
        /// The version retrieved from the service dll file by the LibraryProvisioner
        /// </summary>
        public string Version { get; set; }

        #endregion
    }
}
