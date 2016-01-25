//  ##########################################################################
//  # File Name: AbstractMessagingProxy.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using Strata.Integration.Messaging;

namespace Strata.Integration.MessagingProxy
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractMessagingProxy<K,R>:
        IMessagingProxy,
        IMessageListener
            where R: class
    {
        public String            ReturnAddress { get; private set; }
        public String            RequestChannelId { get; private set; }
        public String            ReplyChannelId { get; private set; }
        public IMessagingSession Session { get; private set; }

        private IDictionary<K,R> pendingReceivers;
        private IMessageReceiver receiver;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected 
        AbstractMessagingProxy(
            String            returnAddress,
            String            requestChannelId,
            String            replyChannelId,
            IMessagingSession session)
        {
            ReturnAddress    = returnAddress;
            RequestChannelId = requestChannelId;
            ReplyChannelId   = replyChannelId;
            Session          = session;
            pendingReceivers = new ConcurrentDictionary<K,R>();
        
            receiver = 
                Session.CreateMessageReceiver( 
                    ReplyChannelId,
                    "ReturnAddress='" + ReturnAddress + "'" );
            receiver.Listener = this;
        }


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void 
        Activate()
        {
            receiver.StartListening();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void 
        Deactivate()
        {
            receiver.StopListening();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void 
        Close()
        {
            receiver.Close();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool 
        IsActivated()
        {
            return receiver.IsListening();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool 
        HasPendingReceivers()
        {
            return pendingReceivers.Count > 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void
        OnMessage(IStringMessage message)
        {
            throw 
                new InvalidOperationException(
                    "Must override in subclass");          
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void
        OnMessage(IMapMessage message)
        {
            throw 
                new InvalidOperationException(
                    "Must override in subclass");                      
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void
        OnMessage(IObjectMessage message)
        {
            throw 
                new InvalidOperationException(
                    "Must override in subclass");                      
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected void
        InsertPendingReceiver(K key,R replyReceiver)
        {
            if (HasPendingReceiver(key))
                throw new ArgumentException("duplicate key");

            pendingReceivers.Add(key,replyReceiver);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected R
        RemovePendingReceiver(K key)
        {
            R replyReceiver = null;

            if (!HasPendingReceiver(key))
                throw new InvalidOperationException("Unknown reply: " + key);

            replyReceiver = pendingReceivers[key];
            return replyReceiver;

        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected bool
        HasPendingReceiver(K key)
        {
            return pendingReceivers.ContainsKey(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected IMessageSender
        CreateMessageSender()
        {
            return Session.CreateMessageSender(RequestChannelId);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected IStringMessage
        CreateStringMessage()
        {
            IStringMessage message = Session.CreateStringMessage();

            message.ReturnAddress = ReturnAddress;
            return message;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected IMapMessage
        CreateMapMessage()
        {
            IMapMessage message = Session.CreateMapMessage();

            message.ReturnAddress = ReturnAddress;
            return message;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected IObjectMessage
        CreateObjectMessage()
        {
            IObjectMessage message = Session.CreateObjectMessage();

            message.ReturnAddress = ReturnAddress;
            return message;
        }
 
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private String 
        CreateReturnAddress(String prefix)
        {
            return prefix + new Random().Next().ToString();
        }

    }
}

//  ##########################################################################
