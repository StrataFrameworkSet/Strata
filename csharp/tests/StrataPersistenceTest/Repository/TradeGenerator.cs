//  ##########################################################################
//  # File Name: TradeGenerator.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using Strata.Common.MoneyDomain;
using Strata.Persistence.TradeDomain;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class TradeGenerator
    {
        private Random    generator;
        private bool      createKeys;
        private ISet<int> accountKeys;
        private ISet<int> managerKeys; 

        public 
        TradeGenerator():
            this(true) {}

        public 
        TradeGenerator(bool create)
        {
            generator = new Random();
            createKeys = create;
            accountKeys = new HashSet<int>();
            managerKeys = new HashSet<int>();
        }

        public Trade
        CreateTrade()
        {
            Trade trade = new Trade();

            if ( createKeys )
                trade.TradeKey = generator.Next(1,int.MaxValue);

            trade.TradeAmount = new Money(CurrencyManager.GetInstanceByCode("USD"), 1000);
            trade.BrokerCode = "BRKR";
            trade.Cusip = "123456789";
            trade.DeliveryDate = new DateTime(2012,10,20);
            trade.ExternalTradeId = 123;
            trade.Price = new Money(CurrencyManager.GetInstanceByCode("USD"), 100);
            trade.SettlementDate = new DateTime(2012, 10, 20);
            trade.TradeDate = new DateTime(2012, 10, 20);
            trade.TradeId = generator.Next(1,int.MaxValue);
            trade.TraderId = 5;
            trade.TransactionType = TransactionType.BUY;
            trade.AccountAllocations = new List<AccountAllocation>(2)
                                    {
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue)),
                                        CreateTestAccountAllocation(trade.TradeId, generator.Next(1,int.MaxValue))
                                    };

            return trade;
        }

        public int 
        CreateExternalTradeId()
        {
            return generator.Next(1,int.MaxValue);
        }

        public AccountAllocation
        CreateAccountAllocation(Trade trade)
        {
            int id = generator.Next(1,int.MaxValue);

            while ( accountKeys.Contains( id  ))
                id = generator.Next( 1,int.MaxValue );
            
            accountKeys.Add( id );
            return CreateTestAccountAllocation( trade.TradeId,id );
        }

        public ManagerAllocation
        CreateManagerAllocation(AccountAllocation allocation)
        {
            int id = generator.Next(1,int.MaxValue);

            while ( managerKeys.Contains( id  ))
                id = generator.Next( 1,int.MaxValue );
            
            managerKeys.Add( id );
            return CreateTestManagerAllocation( allocation.AccountAllocationId,id );
        }

        protected AccountAllocation 
        CreateTestAccountAllocation(int tradeId, int allocId)
        {
            AccountAllocation alloc = new AccountAllocation();
            alloc.AccountCode = "1001";
            alloc.AccountAmount = new Money(CurrencyManager.GetInstanceByCode("USD"), 500);
            alloc.Comment = "Hello";
            alloc.DownstreamAllocationIdOriginal = allocId *2;
            alloc.DownstreamAllocationIdLatest = allocId *5;
            alloc.ExternalAccountAllocationId = allocId *10;
            alloc.AccountAllocationId = allocId;
            alloc.TradeId = tradeId;
            alloc.ManagerAllocations = new List<ManagerAllocation>()
                                           {
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc),
                                               CreateManagerAllocation(alloc)
                                           };
            return alloc;
        }

        protected ManagerAllocation 
        CreateTestManagerAllocation(int allocId,int mAllocId)
        {
            ManagerAllocation alloc = new ManagerAllocation();
            alloc.ManagerAmount = new Money(CurrencyManager.GetInstanceByCode("USD"), 250);
            alloc.AccountAllocationId = allocId;
            alloc.ManagerAllocationId = mAllocId;
            alloc.ManagerId = generator.Next(0, 9999);
            return alloc;
        }

    }
}

//  ##########################################################################
