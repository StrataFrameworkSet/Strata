//  ##########################################################################
//  # File Name: TestModule.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Extensions.Interception.Infrastructure.Language;
using Strata.Ninject.Bootstrap;
using Strata.Ninject.Injection;
using System;

namespace Strata.Ninject.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class TestModule:
        AbstractModule
    {
        public override void 
        Load()
        {
            Bind<string>()
                .ToConstant("XXXXXXX")
                .Named("test1");


            Bind<string>()
                .ToConstant("YYYYYYY")
                .Named("test2");


            Bind<int>().ToConstant(12345);

            Bind<IFoo>()
                .To<Foo>()
                .InDefaultScope()
                .Intercept()
                .With<TestInterceptor>();

            Bind<IBar>()
                .To<Bar>()
                .InDefaultScope();
        }
    }
}

//  ##########################################################################
