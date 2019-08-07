//  ##########################################################################
//  # File Name: TestModule.cs
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
    class TestModule:
        AbstractModule
    {
        public
        TestModule(): 
            base("TestModule")
        {
        }

        public override void 
        Initialize()
        {
            Bind<string>()
                .ToInstance( "XXXXXXX" )
                .WithKey( "test1" );


            Bind<string>()
                .ToInstance( "YYYYYYY" )
                .WithKey( "test2" );


            Bind<int>().ToInstance( 12345 );


            Bind<IFoo>()
                .ToType<Foo>()
                .WithScope<ThreadScope>();


            Bind<IBar>()
                .ToType<Bar>()
                .WithScope<ThreadScope>();

        }
}
}

//  ##########################################################################
