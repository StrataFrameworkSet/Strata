using System;
using System.IO;
using Capgroup.Xwing.WcfIntegration.Messaging;

namespace Capgroup.Xwing.WcfIntegration.Commands
{
    /// <summary>
    /// An command interface for executing service commands.
    /// </summary>
    public interface IServiceCommand : IDisposable 
    {
        /// <summary>
        /// The request context for the command.
        /// </summary>
        RequestMessage Request { get; set; }

        /// <summary>
        /// The results of the command.
        /// </summary>
        ResponseMessage Response { get; set; }
        
        /// <summary>
        /// Executes the command.
        /// </summary>
        void Execute();

        ///<summary>
        /// A stream passed with the command from the client
        ///</summary>
        Stream RequestStream { get; set; }
    }
}
