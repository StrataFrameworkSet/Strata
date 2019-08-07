//  ##########################################################################
//  # File Name: FooService.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################


using Strata.Application.Service;
using Strata.Domain.UnitOfWork;
using Strata.Foundation.Logging;
using System;
using SystemEx.Injection;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class FooService:
        AbstractService,
        IFooService
    {
        [Inject]
        public
        FooService(IUnitOfWorkProvider provider,ILogger logger):
            base(provider,logger) {}

        public GetFooReply
        GetFoo(GetFooRequest request)
        {
            if (request.Throw != null)
                throw request.Throw;

            return
                new GetFooReply()
                {
                    IsSuccessful = true,
                    Foo = request.Foo
                };
        }
    }
}

//  ##########################################################################
