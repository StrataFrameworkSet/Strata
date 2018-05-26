//  ##########################################################################
//  # File Name: FooBarModule.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Injection
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
        FooBarModule(): 
            base("FooBarModule")
        {
        }

        public override void 
        Initialize()
        {
            Bind<IFooBar>()
                .ToType<FooBar>()
                .WithScope<SingletonScope>();        
        }
}
}

//  ##########################################################################
