//  ##########################################################################
//  # File Name: TestApplicationFactory.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using Ninject.Modules;
using Strata.Foundation.Bootstrap;
using Strata.Foundation.Injection;
using Strata.Ninject.Injection;
using AbstractModule = Strata.Ninject.Injection.AbstractModule;

namespace Strata.Ninject.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class TestApplicationFactory:
        NinjectApplicationFactory
    {
        public
        TestApplicationFactory()
        {
            AbstractModule.DefaultScope = typeof(ThreadScope);
        }

        public override void 
        BindStartStopController(INinjectModule module)
        {
            AbstractModule.SetDefaultScope(
                module
                    .Kernel
                    .Bind<IStartStopController>()
                    .To<TestStartStopController>());
        }

        protected override IList<INinjectModule> 
        CreateModules()
        {
            return
                new List<INinjectModule>()
                {
                    new TestModule(),
                    new FooBarModule()
                };
        }
    }
}

//  ##########################################################################
