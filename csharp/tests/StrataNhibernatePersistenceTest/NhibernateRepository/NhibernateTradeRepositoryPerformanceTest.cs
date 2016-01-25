using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Strata.Common.Utility;
using Strata.NhibernatePersistence.NhibernateRepository;
using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.NhibernatePeristence.NhibernateRepository
{
    [TestClass]
    public 
    class NhibernateTradeRepositoryPerformanceTest
    {
        private static ITradeRepository repository;
        private static ISet<long>       tradeKeys;
        private static ISet<int>        tradeIds;

        [ClassInitialize]
        public static void
        ClassSetUp(TestContext c)
        {
            TradeGenerator generator = new TradeGenerator();

            repository = 
                new TradeRepository(                 
                    new NhibernateRepositoryProvider<long,Trade>(
                        new NhibernateRepositoryContext(
                            new String[]{ "StrataPersistenceTest","StrataNhibernatePersistenceTest" } ) ) );

            tradeKeys = new SortedSet<long>();
            tradeIds = new SortedSet<int>();
 
            
            for (int i=0;i<10;i++)
            {
                Trade trade = generator.CreateTrade();
                
                while ( tradeIds.Contains( trade.TradeId ))
                    trade = generator.CreateTrade();

                tradeIds.Add( trade.TradeId );
                trade = repository.InsertTrade( trade );
                tradeKeys.Add( trade.TradeKey );
            }

            repository.UnitOfWork.Commit();
        }

        [ClassCleanup]
        public static void  
        ClassTearDown()
        {   
            repository.UnitOfWork.Commit();
            repository.RemoveTradesWithTradeIds( tradeIds );
            repository.UnitOfWork.Commit();     
            tradeIds.Clear();
            tradeKeys.Clear();
            repository = null;
        }

        [TestInitialize]
        public void
        SetUp()
        {
        }

        [TestCleanup]
        public void
        TearDown()
        {
        }        

        [TestMethod]
        public void 
        TestHasTradeWithTradeId()
        {
            DateTime start;
            DateTime finish;
            TimeSpan duration;
            int      tradeId = tradeIds.First();
            bool     expected = false;
            #if DEBUG
            const double EXPECTED_DURATION =  120;
            #else
            const double EXPECTED_DURATION =  100;
            #endif

            start = DateTime.Now;
            expected = repository.HasTradeWithTradeId( tradeId );          
            finish = DateTime.Now;
            duration = finish.Subtract( start );

            Assert.IsTrue( expected,"HasTradeWithTradeId returned false incorrectly." );
            Assert.IsTrue( 
                duration.TotalMilliseconds < EXPECTED_DURATION,
                "HasTradeWithTradeId duration: " + duration.TotalMilliseconds + " milliseconds.");
        }

        [TestMethod]
        public void 
        TestGetTradesWithManagerId()
        {
            Trade        trade = repository.GetTrade( tradeKeys.Last() );
            int          managerId = trade.AccountAllocations.First().ManagerAllocations.First().ManagerId;
            IList<Trade> output;
            DateTime     start;
            DateTime     finish;
            TimeSpan     duration;
            #if DEBUG
            const double EXPECTED_DURATION =  75;
            #else
            const double EXPECTED_DURATION =  50;
            #endif
            
            start = DateTime.Now;
            output = repository.GetTradesWithManagerId( managerId );
            repository.UnitOfWork.Commit();
            finish = DateTime.Now;
            duration = finish.Subtract( start );

            Assert.IsTrue( output.Count != 0 );

            try
            {
                Assert.IsTrue( 
                    duration.TotalMilliseconds < EXPECTED_DURATION,
                    "GetTradesWithManagerId duration: " + duration.TotalMilliseconds + " milliseconds.");
                
            }
            catch (Exception e)
            {
                Console
                    .Out
                    .WriteLine( e.Message );   
            }


            start = DateTime.Now;
            output = repository.GetTradesWithManagerId( managerId );
            repository.UnitOfWork.Commit();
            finish = DateTime.Now;
            duration = finish.Subtract( start );

            Assert.IsTrue( output.Count != 0 );

            Assert.IsTrue( 
                duration.TotalMilliseconds < EXPECTED_DURATION / 2,
                "GetTradesWithManagerId(cached) duration: " + duration.TotalMilliseconds + " milliseconds.");

            Console
                .Out
                .WriteLine( "GetTradesWithManagerId(cached) duration: " + duration.TotalMilliseconds + " milliseconds." );
        }

        [TestMethod]
        public void 
        TestCacheInvalidation()
        {
            Trade             trade = null;
            AccountAllocation accountAllocation = null;
            ManagerAllocation managerAllocation = null;

            Trade             updatedTrade = null;
            AccountAllocation updatedAccountAllocation = null;
            ManagerAllocation updatedManagerAllocation = null;

            IUnitOfWork       unitOfWork = repository.UnitOfWork;
            IRepositoryMethod update = null;

            Console.Out.WriteLine("Debug 1");
            trade = repository.GetTrade( tradeKeys.Last() ).Copy();
            Console.Out.WriteLine("Debug 2");
            accountAllocation = trade.AccountAllocations.First();
            managerAllocation = trade.AccountAllocations.Last().ManagerAllocations.First();
            unitOfWork.Commit();
            
            unitOfWork = repository.UnitOfWork;
            update = unitOfWork.GetRepositoryMethod( "Trade.UpdateTrade" );

            update.SetInput( "tradeId",trade.TradeId  );
            update.SetInput( "accountAllocationId",accountAllocation.AccountAllocationId );
            update.SetInput( "managerAllocationId",managerAllocation.ManagerAllocationId );     
            update.Execute();

            unitOfWork.Commit();

            unitOfWork = repository.UnitOfWork;
            Console.Out.WriteLine("Debug 3");
            updatedTrade = repository.GetTrade( trade.TradeKey ).Copy();
            updatedAccountAllocation = updatedTrade.AccountAllocations.First();
            updatedManagerAllocation = updatedTrade.AccountAllocations.Last().ManagerAllocations.First();
            unitOfWork.Commit();

            Console.Out.WriteLine("Debug 4");

            Assert.AreNotEqual( trade.TraderId,updatedTrade.TraderId,"TraderId" );
            Assert.AreNotEqual( accountAllocation.Comment,updatedAccountAllocation.Comment,"AccountAllocation Comment" );
        }
    }
}
