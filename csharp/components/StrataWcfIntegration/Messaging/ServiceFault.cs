using System;
using System.Runtime.Serialization;

namespace Capgroup.Xwing.WcfIntegration.Messaging
{
    [DataContract]
    public class ServiceFault
    {
        [DataMember(IsRequired = true, Order = 1)]
        public Guid Id;

        [DataMember(IsRequired = true, Order = 2)]
        public string MessageText;
    }
}
