//  ##########################################################################
//  # File Name: ServiceDecoratorTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;
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
                new DecoratedContainer(
                    new NinjectContainer(new FooModule()),
                    new DecoratorMap()
                        .InsertDecorator<IFooService>(
                            new ServiceDecoratorFactory()));
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
                    Throw = false
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
                    Throw = true
                };
            GetFooReply reply = decorated.GetFoo(request);

            Assert.AreEqual(request.CorrelationId,reply.CorrelationId);
            Assert.IsFalse(reply.IsSuccessful);
            Assert.AreEqual("Foo Service Exception",reply.FailureMessage);
        }
    }
}

//  ##########################################################################
