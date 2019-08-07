using System;
using System.Threading;
using Strata.Integration.Messaging;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Integration.Requester
{
    [TestClass]
    public abstract 
    class RequestTest
    {
        private IMessagingSession session;
        private IRequestFactory requestFactory;
        private String correlationId;
        private IRequestContext context;
        private IRequestRepository repository;
        private IRequest target;

        [TestInitialize()]
        public virtual void TestInitialize()
        {
            session = CreateMessaging();
            requestFactory = CreateRequestFactory();
            correlationId = "TEST-CORRELATION-ID";
            context = requestFactory.CreateRequestContext(session);
            repository = context.Repository;
            target = requestFactory.CreateRequest(correlationId, context);
            Counter.CurrentCount = 0;
        }

        [TestCleanup()]
        public virtual void TestCleanup()
        {
            target = null;
            repository = null;
        }

        [TestMethod]
        public virtual void TestConstructor()
        {
            Assert.AreEqual(correlationId, target.CorrelationId);
            Assert.AreSame(repository, target.Repository);
            Assert.IsNull(target.Input);
            Assert.IsNull(target.Output);
            Assert.AreEqual(NewRequestState.GetInstance(), target.CurrentState);
            Assert.IsFalse(target.HasExceededResendLimit());
        }

        [TestMethod]
        public virtual void TestScenario1()
        {
            String input = "INPUT";
            IStringMessage output = target.Session.CreateStringMessage();
            ReceiveAction action = DummyMethod;

            output.CorrelationId = target.CorrelationId;
            output.Payload = "OUTPUT";

            Assert.AreEqual(NewRequestState.GetInstance(), target.CurrentState);
            Assert.AreEqual(repository, target.Repository);
            Assert.IsFalse(repository.HasRequest(target.CorrelationId));

            target.SendInput(input);

            Assert.IsTrue( repository.HasRequest( target.CorrelationId ) );
		    Assert.AreEqual(repository.GetRequest( target.CorrelationId ),target );
		    Assert.IsTrue( target.CurrentState is PendingReplyState );
		    Assert.AreEqual( input,target.Input );

            target.ReceiveOutput(output, action);

            Assert.AreEqual(ReceivedReplyState.GetInstance(),
                target.CurrentState);
            Assert.AreEqual(output.Payload, target.Output);
        }

        [TestMethod]
        public virtual void TestScenario2()
        {
            String input = "INPUT";
            IStringMessage output = target.Session.CreateStringMessage();
            ReceiveAction action = DummyMethod;

            output.CorrelationId = target.CorrelationId;
            output.Payload = "OUTPUT";

            Assert.AreEqual(NewRequestState.GetInstance(), target.CurrentState);
            Assert.AreEqual(repository, target.Repository);
            Assert.IsFalse(repository.HasRequest(target.CorrelationId));

            target.SendInput(input);

            Assert.IsTrue(repository.HasRequest(target.CorrelationId));
            Assert.AreEqual(repository.GetRequest(target.CorrelationId), target);
            Assert.IsTrue(target.CurrentState is PendingReplyState);
            Assert.AreEqual(input, target.Input);

            Thread.Sleep((target.ResendTimeout + 1) * 1000);

            target.ReceiveOutput(output, action);

            Assert.AreEqual(ReceivedReplyState.GetInstance(),
                target.CurrentState);
            Assert.AreEqual(output.Payload, target.Output);
        }

        [TestMethod]
        public virtual void TestScenario3()
        {
            String input = "INPUT";
            IStringMessage output = target.Session.CreateStringMessage();
            ReceiveAction action = Counter.IncrementCount;

            output.CorrelationId = target.CorrelationId;
            output.Payload = "OUTPUT";

            Assert.AreEqual(NewRequestState.GetInstance(), target.CurrentState);
            Assert.AreEqual(repository, target.Repository);
            Assert.IsFalse(repository.HasRequest(target.CorrelationId));

            target.SendInput(input);

            Assert.IsTrue(repository.HasRequest(target.CorrelationId));
            Assert.AreEqual(repository.GetRequest(target.CorrelationId), target);
            Assert.IsTrue(target.CurrentState is PendingReplyState);
            Assert.AreEqual(input, target.Input);

            Thread.Sleep(target.ResendTimeout * (target.ResendLimit + 1) * 1000);

            Assert.AreEqual(ExceededResendLimitState.GetInstance(),
                target.CurrentState);

            target.ReceiveOutput(output, action);

            Assert.AreEqual(ReceivedReplyState.GetInstance(),
                target.CurrentState);
            Assert.AreEqual(1, Counter.CurrentCount);
            Assert.AreEqual(output.Payload, target.Output);

            target.ReceiveOutput(output, action);

            Assert.AreEqual(ReceivedReplyState.GetInstance(),
                target.CurrentState);
            Assert.AreEqual(1, Counter.CurrentCount);
            Assert.AreEqual(output.Payload, target.Output);
        }

        [TestMethod]
        public virtual void TestScenario4()
        {
            IStringMessage intput = target.Session.CreateStringMessage();
            IStringMessage output = target.Session.CreateStringMessage();
            ReceiveAction action = Counter.IncrementCount;

            output.CorrelationId = correlationId;
            output.Payload = "OUTPUT";

            Assert.AreEqual(NewRequestState.GetInstance(), target.CurrentState);
            Assert.AreEqual(repository, target.Repository);
            Assert.IsFalse(repository.HasRequest(target.CorrelationId));

            try
            {
                target.ReceiveOutput(output, action);
                Assert.Fail("Should have thrown IllegalStateException");
            }
            catch (InvalidOperationException)
            {
            }

            Assert.AreEqual(NewRequestState.GetInstance(), target.CurrentState);
        }

        private void DummyMethod(IMessage unused) { }

        protected abstract IMessagingSession 
        CreateMessaging();

        protected abstract IRequestFactory 
        CreateRequestFactory();

    }
}
