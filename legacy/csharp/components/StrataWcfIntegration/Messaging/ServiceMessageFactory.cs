using System;
using System.Configuration;
using System.IO;
using Microsoft.Practices.Unity;

namespace Capgroup.Xwing.WcfIntegration.Messaging 
{
    public class ServiceMessageFactory : IServiceMessageFactory
    {
        #region Static Client Tag

        ///<summary>
        ///</summary>
        public const string UNSET_CLIENT_TAG = "UNSET";
        private const string ClientTagConfigKey = "ClientTag";
        private readonly static string _clientTag;

        static ServiceMessageFactory()
        {
            _clientTag = ConfigurationManager.AppSettings[ClientTagConfigKey] ?? UNSET_CLIENT_TAG;
        }

        #endregion

        [Dependency]
        public IUnityContainer Container { get; set; }

        /// <summary>
        /// Creates a <see cref="RequestMessage"/> with the following properties pre-populated: 
        /// <see cref="RequestMessage.ClientTag"/>,
        /// <see cref="RequestMessage.RequestId"/>,
        /// <see cref="RequestMessage.RequestorId"/>.
        /// </summary>
        /// <returns>A service request of <see cref="RequestMessage"/></returns>
        public RequestMessage CreateRequest(string command, object payload = null)
        {
            var request = Container.Resolve<RequestMessage>();
            request.ClientTag = _clientTag;
            request.RequestId = Guid.NewGuid().ToString();
            request.RequestorId = Environment.UserName;
            request.Command = command;
            request.Payload = payload;

            return request;
        }

        /// <summary>
        /// Creates a <see cref="ResponseMessage"/> with the <see cref="ResponseMessage.CorrelationId"/> populated with
        /// the <see cref="RequestMessage.RequestId"/>.
        /// </summary>
        /// <param name="request">The <see cref="RequestMessage"/> to associate the response with.</param>
        /// <returns>A new <see cref="ResponseMessage"/>.</returns>
        public ResponseMessage CreateResponse(RequestMessage request)
        {
            var response = Container.Resolve<ResponseMessage>();
            response.CorrelationId = request.RequestId;
            return response;
        }

        ///<summary>
        /// Creates a RequestStreamMessage
        ///</summary>
        ///<param name="command"></param>
        ///<param name="payload">Payload is always expected to be the file name of the source stream.</param>
        ///<param name="requestStream"></param>
        ///<returns></returns>
        public RequestStreamMessage CreateStreamRequest(string command, object payload, Stream requestStream)
        {
            var request = Container.Resolve<RequestMessage>();
            request.ClientTag = _clientTag;
            request.RequestId = Guid.NewGuid().ToString();
            request.RequestorId = Environment.UserName;
            request.Command = command;
            request.Payload = payload;

            var streamRequest = Container.Resolve<RequestStreamMessage>();
            streamRequest.RequestStream = requestStream;
            streamRequest.RequestMessageHeader = request;

            return streamRequest;
        }
    }
}
