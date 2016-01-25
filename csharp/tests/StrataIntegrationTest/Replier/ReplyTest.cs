using System;
using Strata.Integration.Messaging;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Integration.Replier
{
    [TestClass]
    public abstract 
    class ReplyTest
    {
        private IMessagingSession messagingFactory;
        private IReplyFactory     replyFactory;
        private IReplyContext     replyContext;
        private String            correlationId;
        private IReplyRepository  repository;
        private IReply            target;

        [TestInitialize()]
        public virtual void 
        TestInitialize()
        {
            messagingFactory = CreateMessaging();
            replyFactory     = CreateReplyFactory();
            replyContext = replyFactory.CreateReplyContext(messagingFactory);
           
            correlationId    = "TEST-CORRELATION-ID";
            repository = replyContext.Repository;

            if ( repository.HasReply(correlationId) )
                repository.RemoveReply(correlationId);

            target           = replyFactory.CreateReply(correlationId,replyContext);

        }

        [TestCleanup()]
        public virtual void 
        TestCleanup()
        {
            target           = null;
            repository       = null;
            replyFactory     = null;
            replyContext = null;
        }

        [TestMethod]
        public virtual void 
        TestConstructor()
        {
            Assert.AreSame(repository, target.Repository);
            Assert.AreEqual(String.Empty,target.Input);
            Assert.AreEqual(String.Empty,target.Output);
            Assert.AreEqual(NewReplyState.GetInstance(), target.CurrentState);
        }

        [TestMethod]
        public virtual void 
        TestScenario1()
        {
            IStringMessage input  = replyContext.Messaging.CreateStringMessage();
            String         output = "OUTPUT";

            Assert.AreEqual(NewReplyState.GetInstance(), target.CurrentState);
            Assert.AreEqual(repository, target.Repository);
            Assert.IsFalse(repository.HasReply(target.CorrelationId));

            input.CorrelationId = correlationId;
            input.Payload = "INPUT";
            target.ReceiveInput(input,this.DummyMethod);

            Assert.IsTrue( repository.HasReply( target.CorrelationId ) );
		    Assert.AreEqual(repository.GetReply( target.CorrelationId ),target );
		    Assert.IsTrue( target.CurrentState is ReceivedRequestState );
		    Assert.AreEqual( input.Payload,target.Input );

            target.SendOutput(output);

            Assert.AreEqual(
                SentReplyState.GetInstance(),
                target.CurrentState);
            Assert.AreEqual(output,target.Output);
        }

        [TestMethod]
        public virtual void 
        TestScenario2()
        {
            IStringMessage input  = replyContext.Messaging.CreateStringMessage();
            String         output  = "OUTPUT";
            Counter        counter = new Counter();

            Assert.AreEqual(NewReplyState.GetInstance(), target.CurrentState);
            Assert.AreEqual(repository, target.Repository);
            Assert.IsFalse(repository.HasReply(target.CorrelationId));

            input.CorrelationId = correlationId;
            input.Payload = "INPUT";
            target.ReceiveInput(input,counter.Increment);

            Assert.IsTrue( repository.HasReply( target.CorrelationId ) );
		    Assert.AreEqual(repository.GetReply( target.CorrelationId ),target );
		    Assert.IsTrue( target.CurrentState is ReceivedRequestState );
		    Assert.AreEqual( input.Payload,target.Input );
            Assert.AreEqual(1,counter.Count);

            target.ReceiveInput(input,counter.Increment);

            Assert.AreEqual(1,counter.Count);

            target.SendOutput(output);

            Assert.AreEqual(
                SentReplyState.GetInstance(),
                target.CurrentState);
            Assert.AreEqual(output,target.Output);
        }

        [TestMethod]
        public virtual void TestScenario3()
        {
            String output = "OUTPUT";

            Assert.AreEqual(NewReplyState.GetInstance(),target.CurrentState);
            Assert.AreEqual(repository, target.Repository);
            Assert.IsFalse(repository.HasReply(target.CorrelationId));

            try
            {
                target.SendOutput(output);
                Assert.Fail("Should have thrown InvalidOperationException");
            }
            catch (InvalidOperationException) {}

            Assert.AreEqual(NewReplyState.GetInstance(),target.CurrentState);
        }

        protected abstract IMessagingSession 
        CreateMessaging();

        protected abstract IReplyFactory 
        CreateReplyFactory();

        private void 
        DummyMethod(String unused) {}
    }
}
