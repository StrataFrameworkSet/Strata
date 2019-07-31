//  ##########################################################################
//  # File Name: TestModule.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Ninject.Injection;
using System;

namespace Strata.Ninject.Bootstrap
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
                .InThreadScope();

            Bind<IBar>()
                .To<Bar>()
                .InDefaultScope();
        }
    }
}

//  ##########################################################################
