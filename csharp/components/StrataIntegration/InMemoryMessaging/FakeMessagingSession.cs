//  ##########################################################################
//  # File Name: FakeMessagingSession.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using Strata.Integration.Messaging;

namespace Strata.Integration.InMemoryMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class FakeMessagingSession:
        IMessagingSession
    {
        public IDictionary<String,Queue<IMessage>> Connections { get; protected set; }
        public FakeMessageBroker Broker { get; protected set; }

        private bool receiving;

        public FakeMessagingSession(IDictionary<String,Queue<IMessage>> connections)
        {
            Connections = connections;
            Broker = new FakeMessageBroker();
            receiving = false;
        }

        public IMessageSender 
        CreateMessageSender(String id)
        {
            return new FakeMessageSender(id,Broker);
        }

        public IMessageReceiver 
        CreateMessageReceiver(String id)
        {
            FakeMessageReceiver receiver = new FakeMessageReceiver();

            Broker.RegisterReceiver(id,receiver);
            return receiver;
        }

        public IMessageReceiver 
        CreateMessageReceiver(String id,String selector)
        {
            FakeMessageReceiver receiver = new FakeMessageReceiver();

            Broker.RegisterReceiver(id,receiver);
            return receiver;
        }

        public IStringMessage 
        CreateStringMessage()
        {
            return new FakeTextMessage();
        }

        public IMapMessage 
        CreateMapMessage()
        {
            return new FakeMapMessage();
        }

        public IObjectMessage 
        CreateObjectMessage()
        {
            return new FakeObjectMessage();
        }

        public void StartReceiving()
        {
            receiving = true;
        }

        public void StopReceiving()
        {
            receiving = false;
        }

        public bool IsReceiving()
        {
            return receiving;
        }

        public void 
        Close() {}
    }
}

//  ##########################################################################
