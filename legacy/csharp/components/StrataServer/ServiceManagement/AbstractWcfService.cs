using System;
using System.IO;
using System.ServiceModel;
using Strata.Common.Utility;
using Strata.WcfIntegration;
using Strata.WcfIntegration.Commands;
using Strata.WcfIntegration.Messaging;
using Strata.WcfIntegration.Unity;
using Microsoft.Practices.ServiceLocation;
using Microsoft.Practices.Unity.InterceptionExtension;

namespace Strata.Server.ServiceManagement
{
    ///<summary>
    /// An abstract representation of a service which hosts a WCF endpoint to be used as a template.  This abstraction
    /// is decorated with the <see cref="UnityContractBehaviorAttribute"/> so any services built using its default
    /// behavior will receive policy injection.  Also gets [ServiceBehavior(MaxItemsInObjectGraph = int.MaxValue)].
    ///</summary>
    [UnityContractBehavior]
    [ServiceBehavior(MaxItemsInObjectGraph = int.MaxValue)]
    public abstract class AbstractWcfService : AbstractService, IWcfService
    {
        private const string CommandNotProvided = "Command must not be null or empty.";
        private const string CommandTypeNotFound = "Command not supported (could not locate command type in service assembly): {0}";
        private const string CommandTypeNotResolved = "Command not supported (could not resolve from container - there might be a dependency issue): {0}";

        /// <summary>
        /// Represents a method which returns a Type name based on the command supplied - this name is used to resolve the type, so it needs
        /// to be a valid name.
        /// </summary>
        /// <param name="command">The command.</param>
        /// <returns>
        /// A Type name that resolves into a supported Type.  This Type should implement, at least, <see cref="IServiceCommand"/> 
        /// or it will not get executed.
        /// </returns>
        protected abstract string GetCommandTypeName(string command);

        ///<summary>
        /// Executes the command indicated in the <see cref="RequestMessage.Command"/> property.  A <see cref="Type.Name"/> will be built using the
        /// <see cref="GetCommandTypeName"/> implementation, and the command instance will be invoked.
        ///</summary>
        /// <remarks>
        /// The "ServiceInterception" <see cref="TagAttribute"/> flags this method for interception by the policy named "ServiceInterception".
        /// </remarks>
        ///<param name="request">The request message containing the command to execute.</param>
        ///<returns>A <see cref="ResponseMessage"/> containing any results of the command execution.</returns>
        //[Tag(InterceptionPolicies.Metrics)]
        //[Tag(InterceptionPolicies.Audit)]
        public ResponseMessage Execute(RequestMessage request)
        {
            return CommonExecute(request);
        }

        public ResponseMessage ExecuteStream(RequestStreamMessage request)
        {
            return CommonExecute(request.RequestMessageHeader, request.RequestStream);
        }

        private ResponseMessage CommonExecute(RequestMessage request, Stream stream = null)
        {
            ThreadTools.SetThreadName(Name + " | " + request.RequestorId);

            var response = ServiceLocator.Current.GetInstance<ResponseMessage>();
            response.CorrelationId = request.RequestId;

            if (string.IsNullOrEmpty(request.Command))
            {
                response.Errors.Add(CommandNotProvided);
                return response;
            }

            string commandTypeName = GetCommandTypeName(request.Command);
            Type commandType = Type.GetType(commandTypeName);

            if (commandType == null)
            {
                response.Errors.Add(string.Format(CommandTypeNotFound, commandTypeName));
                return response;
            }

            using (var command = ServiceLocator.Current.GetInstance(commandType) as IServiceCommand)
            {
                if (command == null)
                {
                    response.Errors.Add(string.Format(CommandTypeNotResolved, commandTypeName));
                    return response;
                }

                try
                {
                    command.Request = request;
                    command.Response = response;
                    command.RequestStream = stream;
                    command.Execute();
                }
                catch (Exception ex)
                {
                    command.Response.Errors.Add(ex.ToString());
                    Log.Error(ex.ToString());
                }

                response = command.Response;
            }

            return response;
        }
    }
}