//  ##########################################################################
//  # File Name: TradeAllocation.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Strata.Common.MoneyDomain;

namespace Strata.Persistence.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public partial 
    class AccountAllocation: 
        IComparable
    {
        public virtual long                     AccountAllocationKey { get; set; }
        public virtual long                     TradeKey { get; set; }
        public virtual int                      Version { get; set; }

        public virtual int                      AccountAllocationId { get; set; }
        public virtual int                      TradeId { get; set; }
        public virtual int                      DownstreamAllocationIdOriginal { get; set; }
        public virtual int                      DownstreamAllocationIdLatest { get; set; }
        public virtual int                      ExternalAccountAllocationId { get; set; }
        public virtual String                   AccountCode { get; set; }
        public virtual Money                    AccountAmount { get; set; }
        public virtual String                   Comment { get; set; }
        public virtual int                      ListIndex { get; set; }
        public virtual IList<ManagerAllocation> ManagerAllocations { get; set; }

        public 
        AccountAllocation()
        {
            AccountAllocationKey = 0;
            TradeKey = 0;
            AccountAllocationId = 0;
            TradeId = 0;
            DownstreamAllocationIdOriginal = 0;
            DownstreamAllocationIdLatest = 0;
            ExternalAccountAllocationId = 0;
            AccountCode = String.Empty;
            AccountAmount = new Money(CurrencyManager.GetInstanceByCode("USD"),Decimal.Zero);
            Comment = String.Empty;
            ManagerAllocations = new List<ManagerAllocation>();
        }

        public 
        AccountAllocation(AccountAllocation other)
        {
            AccountAllocationKey = other.AccountAllocationKey;
            TradeKey = other.TradeKey;
            AccountAllocationId = other.AccountAllocationId;
            TradeId = other.TradeId;
            DownstreamAllocationIdOriginal = other.DownstreamAllocationIdOriginal;
            DownstreamAllocationIdLatest = other.DownstreamAllocationIdLatest;
            ExternalAccountAllocationId = other.ExternalAccountAllocationId;
            AccountCode = other.AccountCode;
            AccountAmount = other.AccountAmount;
            Comment = other.Comment;

            ManagerAllocations = new List<ManagerAllocation>();

            foreach (ManagerAllocation allocation in other.ManagerAllocations)
                ManagerAllocations.Add((ManagerAllocation)allocation.Clone());
            
        }

        public virtual Object
        Clone()
        {
            return new AccountAllocation(this);
        }

        public override bool
        Equals(Object obj)
        {
            if (obj is AccountAllocation)
            {
                AccountAllocation other = (AccountAllocation) obj;
                return AccountAllocationId.Equals(other.AccountAllocationId) &&
                       TradeId.Equals(other.TradeId) &&
                       DownstreamAllocationIdOriginal.Equals(other.DownstreamAllocationIdOriginal) &&
                       DownstreamAllocationIdLatest.Equals(other.DownstreamAllocationIdLatest) &&
                       ExternalAccountAllocationId.Equals(other.ExternalAccountAllocationId) &&
                       String.Equals(AccountCode, other.AccountCode) &&
                       AccountAmount.Equals(other.AccountAmount) &&
                       String.Equals(Comment, other.Comment);
            }
            return false;
        }

        public virtual bool
        IsUpdated(Object obj)
        {
            if (obj is AccountAllocation)
            {
                AccountAllocation other = (AccountAllocation)obj;
                return !String.Equals(AccountCode, other.AccountCode) ||
                       !DownstreamAllocationIdLatest.Equals(other.DownstreamAllocationIdLatest) ||
                       !AccountAmount.Equals(other.AccountAmount) ||
                       !String.Equals(Comment, other.Comment);
            }
            return false;
        }

        public virtual ManagerAllocation 
        GetManagerAllocation(int managerId)
        {
            return
                ManagerAllocations
                    .FirstOrDefault(m => m.ManagerId == managerId);
        }

        public override int
        GetHashCode()
        {
            return AccountAllocationId;
        }

        public virtual int 
        CompareTo(object obj)
        {
            AccountAllocation other = (AccountAllocation) obj;
            return AccountAllocationId.CompareTo(other.AccountAllocationId);
        }

        public override string ToString()
        {
            StringBuilder b = new StringBuilder();
            b.Append("Allocation:");
            b.Append("  AccountAllocationKey=" + AccountAllocationKey);
            b.Append(", Version:" + Version);
            b.Append(", AccountAllocationId=" + AccountAllocationId);
            b.Append(", DownstreamAllocationIdOriginal=" + DownstreamAllocationIdOriginal);
            b.Append(", DownstreamAllocationIdLatest=" + DownstreamAllocationIdLatest);
            b.Append(", AccountAmount=" + AccountAmount);
            b.Append(", AccountCode=" + AccountCode);
            b.Append(", Comment=" + Comment);
                
            foreach (var manager in ManagerAllocations)
            {
                b.Append("\n" + manager.ToString());
            }
            return b.ToString();
        }

    }
}

//  ##########################################################################
