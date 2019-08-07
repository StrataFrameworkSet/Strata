//  ##########################################################################
//  # File Name: IAccountAllocationDomainEventObserver.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Shared;

namespace Strata.Domain.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IAccountAllocationDomainEventObserver:
        IDomainEventObserver<IAccountAllocationDomainEvent>
    {
    }
}

//  ##########################################################################
