//  ##########################################################################
//  # File Name: InterceptionTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject;
using NUnit.Framework;
using Strata.Ninject.Bootstrap;
using System;

namespace Strata.Ninject.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class InterceptionTest
    {
        private IKernel kernel;

        [SetUp]
        public void
        SetUp()
        {
            kernel = new StandardKernel(new TestModule());
        }

        [Test]
        public void
        TestDoSomething()
        {
            IFoo target = kernel.Get<IFoo>();

            target.DoSomething(false);

            try
            {
                target.DoSomething(true);
            }
            catch (Exception e)
            {
                Assert.AreEqual("Foo",e.Message);
            }
        }
    }
}

//  ##########################################################################
