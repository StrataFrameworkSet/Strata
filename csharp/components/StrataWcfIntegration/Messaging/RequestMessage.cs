using System.ServiceModel;

namespace Capgroup.Xwing.WcfIntegration.Messaging
{
    /// <summary>
    /// A message for making service requests from a client.  This message defines the default request contract for making a 
    /// service call.  Values are held in public fields, with no surrounding logic so handle accordingly.
    /// </summary>
    [MessageContract]
    public class RequestMessage
    {
        /// <summary>
        /// A tag assigned to the client type making the request.  Enables a service to differentiate between 
        /// disparate client types which might be using a single service interface.
        /// </summary>
        [MessageHeader]
        public string ClientTag;

        /// <summary>
        /// A unique identifer used to match this request message to any asynchronous replies.  This value should
        /// match the response's <see cref="ResponseMessage.CorrelationId"/>
        /// </summary>
        [MessageHeader]
        public string RequestId;

        /// <summary>
        /// The unique identifier of the entity initiating the request.
        /// </summary>
        [MessageHeader]
        public string RequestorId;

        /// <summary>
        /// The command for the service to execute.  Default behavior is for the service to append "Command" to the end
        /// of this string in order to locate the implementation class for this command via reflection.
        /// </summary>
        /// <example>
        /// If the implementation class for a command is GetDataCommand, then this value would be GetData.
        /// </example>
        [MessageHeader]
        public string Command;

        /// <summary>
        /// The data associated with making the request.  Serialization is managed by the <see cref="ServiceKnownTypeAttribute"/>s 
        /// of the service contract.
        /// </summary>
        [MessageBodyMember]
        public object Payload;

        #region Override of ToString

        public override string ToString()
        {
            return string.Format("[RequestMessage: ClientTag = {0}, RequestId = {1}, RequestorId = {2}, Command = {3}, Payload = {4}]",
                                 ClientTag,
                                 RequestId,
                                 RequestorId,
                                 Command,
                                 Payload);
        }

        #endregion

    }
}
