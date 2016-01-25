using System;
using System.IO;
using System.ServiceModel;

namespace Capgroup.Xwing.WcfIntegration.Messaging
{
    ///<summary>
    /// Special message type for streams. Streams must be the only member in the message body.
    ///</summary>
    [MessageContract]
    public class RequestStreamMessage : IDisposable 
    {
        [MessageHeader] public RequestMessage RequestMessageHeader;
        [MessageBodyMember] public Stream RequestStream;
        
        public void Dispose()
        {
            if (RequestStream != null)
            {
                RequestStream.Close();
                RequestStream = null;
            }
        }
    }
}
