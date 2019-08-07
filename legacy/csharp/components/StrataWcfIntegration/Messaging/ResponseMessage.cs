using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace Capgroup.Xwing.WcfIntegration.Messaging
{
    /// <summary>
    /// A message for sending responses back to a client.  This message defines the default response contract for responding to a 
    /// service call.  Values are held in public fields, with no surrounding logic so handle accordingly.
    /// </summary>
    [MessageContract]
    public class ResponseMessage
    {
        /// <summary>
        /// A unique value used to associate this response message with its originating request message.
        /// </summary>
        [MessageBodyMember]
        public string CorrelationId;

        /// <summary>
        /// The <see cref="AcknowledgeType"/> of the request.  A simple indication of the outcome of the request.  Default is <see cref="AcknowledgeType.Failure"/>.
        /// </summary>
        [MessageBodyMember]
        public AcknowledgeType Acknowledgement = AcknowledgeType.Failure;

        /// <summary>
        /// The results of the service request.
        /// </summary>
        [MessageBodyMember]
        public object Payload;

        /// <summary>
        /// Any informational/harmless messages returned by the service as a result of the request.
        /// </summary>
        [MessageBodyMember]
        public readonly List<string> Messages = new List<string>();

        /// <summary>
        /// Any warning messages returned by the service as a result of the request.
        /// </summary>
        [MessageBodyMember]
        public readonly List<string> Warnings = new List<string>();

        /// <summary>
        /// Any error messages returned by the service as a result of the request.
        /// </summary>
        [MessageBodyMember]
        public readonly List<string> Errors = new List<string>();

        #region Override of ToString

        public override string ToString()
        {
            return string.Format("[ResponseMessage: CorrelationId = {0}, Acknowledgement = {1}, Messages = [{2}], Warnings = [{3}], Errors = [{4}], Payload = {5}]",
                                 CorrelationId,
                                 Acknowledgement,
                                 (Messages.Count > 0) ? Messages.Aggregate((i, j) => i + ", " + j) : string.Empty,
                                 (Warnings.Count > 0) ? Warnings.Aggregate((i, j) => i + ", " + j) : string.Empty,
                                 (Errors.Count > 0) ? Errors.Aggregate((i, j) => i + ", " + j) : string.Empty,
                                 Payload);
        }

        #endregion
    }
}
