//  ##########################################################################
//  # File Name: FooService.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################


using System;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class FooService:
        IFooService
    {
        public GetFooReply
        GetFoo(GetFooRequest request)
        {
            if (request.Throw)
                throw new Exception("Foo Service Exception");

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
