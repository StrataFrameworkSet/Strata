using System.Collections.Generic;
using System.ServiceModel;
using Capgroup.Xwing.WcfIntegration.Messaging;

namespace Capgroup.Xwing.WcfIntegration
{
    /// <summary>
    /// <para>
    /// A basic interface exposing a simple <see cref="RequestMessage"/>-<see cref="ResponseMessage"/> execution interface.  This
    /// interface is meant to be extended by service authors exposing their service as a WCF endpoint.  The <see cref="ServiceKnownTypeAttribute"/>s 
    /// applied here provide a template core serialization options.  Service authors need only add their own <see cref="ServiceKnownTypeAttribute"/>s
    /// when they extend this interface for their own service interface.
    /// </para> 
    /// <para>
    /// This interface should not be implemented directly.  Implement through inheritance.  
    /// Note: The inheriting interface must include the <see cref="ServiceContractAttribute"/>.
    /// </para> 
    /// <example>
    /// <code>
    /// [ServiceContract]
    /// [ServiceKnownType(typeof(ServiceSpecificObject)))]
    /// [ServiceKnownType(typeof(List{ServiceSpecificObject})))]
    /// public interface IExampleService : IWcfService { }
    /// </code>
    /// </example>
    /// </summary>
    /// <remarks>
    /// Note: Collections of objects must be explicitly declared. 
    /// </remarks>
    [ServiceContract]
    [ServiceKnownType(typeof(List<string>))]
    [ServiceKnownType(typeof(RequestMessage))]
    public interface IWcfService
    {
        [OperationContract]
        ResponseMessage Execute(RequestMessage request);

        [OperationContract]
        ResponseMessage ExecuteStream(RequestStreamMessage request);
    }
}
