using System;
using System.ServiceModel;
using System.ServiceModel.Channels;
using Capgroup.Xwing.Common.Configuration;

namespace Capgroup.Xwing.WcfIntegration
{
    /// <summary>
    /// Defines declarative WCF metadata used by the <see cref="IServiceManager"/> when hosting this service as a WCF endpoint.
    /// </summary>
    [AttributeUsage(AttributeTargets.Class)]
    public class WcfEndpointAttribute : Attribute
    {
        private const string URL_ROOT = "{0}://localhost:{1}/fifx/services";

        /// <summary>
        /// The base URL used by all services, plus the current environment the service is running in.
        /// </summary>
        public string BaseAddress { get; private set; }

        /// <summary>
        /// The interface Type which specifies the <see cref="ServiceContractAttribute"/> for this service.
        /// </summary>
        public Type ContractType { get; private set; }

        ///<summary>
        /// Transfermode can be 
        ///</summary>
        public TransferMode CustomTransferMode { get; private set; }

        public Binding Binding { get; private set; }

        ///// <summary>
        ///// Constructs a new <see cref="WcfEndpointAttribute"/> instance with the provided parameters.
        ///// </summary>
        ///// <param name="contractType">The <see cref="Type"/> of the service interface.</param>
        //public WcfEndpointAttribute(Type contractType)
        //{
        //    BaseAddress = URL_ROOT + "/" + ApplicationSettings.Environment;
        //    ContractType = contractType;
        //}

        /// <summary>
        /// Constructs a new <see cref="WcfEndpointAttribute"/> instance with the provided parameters.
        /// </summary>
        /// <param name="contractType">The <see cref="Type"/> of the service interface.</param>
        /// <param name="customTransferMode">TransferMode can be streamed or buffered (default)</param>
        public WcfEndpointAttribute(Type contractType, TransferMode customTransferMode = TransferMode.Buffered)
        {
            ContractType = contractType;
            CustomTransferMode = customTransferMode;
            BaseAddress = URL_ROOT + "/" + ApplicationSettings.Environment + "/";
        }
    }
}
