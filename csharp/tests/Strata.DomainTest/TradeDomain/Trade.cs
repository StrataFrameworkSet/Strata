//  ##########################################################################
//  # File Name: Trade.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Strata.Foundation.Utility;
using Strata.Foundation.Value;

namespace Strata.Domain.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class Trade:
        ICopyable
    {
        public virtual long                     TradeKey { get; set; }
        public virtual int                      Version { get; set; }

        public virtual int                      TradeId { get; set; }
        public virtual int                      ExternalTradeId { get; set; }
        public virtual String                   Cusip { get; set; }
        public virtual TransactionType          TransactionType { get; set; }
        public virtual Money                    Price { get; set; }
        public virtual Money                    TradeAmount { get; set; }
        public virtual Decimal                  CouponRate { get; set; }
        public virtual int                      TraderId { get; set; }
        public virtual String                   BrokerCode { get; set; }
        public virtual DateTime                 TradeDate { get; set; }
        public virtual DateTime                 SettlementDate { get; set; }
        public virtual DateTime                 DeliveryDate { get; set; }
        public virtual IList<AccountAllocation> AccountAllocations { get; set; }
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        Trade()
        {
            TradeKey = 0;
            TradeId = 0;
            ExternalTradeId = 0;
            Cusip = String.Empty;
            Price = new Money(CurrencyManager.GetInstanceByCode("USD"),Decimal.Zero);
            TradeAmount = new Money(CurrencyManager.GetInstanceByCode("USD"),Decimal.Zero);
            TransactionType = TransactionType.BUY;
            TraderId = 0;
            BrokerCode = String.Empty;
            TradeDate = DateTime.MinValue;
            SettlementDate = DateTime.MinValue;
            DeliveryDate = DateTime.MinValue;
            AccountAllocations = new List<AccountAllocation>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        Trade(Trade other)
        {
            TradeKey = other.TradeKey;
            TradeId = other.TradeId;
            ExternalTradeId = other.ExternalTradeId;
            Cusip = other.Cusip;
            Price = other.Price;
            TradeAmount = other.TradeAmount;
            TransactionType = other.TransactionType;
            TraderId = other.TraderId;
            BrokerCode = other.BrokerCode;
            TradeDate = other.TradeDate;
            SettlementDate = other.SettlementDate;
            DeliveryDate = other.DeliveryDate;
            AccountAllocations = new List<AccountAllocation>();

            foreach (AccountAllocation allocation in other.AccountAllocations)
                AccountAllocations.Add((AccountAllocation)allocation.Clone());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual ICopyable 
        MakeCopy()
        {
            return new Trade(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override bool  
        Equals(Object obj)
        {
            if ( obj is Trade)
            {
                Trade other = (Trade)obj;

                return
                    TradeId.Equals(other.TradeId) &&
                    ExternalTradeId.Equals(other.ExternalTradeId) && 
                    Cusip.Equals(other.Cusip) &&
                    Price.Equals(other.Price) &&
                    TradeAmount.Equals(other.TradeAmount) &&
                    TransactionType.Equals(other.TransactionType) &&
                    TraderId.Equals(other.TraderId) &&
                    BrokerCode.Equals(other.BrokerCode) &&
                    TradeDate.Equals(other.TradeDate) &&
                    SettlementDate.Equals(other.SettlementDate) &&
                    DeliveryDate.Equals(other.DeliveryDate) &&
                    AllocationsEquals(other.AccountAllocations);
            }

            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override int  
        GetHashCode()
        {
		    return TradeId;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override String 
        ToString()
        {
            StringBuilder b = new StringBuilder();
            b.Append("Trade:");
            b.Append("  TradeKey:" + TradeKey);
            b.Append(", Version:" + Version);
            b.Append(", TradeId=" + TradeId);
            b.Append(", BrokerCode=" + BrokerCode);
            b.Append(", Cusip=" + Cusip);
            b.Append(", DeliveryDate=" + DeliveryDate);
            b.Append(", ExternalTradeId=" + ExternalTradeId);
            b.Append(", Price=" + Price);
            b.Append(", SettlementDate=" + SettlementDate);
            b.Append(", TradeAmount=" + TradeAmount);
            b.Append(", TraderId=" + TraderId);
            foreach (var allocation in AccountAllocations)
            {
                b.Append("\n" + allocation.ToString());
            }
            return b.ToString();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void 
        InsertAccountAllocation(AccountAllocation allocation)
        {
            if ( HasAccountAllocation(allocation.AccountAllocationId) )
                throw 
                    new ArgumentException(
                        "AccountAllocation <" + 
                        allocation.AccountAllocationId + 
                        "> is already inserted.");

            allocation.TradeId = TradeId;
            AccountAllocations.Add(allocation);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void 
        InsertManagerAllocation(ManagerAllocation allocation)
        {
            if ( HasManagerAllocation(allocation.ManagerAllocationId) )
                throw 
                    new ArgumentException(
                        "ManagerAllocation <" + 
                        allocation.AccountAllocationId + 
                        "> is already inserted.");

            if ( !HasAccountAllocation(allocation.AccountAllocationId) )
                throw 
                    new ArgumentException(
                        "AccountAllocation <" + 
                        allocation.AccountAllocationId + 
                        "> does not exist.");

             GetAccountAllocation(allocation.AccountAllocationId)
                .ManagerAllocations
                .Add(allocation);  
            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual bool
        HasAccountAllocation(int allocationId)
        {
            return
                AccountAllocations.Any(
                    a => a.AccountAllocationId == allocationId);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual bool
        HasManagerAllocation(int allocationId)
        {
            return
                AccountAllocations.Any(
                    a => a.ManagerAllocations.Any(
                        m => m.ManagerAllocationId == allocationId));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual bool
        IsUpdated(Object obj, bool includeAllocations)
        {
            if (obj is Trade)
            {
                Trade other = (Trade)obj;

                bool comparison =
                    !Cusip.Equals(other.Cusip) ||
                    !Price.Equals(other.Price) ||
                    !TradeAmount.Equals(other.TradeAmount) ||
                    !TransactionType.Equals(other.TransactionType) ||
                    !TraderId.Equals(other.TraderId) ||
                    !BrokerCode.Equals(other.BrokerCode) ||
                    !TradeDate.Equals(other.TradeDate) ||
                    !SettlementDate.Equals(other.SettlementDate) ||
                    !DeliveryDate.Equals(other.DeliveryDate);

                if (includeAllocations)
                    comparison = comparison || AllocationsAreUpdated(other.AccountAllocations);
                return comparison;
            }

            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void 
        StartUpdate()
        {
            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void 
        FinishUpdate()
        {
            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual void 
        Cancel()
        {
            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual AccountAllocation 
        GetAccountAllocation(int accountAllocId)
        {
            return
                AccountAllocations.FirstOrDefault(
                    a => a.AccountAllocationId == accountAllocId);
        }        

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public virtual ManagerAllocation 
        GetManagerAllocation(int mgrAllocId)
        {
            AccountAllocation allocation = 
                AccountAllocations.FirstOrDefault(
                    a => a.ManagerAllocations.Any(
                        m => m.ManagerAllocationId == mgrAllocId));

            if ( allocation == null )
                return null;

            return
                allocation
                    .ManagerAllocations
                    .FirstOrDefault(
                        m => m.ManagerAllocationId == mgrAllocId);
        }        

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool 
        AllocationsEquals(IList<AccountAllocation> other)
        {
            IEnumerator<AccountAllocation> x = null;
            IEnumerator<AccountAllocation> y = null;

            if ( AccountAllocations.Count != other.Count )
                return false;

            AccountAllocations.Sort();
            other.Sort();

            x = AccountAllocations.GetEnumerator();
            y = other.GetEnumerator();

            while (x.MoveNext() && y.MoveNext())
                if ( !x.Current.Equals(y.Current))
                    return false;
             
            return true;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool
        AllocationsAreUpdated(IList<AccountAllocation> other)
        {
            IEnumerator<AccountAllocation> x = null;
            IEnumerator<AccountAllocation> y = null;

            if (AccountAllocations.Count != other.Count)
                return false;

            AccountAllocations.Sort();
            other.Sort();

            x = AccountAllocations.GetEnumerator();
            y = other.GetEnumerator();

            while (x.MoveNext() && y.MoveNext())
                if (!x.Current.IsUpdated(y.Current))
                    return false;

            return true;
        }
    }
}

//  ##########################################################################
