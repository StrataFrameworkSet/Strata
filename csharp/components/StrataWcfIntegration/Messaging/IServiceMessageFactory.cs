using System.IO;

namespace Capgroup.Xwing.WcfIntegration.Messaging
{
    public interface IServiceMessageFactory
    {
        RequestMessage CreateRequest(string command, object payload = null);
        ResponseMessage CreateResponse(RequestMessage request);
        RequestStreamMessage CreateStreamRequest(string command, object payload, Stream requestStream);
    }
}
