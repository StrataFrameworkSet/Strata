//  ##########################################################################
//  # File Name: EmsMessage.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using TIBCO.EMS;
using Strata.Integration.Messaging;

namespace Strata.EmsIntegration.EmsMessaging
{
    
    using Delivery = Strata.Integration.Messaging.DeliveryMode;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class EmsMessage:
        IMessage
    {
        public const String RETURN_ADDRESS_PROPERTY = "ReturnAddress";

        public abstract Message MessageImp { get; }
        
        public String 
        MessageId
        {
            get { return MessageImp.MessageID; }
            set { MessageImp.MessageID = value; }
        }

        public String 
        CorrelationId
        {
            get { return MessageImp.CorrelationID; }
            set { MessageImp.CorrelationID = value; }
        }

        public String 
        ReturnAddress
        {
            get {return MessageImp.GetStringProperty(RETURN_ADDRESS_PROPERTY);}
            set {MessageImp.SetStringProperty(RETURN_ADDRESS_PROPERTY,value);}
        }

        public Delivery
        DeliveryMode
        {
            get
            {
                return 
                    MessageImp.DeliveryMode == TIBCO.EMS.DeliveryMode.PERSISTENT
                        ? Delivery.PERSISTENT : Delivery.NON_PERSISTENT;
            }

            set
            {
                MessageImp.DeliveryMode = 
                    value == Delivery.PERSISTENT 
                        ? TIBCO.EMS.DeliveryMode.PERSISTENT 
                        : TIBCO.EMS.DeliveryMode.NON_PERSISTENT;
            }
        }

        protected
        EmsMessage() {}

        public void 
        SetStringProperty(String key,String value)
        {
            MessageImp.SetStringProperty( key,value );
        }

        public void 
        SetLongProperty(String key,long value)
        {
            MessageImp.SetLongProperty( key,value );            
        }

        public String 
        GetStringProperty(String key)
        {
            return MessageImp.GetStringProperty( key );
        }

        public long 
        GetLongProperty(String key)
        {
            return MessageImp.GetLongProperty( key );
        }

        public bool 
        HasProperty(String key)
        {
            return MessageImp.PropertyExists( key );
        }
    }
}

//  ##########################################################################
