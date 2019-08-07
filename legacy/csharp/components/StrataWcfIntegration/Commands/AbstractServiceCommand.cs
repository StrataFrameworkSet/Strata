using System.IO;
using Capgroup.Xwing.Common.ExceptionManagement;
using Capgroup.Xwing.Common.Logging;
using Capgroup.Xwing.WcfIntegration.Messaging;
using Microsoft.Practices.Unity;

namespace Capgroup.Xwing.WcfIntegration.Commands
{
    /// <summary>
    /// A basic command implementation which provides a stub Execute method as well as some simple entity persistence logic.
    /// </summary>
    public abstract class AbstractServiceCommand : IServiceCommand
    {
        /// <summary>
        /// The application logger dependency.
        /// </summary>
        [Dependency]
        public ILogger Log { get; set; }

        /// <summary>
        /// The application exception handling dependency.
        /// </summary>
        [Dependency]
        public IExceptionManager ExceptionManager { get; set; }

        /// <summary>
        /// The request context for the command.
        /// </summary>
        public RequestMessage Request { get; set; }

        /// <summary>
        /// The results of the command.
        /// </summary>
        public ResponseMessage Response { get; set; }

        /// <summary>
        /// Executes the command.
        /// </summary>
        public abstract void Execute();

        public Stream RequestStream {get; set;}
            
        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        /// <filterpriority>2</filterpriority>
        public virtual void Dispose() { }
    }
}
