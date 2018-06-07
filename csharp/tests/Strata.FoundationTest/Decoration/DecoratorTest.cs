//  ##########################################################################
//  # File Name: DecoratorTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;
using System;

namespace Strata.Foundation.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class DecoratorTest
    {
        private IDecoratorFactory itsFactory;

        [SetUp]
        public void
        SetUp()
        {
            itsFactory = new DecoratorFactory<TestDecorator>();
        }

        [Test]
        public void
        TestDoSomething()
        {
            IFoo target    = new Foo();
            IFoo decorated = itsFactory.Create(target);

            decorated.DoSomething(false);

            try
            {
                decorated.DoSomething(true);
            }
            catch (Exception e)
            {
                Assert.AreEqual("Foo",e.Message);
            }
        }
    }
}

//  ##########################################################################
