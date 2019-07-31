//  ##########################################################################
//  # File Name: NinjectContainerTest.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;
using Strata.Foundation.Injection;
using System;

namespace Strata.Ninject.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class NinjectContainerTest:
        ContainerTest
    {
        [Test]
        public override void
        TestGetInstance()
        {
            base.TestGetInstance();
        }

        protected override IContainer 
        CreateTarget(params IModule[] modules)
        {
            return new NinjectContainer(modules);
        }
    }
}

//  ##########################################################################
