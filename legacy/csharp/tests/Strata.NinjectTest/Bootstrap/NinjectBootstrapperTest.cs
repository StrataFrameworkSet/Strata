//  ##########################################################################
//  # File Name: NinjectBootstrapperTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject;
using NUnit.Framework;
using Strata.Foundation.Bootstrap;
using Strata.Foundation.Logging;
using System;

namespace Strata.Ninject.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class NinjectBootstrapperTest
    {
        private INinjectBootstrapper       itsTarget;
        private INinjectApplicationFactory itsFactory;

        [SetUp]
        public virtual void
        SetUp()
        {
            itsFactory = new TestApplicationFactory();
            itsTarget = new NinjectBootstrapper();
        }

        [Test]
        public virtual void
        TestRun()
        {
            itsTarget.Run(itsFactory);

            Assert.NotNull(itsTarget.Container);
        }

        [Test]
        public virtual void
        TestGetLogger()
        {
            IKernel kernel = null;
            ILogger logger = null;

            itsTarget.Run(itsFactory);
            kernel = itsTarget.Container;

            logger = kernel.Get<ILogger>();

            Assert.NotNull(logger);
            Assert.AreEqual(typeof(NullLogger),logger.GetType());
        }

        [Test]
        public virtual void
        TestGetStartStopController()
        {
            IKernel              kernel = null;
            IStartStopController controller = null;

            itsTarget.Run(itsFactory);
            kernel = itsTarget.Container;

            controller = kernel.Get<IStartStopController>();

            Assert.NotNull(controller);
            Assert.AreEqual(typeof(TestStartStopController),controller.GetType());
        }

        [Test]
        public virtual void
        TestGetInstance()
        {
            IFooBar foobar = null;
            IKernel kernel = null;

            itsTarget.Run(itsFactory);
            kernel = itsTarget.Container;

            foobar = kernel.Get<IFooBar>();

            Assert.NotNull(foobar);
            Assert.NotNull(foobar.Foo);
            Assert.AreEqual("XXXXXXX",foobar.Foo.FooName);
            Assert.NotNull(foobar.Bar);
            Assert.AreEqual(12345,foobar.Bar.BarId);
            Assert.AreEqual("YYYYYYY",foobar.Baz);

        }

        [Test]
        public virtual void
        TestGetInstanceOfSelfInjectedContainer()
        {
            IKernel kernel = null;
            IKernel self = null;

            itsTarget.Run(itsFactory);
            kernel = itsTarget.Container;
            self = kernel.Get<IKernel>();

            Assert.NotNull(self);
            Assert.AreEqual(kernel,self);
        }
    }
}

//  ##########################################################################
