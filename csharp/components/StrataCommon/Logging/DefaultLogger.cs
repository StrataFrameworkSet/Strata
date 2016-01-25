using System;
using System.Diagnostics;
using System.Threading;
using Microsoft.Practices.EnterpriseLibrary.Logging;

namespace Strata.Common.Logging
{
    /// <summary>
    /// Implements <see cref="ILogger"/> as a wrapper around the Enterprise Library Logging Block.
    /// </summary>
    public 
    class DefaultLogger: 
        ILogger
    {
        //private readonly LoggingExceptionHandler handler = null;

        /// <summary>
        /// Writes a message to the Enterprise Library logger of the given <see cref="TraceEventType"/>.
        /// </summary>
        /// <param name="message">The text message.</param>
        /// <param name="logType">The <see cref="TraceEventType"/>.</param>
        private void Write(string message, TraceEventType logType)
        {
            Logger.Write(new LogEntry
            {
                Message = message,
                Severity = logType
            });

            string timestamp = ("[" + DateTime.Now).PadRight(23);
            string threadName = ("] [" + Thread.CurrentThread.Name).PadRight(20);
            string typeOfLog = "] " + logType.ToString().PadRight(12);
            message = timestamp + threadName + typeOfLog + ": " + message;
            Debug.WriteLine(message);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Verbose.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Verbose(string message)
        {
            Write(message, TraceEventType.Verbose);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Information.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Info(string message)
        {
            Write(message, TraceEventType.Information);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Warning.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Warn(string message)
        {
            Write(message, TraceEventType.Warning);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Error.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Error(string message)
        {
            Write(message, TraceEventType.Error);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Critical.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Critical(string message)
        {
            Write(message, TraceEventType.Critical);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Start.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Start(string message)
        {
            Write(message, TraceEventType.Start);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Stop.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Stop(string message)
        {
            Write(message, TraceEventType.Stop);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Suspend.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Suspend(string message)
        {
            Write(message, TraceEventType.Suspend);
        }

        /// <summary>
        /// Logs a message as <see cref="TraceEventType"/>.Resume.
        /// </summary>
        /// <param name="message">The message to log.</param>
        public void Resume(string message)
        {
            Write(message, TraceEventType.Resume);
        }


    }
}
