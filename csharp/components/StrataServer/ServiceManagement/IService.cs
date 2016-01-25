namespace Strata.Server.ServiceManagement
{
    /// <summary>
    /// Provides an interface to service implementations for use by an <see cref="IServiceManager"/>.  The interface provides means for
    /// starting and stopping services, as well as some metadata related to the service such as name and status.
    /// </summary>
    public interface IService
    {
        /// <summary>
        /// This method is called to invoke any pre-processing logic the service might require.  Things like interface mappings for the container
        /// that other services may need during their own startup would go here.
        /// </summary>
        /// <example>
        /// The AutoSys service is an implementation of the IJobLauncher API and Tracking Error implements IScheduledJob.  In order for Tracking Error
        /// to attach to AutoSys at startup using IJobLauncher, AutoSys must map its IJobLauncher implementation in the container first.  This mapping is
        /// done in <see cref="Initialize"/> while Tracking Error attaches inside the <see cref="Start"/> implementation.
        /// </example>
        void Initialize();

        /// <summary>
        /// Starts any background threads this service executes.
        /// </summary>
        void Start();

        /// <summary>
        /// Stops any background threads the service is running, and shuts down any endpoints the service may be hosting.
        /// </summary>
        /// <returns></returns>
        bool Stop();

        /// <summary>
        /// Forcibly stops the service if the Stop does not complete in a timely fashion or returns an error.
        /// </summary>
        void ForceStop();

        /// <summary>
        /// The name of the service.
        /// </summary>
        string Name { get; }
        
        /// <summary>
        /// The version retrieved from the service dll file by the LibraryProvisioner
        /// </summary>
        string Version { get; set; }
    }
}