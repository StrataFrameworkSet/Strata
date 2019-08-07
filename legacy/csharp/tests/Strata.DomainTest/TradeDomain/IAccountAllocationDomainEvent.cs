//  ##########################################################################
//  # File Name: IAccountAllocationDomainEvent.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Shared;
using System;

namespace Strata.Domain.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IAccountAllocationDomainEvent:
        IDomainEvent<AccountAllocation>
    {
    }
}

//  ##########################################################################
