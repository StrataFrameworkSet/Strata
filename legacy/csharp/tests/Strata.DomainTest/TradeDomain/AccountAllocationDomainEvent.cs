//  ##########################################################################
//  # File Name: AccountAllocationDomainEvent.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Shared;

namespace Strata.Domain.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class AccountAllocationDomainEvent:
        AbstractDomainEvent<AccountAllocation>,
        IAccountAllocationDomainEvent
    {
        public 
        AccountAllocationDomainEvent(string name,AccountAllocation source): 
            base(name,source) {}
    }
}

//  ##########################################################################
