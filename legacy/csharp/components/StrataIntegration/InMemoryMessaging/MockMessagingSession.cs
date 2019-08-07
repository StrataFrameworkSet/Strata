using System;
using Strata.Integration.Messaging;
using Moq;

namespace Strata.Integration.InMemoryMessaging
{
    public
    class MockMessagingSession:
        IMessagingSession
    {
        private bool receiving;

        public 
        MockMessagingSession()
        {
            receiving = false;
        }

        public IMessageSender 
        CreateMessageSender(String id)
        {
            Mock<IMessageSender> sender = new Mock<IMessageSender>();
            return sender.Object;
        }

        public IMessageReceiver 
        CreateMessageReceiver(String id)
        {
            Mock<IMessageReceiver> receiver = new Mock<IMessageReceiver>();
            return receiver.Object;
        }

        public IMessageReceiver 
        CreateMessageReceiver(String id,String selector)
        {
            Mock<IMessageReceiver> receiver = new Mock<IMessageReceiver>();
            return receiver.Object;
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


        public void 
        StartReceiving()
        {
            receiving = true;
        }

        public void 
        StopReceiving()
        {
            receiving = false;
        }

        public bool 
        IsReceiving()
        {
            return receiving;
        }

        public void
        Close()
        {
            
        }
    }
}
