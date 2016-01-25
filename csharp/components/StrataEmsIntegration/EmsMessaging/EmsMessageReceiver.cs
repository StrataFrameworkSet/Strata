//  ##########################################################################
//  # File Name: EmsMessageReceiver.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;
using TIBCO.EMS;
using IMessageListener = Strata.Integration.Messaging.IMessageListener;

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
    class EmsMessageReceiver:
        IMessageReceiver
    {
        public IMessageListener Listener { get; set; }
        public String Selector { get { return consumer.MessageSelector; }}

        private TIBCO.EMS.MessageConsumer consumer;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>EmsMessageReceiver</c> instance.
        /// </summary>
        /// 
        public 
        EmsMessageReceiver(TIBCO.EMS.MessageConsumer c)
        {
            consumer = c;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        StartListening()
        {
            consumer.MessageListener = new EmsMessageListener(Listener);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        StopListening()
        {
            consumer.MessageListener = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        IsListening()
        {
            return consumer.MessageListener != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IMessage 
        Receive()
        {
            return Convert( consumer.Receive() );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IMessage 
        ReceiveWithTimeout(long milliseconds)
        {
            return Convert( consumer.Receive(milliseconds) );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IMessage 
        ReceiveNoWait()
        {
            return Convert( consumer.Receive() );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Close()
        {
            consumer.Close();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private IMessage
        Convert(Message message)
        {
            if ( message is TextMessage )
                return new EmsStringMessage((TextMessage)message);
            else if ( message is MapMessage )
                return new EmsMapMessage((MapMessage)message);
            else if ( message is ObjectMessage )
                return new EmsObjectMessage((ObjectMessage)message);
        
            throw new ArgumentException("Unsupported message type","message");
        }
    }
}

//  ##########################################################################
