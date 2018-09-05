//  ##########################################################################
//  # File Name: ServiceDecoratorTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject;
using NUnit.Framework;
using Strata.Domain.UnitOfWork;
using Strata.Foundation.Injection;
using Strata.Ninject.Injection;
using System;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class ServiceDecoratorTest
    {
        private IContainer itsContainer;

        [SetUp]
        public void
        SetUp()
        {
            itsContainer =
                new NinjectDecoratedContainer(
                    new DecoratorMap()
                        .InsertDecorator<IFooService>(
                            new ServiceDecoratorFactory(3)),
                    new FooModule());
        }

        [TearDown]
        public void
        TearDown()
        {
            itsContainer = null;
        }

        [Test]
        public void
        TestIntercept()
        {
            IFooService decorated = itsContainer.GetInstance<IFooService>();
            GetFooRequest request =
                new GetFooRequest()
                {
                    CorrelationId = Guid.NewGuid(),
                    Foo = "XXXXXXXXXXXX",
                    Throw = null
                };
            GetFooReply reply = decorated.GetFoo(request);

            Assert.AreEqual(request.CorrelationId,reply.CorrelationId);
            Assert.IsTrue(reply.IsSuccessful);
            Assert.AreEqual(request.Foo,reply.Foo);
        }

        [Test]
        public void
        TestInterceptFromKernel()
        {
            IKernel     kernel = (IKernel)itsContainer;
            IFooService decorated = kernel.Get<IFooService>();
            GetFooRequest request =
                new GetFooRequest()
                {
                    CorrelationId = Guid.NewGuid(),
                    Foo = "XXXXXXXXXXXX",
                    Throw = null
                };
            GetFooReply reply = decorated.GetFoo(request);

            Assert.AreEqual(request.CorrelationId,reply.CorrelationId);
            Assert.IsTrue(reply.IsSuccessful);
            Assert.AreEqual(request.Foo,reply.Foo);
        }

        [Test]
        public void
        TestInterceptWithException()
        {
            IFooService decorated = itsContainer.GetInstance<IFooService>();
            GetFooRequest request =
                new GetFooRequest()
                {
                    CorrelationId = Guid.NewGuid(),
                    Foo = "XXXXXXXXXXXX",
                    Throw = 
                        new CommitFailedException(
                            "commit failed",
                            new Exception("Foo Service Exception"))
                };
            GetFooReply reply = decorated.GetFoo(request);

            Assert.AreEqual(request.CorrelationId,reply.CorrelationId);
            Assert.IsFalse(reply.IsSuccessful);
            Assert.AreEqual(
                "commit failed (caused by Exception: Foo Service Exception)",
                reply.FailureMessage);
        }

        [Test]
        public void
        TestInterceptWithOptimisticLockException()
        {
            IFooService decorated = itsContainer.GetInstance<IFooService>();
            GetFooRequest request =
                new GetFooRequest()
                {
                    CorrelationId = Guid.NewGuid(),
                    Foo = "XXXXXXXXXXXX",
                    Throw = new OptimisticLockException("Foo has been changed")
                };
            GetFooReply reply = decorated.GetFoo(request);

            Assert.AreEqual(request.CorrelationId,reply.CorrelationId);
            Assert.IsFalse(reply.IsSuccessful);
            Assert.AreEqual("Foo has been changed",reply.FailureMessage);
        }
    }
}

//  ##########################################################################
