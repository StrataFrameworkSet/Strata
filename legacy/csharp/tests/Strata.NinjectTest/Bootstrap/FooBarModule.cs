//  ##########################################################################
//  # File Name: FooBarModule.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################


using Strata.Ninject.Injection;

namespace Strata.Ninject.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class FooBarModule:
        AbstractModule
    {
        public
        FooBarModule()
        {
        }

        public override void 
        Load()
        {
            Bind<IFooBar>()
                .To<FooBar>()
                .InSingletonScope();        
        }
}
}

//  ##########################################################################
