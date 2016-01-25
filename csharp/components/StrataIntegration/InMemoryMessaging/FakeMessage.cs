using System;
using System.Collections;
using System.Collections.Generic;
using Strata.Integration.Messaging;

namespace Strata.Integration.InMemoryMessaging
{
    public
    class FakeMessage:
        IMessage
    {
        public String       MessageId { get; set; }
        public String       CorrelationId { get; set; }
        public String       ReturnAddress { get; set; }
        public DeliveryMode DeliveryMode { get; set; }

        private IDictionary<String,Object> properties;

        public
        FakeMessage()
        {
            properties = new Dictionary<String,Object>();
        }

        public void 
        SetStringProperty(String key,String value)
        {
            properties.Add( key,value );
        }

        public void 
        SetLongProperty(String key,long value)
        {
            properties.Add( key,value );
        }

        public String 
        GetStringProperty(String key)
        {
            if ( properties.ContainsKey( key ))
                return (String)properties[key];

            return null;
        }

        public long 
        GetLongProperty(String key)
        {
            if ( properties.ContainsKey( key ))
                return (long)properties[key];

            return 0;
        }

        public bool
        HasProperty(String key)
        {
            return properties.ContainsKey( key );    
        }
    }
}
