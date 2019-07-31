//  ##########################################################################
//  # File Name: EmsMessageSender.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using TIBCO.EMS;
using Strata.Integration.Messaging;

namespace Strata.EmsIntegration.EmsMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class EmsMessageSender:
        IMessageSender
    {
        public long TimeToLiveInMilliseconds
        {
            get
            {
                return producer.TimeToLive;
            }

            set
            {
                producer.TimeToLive = value;
            }
        }

        private MessageProducer producer;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="p"></param>
        /// 
        public 
        EmsMessageSender(MessageProducer p)
        {
            producer = p;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        public void 
        Send(IMessage message)
        {
            producer.Send(((EmsMessage)message).MessageImp);
        }
    }
}

//  ##########################################################################
