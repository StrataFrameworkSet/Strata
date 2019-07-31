//  ##########################################################################
//  # File Name: IFooService.cs
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
    interface IFooService
    {
        GetFooReply
        GetFoo(GetFooRequest request);
    }
}

//  ##########################################################################
