//  ##########################################################################
//  # File Name: ContainerTest.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;
using System;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public abstract
    class ContainerTest
    {
        private IContainer itsTarget;

        [SetUp]
        public void
        SetUp()
        {
            itsTarget = 
                CreateTarget(
                    new TestModule(),
                    new FooBarModule());
        }

        [TearDown]
        public void
        TearDown()
        {
            itsTarget = null;
        }

        [Test]
        public virtual void
        TestGetInstance()
        {
            IFooBar foobar = null;

            foobar = itsTarget.GetInstance<IFooBar>();

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
            IContainer container = itsTarget.GetInstance<IContainer>();
        
            Assert.NotNull( container );
            Assert.AreEqual( itsTarget,container );
        }

        protected abstract IContainer
        CreateTarget(params IModule[] modules);
    }
}

//  ##########################################################################
