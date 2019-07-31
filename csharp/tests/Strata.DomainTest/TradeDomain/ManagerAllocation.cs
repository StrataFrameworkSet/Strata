//  ##########################################################################
//  # File Name: ManagerAllocation.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Value;
using System;
using System.Text;

namespace Strata.Domain.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public partial 
    class ManagerAllocation: 
        IComparable
    {
        public virtual long  ManagerAllocationKey { get; set; }
        public virtual long  AccountAllocationKey { get; set; }
        public virtual int   Version { get; set; }

        public virtual int   ManagerAllocationId { get; set; }
        public virtual int   AccountAllocationId { get; set; }
        public virtual int   ManagerId { get; set; }
        public virtual Money ManagerAmount { get; set; }
        public virtual int   ListIndex { get; set; }

        public 
        ManagerAllocation()
        {
            ManagerAllocationKey = 0;
            AccountAllocationKey = 0;
            ManagerAllocationId = 0;
            AccountAllocationId = 0;
            ManagerId = 0;
            ManagerAmount = new Money(CurrencyManager.GetInstanceByCode("USD"),Decimal.Zero);
        }

        public 
        ManagerAllocation(ManagerAllocation other)
        {
            ManagerAllocationKey = other.ManagerAllocationKey;
            AccountAllocationKey = other.AccountAllocationKey;
            ManagerAllocationId = other.ManagerAllocationId;
            AccountAllocationId = other.AccountAllocationId;
            ManagerId = other.ManagerId;
            ManagerAmount = other.ManagerAmount;
        }

        public virtual Object
        Clone()
        {
            return new ManagerAllocation(this);
        }

        public override bool
        Equals(Object obj)
        {
            if (obj is ManagerAllocation)
            {
                ManagerAllocation other = (ManagerAllocation) obj;
                return ManagerAllocationId.Equals(other.ManagerAllocationId) &&
                       AccountAllocationId.Equals(other.AccountAllocationId) &&
                       ManagerId.Equals(other.ManagerId) &&                      
                       ManagerAmount.Equals(other.ManagerAmount);
            }
            return false;
        }

        public virtual bool
        IsUpdated(ManagerAllocation other)
        {

            return 
                !ManagerId.Equals(other.ManagerId) ||
                !ManagerAmount.Equals(other.ManagerAmount) ;

        }

        public override int
        GetHashCode()
        {
            return ManagerAllocationId;
        }

        public virtual int 
        CompareTo(object obj)
        {
            AccountAllocation other = (AccountAllocation) obj;
            return ManagerAllocationId.CompareTo(other.AccountAllocationId);
        }

        public override string ToString()
        {
            StringBuilder b = new StringBuilder();
            b.Append("Manager:");
            b.Append("  ManagerAllocationKey=" + ManagerAllocationKey);
            b.Append(", Version:" + Version);
            b.Append(", ManagerAllocationId=" + ManagerAllocationId);
            b.Append(", ManagerId=" + ManagerId);
            b.Append(", ManagerAmount=" + ManagerAmount);
            return b.ToString();
        }
    }
}

//  ##########################################################################
