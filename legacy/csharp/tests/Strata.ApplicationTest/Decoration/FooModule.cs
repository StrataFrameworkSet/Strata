//  ##########################################################################
//  # File Name: FooModule.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.InMemory;
using Strata.Domain.UnitOfWork;
using Strata.Foundation.Injection;
using Strata.Foundation.Logging;
using System;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class FooModule:
        AbstractModule
    {
        public 
        FooModule(): 
            base("FooModule") {}

        public override void 
        Initialize()
        {
            Bind<IUnitOfWorkProvider>()
                .ToType<InMemoryUnitOfWorkProvider>()
                .WithScope(DefaultScope);

            Bind<ILogger>()
                .ToType<ConsoleLogger>()
                .WithScope(DefaultScope);

            Bind<IFooService>()
                .ToType<FooService>()
                .WithScope(DefaultScope);
        }
    }
}

//  ##########################################################################
