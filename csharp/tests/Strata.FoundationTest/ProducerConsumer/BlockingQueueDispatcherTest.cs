using System;
using NUnit;
using NUnit.Framework;

namespace Strata.Foundation.ProducerConsumer
{
    [TestFixture]
    public 
    class BlockingQueueDispatcherTest
    {
        private static long MAX = 10000000L;

        private ICountRequestDispatcher itsTarget;
        private ICountRequestProducer itsProducer1;
        private ICountRequestProducer itsProducer2;
        private ICountRequestProducer itsProducer3;
        private ICountRequestConsumer itsConsumer1;
        private ICountRequestConsumer itsConsumer2;
        private ICountRequestConsumer itsConsumer3;

        [SetUp]
        public void
        SetUp()
        {
            itsTarget = new CountRequestBlockingQueueDispatcher();
            itsProducer1 = new CountRequestProducer(1, MAX, DispatchKind.ROUTE);
            itsProducer2 = new CountRequestProducer(2, MAX, DispatchKind.ROUTE);
            itsProducer3 = new CountRequestProducer(3, MAX, DispatchKind.ROUTE);
            itsConsumer1 = new CountRequestConsumer(1);
            itsConsumer2 = new CountRequestConsumer(2);
            itsConsumer3 = new CountRequestConsumer(3);

            itsProducer1.SetDispatcher(itsTarget);
            itsProducer2.SetDispatcher(itsTarget);
            itsProducer3.SetDispatcher(itsTarget);

            itsTarget.AttachConsumer(itsConsumer1);
            itsTarget.AttachConsumer(itsConsumer2);
            itsTarget.AttachConsumer(itsConsumer3);

        }

        [TearDown]
        public void
        TearDown()
        {
            itsProducer1.StopProducing();
            itsProducer2.StopProducing();
            itsProducer3.StopProducing();

            itsTarget.StopDispatching();

        }

        [Test]
        public void 
        TestAttachConsumer()
        {
            ICountRequestConsumer consumer = new CountRequestConsumer(100);

            Assert.IsFalse(itsTarget.HasConsumer(consumer));
            itsTarget.AttachConsumer(consumer);
            Assert.IsTrue(itsTarget.HasConsumer(consumer));
        }

        [Test]
        public void
        TestDetachConsumer()
        {
            ICountRequestConsumer consumer = new CountRequestConsumer(100);

            Assert.IsFalse(itsTarget.HasConsumer(consumer));
            itsTarget.AttachConsumer(consumer);
            Assert.IsTrue(itsTarget.HasConsumer(consumer));
            itsTarget.DetachConsumer(consumer);
            Assert.IsFalse(itsTarget.HasConsumer(consumer));
        }

        [Test]
        public void
        TestDispatch1()
        {

            Assert.AreEqual( 0,itsProducer1.GetCount() );
            Assert.AreEqual( 0,itsProducer2.GetCount() );
            Assert.AreEqual( 0,itsProducer3.GetCount() );

            Assert.AreEqual( 0,itsConsumer1.GetCount() );
            Assert.AreEqual( 0,itsConsumer2.GetCount() );
            Assert.AreEqual( 0,itsConsumer3.GetCount() );

            itsTarget.StartDispatching();

            itsProducer1.StartProducing();
            itsProducer2.StartProducing();
            itsProducer3.StartProducing();

            itsProducer1.StopProducing();
            itsProducer2.StopProducing();
            itsProducer3.StopProducing();

            Assert.AreEqual( MAX,itsProducer1.GetCount() );
            Assert.AreEqual( MAX,itsProducer2.GetCount() );
            Assert.AreEqual( MAX,itsProducer3.GetCount() );

            Assert.AreEqual( MAX,itsConsumer1.GetCount() );
            Assert.AreEqual( MAX,itsConsumer2.GetCount() );
            Assert.AreEqual( MAX,itsConsumer3.GetCount() );
        }

        [Test]
        public void
        TestDispatch2()
        {
            ICountRequestConsumer consumer = 
            new CountRequestConsumer(3);

            itsTarget.AttachConsumer( consumer );

            Assert.AreEqual( 0,itsProducer1.GetCount() );
            Assert.AreEqual( 0,itsProducer2.GetCount() );
            Assert.AreEqual( 0,itsProducer3.GetCount() );

            Assert.AreEqual( 0,itsConsumer1.GetCount() );
            Assert.AreEqual( 0,itsConsumer2.GetCount() );
            Assert.AreEqual( 0,itsConsumer3.GetCount() );

            itsTarget.StartDispatching();

            itsProducer1.StartProducing();
            itsProducer2.StartProducing();
            itsProducer3.StartProducing();

            itsProducer1.StopProducing();
            itsProducer2.StopProducing();
            itsProducer3.StopProducing();

            Assert.AreEqual( MAX,itsProducer1.GetCount() );
            Assert.AreEqual( MAX,itsProducer2.GetCount() );
            Assert.AreEqual( MAX,itsProducer3.GetCount() );

            Assert.AreEqual( MAX,itsConsumer1.GetCount() );
            Assert.AreEqual( MAX,itsConsumer2.GetCount() );
            Assert.AreEqual( MAX,itsConsumer3.GetCount()+consumer.GetCount() );
        }

        [Test]
        public void
        TestHasConsumer()
        {
            ICountRequestConsumer consumer = new CountRequestConsumer(100);

            Assert.IsFalse(itsTarget.HasConsumer(consumer));
            itsTarget.AttachConsumer(consumer);
            Assert.IsTrue(itsTarget.HasConsumer(consumer));
            itsTarget.DetachConsumer(consumer);
            Assert.IsFalse(itsTarget.HasConsumer(consumer));
        }

        [Test]
        public void
        TestStartDispatching()
        {
            Assert.IsFalse(itsTarget.IsDispatching());
            itsTarget.StartDispatching();
            Assert.IsTrue(itsTarget.IsDispatching());
        }

        [Test]
        public void
        TestStopDispatching()
        {
            Assert.IsFalse(itsTarget.IsDispatching());
            itsTarget.StartDispatching();
            Assert.IsTrue(itsTarget.IsDispatching());
            itsTarget.StopDispatching();
            Assert.IsFalse(itsTarget.IsDispatching());
        }

        [Test]
        public void
        TestIsDispatching()
        {
            Assert.IsFalse(itsTarget.IsDispatching());
            itsTarget.StartDispatching();
            Assert.IsTrue(itsTarget.IsDispatching());
            itsTarget.StopDispatching();
            Assert.IsFalse(itsTarget.IsDispatching());
        }

    }
}
