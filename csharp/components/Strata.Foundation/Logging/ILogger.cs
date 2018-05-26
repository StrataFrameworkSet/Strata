using System.Diagnostics;

namespace Strata.Foundation.Logging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// An interface for logging.
    /// </summary>
    /// 
    public
    interface ILogger
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Verbose"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Verbose(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Information"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Info(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Warning"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Warn(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Error"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Error(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Critical"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Critical(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Start"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Start(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Stop"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Stop(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Suspend"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Suspend(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Logs a message as <see cref="TraceEventType.Resume"/>.
        /// </summary>
        /// <param name="message">The message to log.</param>
        /// 
        void Resume(string message);
    }
}
