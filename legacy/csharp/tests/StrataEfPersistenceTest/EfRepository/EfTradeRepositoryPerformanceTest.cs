using System;
using System.Collections.Generic;
using System.Linq;
using Strata.EfPersistence.TradeDomain;
using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.EfPersistence.EfRepository
{
    [Ignore]
    [TestClass]
    public
    class EfTradeRepositoryPerformanceTest
    {
        private static ITradeRepository repository;
        private static ISet<long> tradeKeys;
        private static ISet<int> tradeIds;

        [ClassInitialize]
        public static void
        ClassSetUp(TestContext c)
        {
            TradeGenerator generator = new TradeGenerator();

            repository =
                new TradeRepository(
                    new EfRepositoryProvider<long, Trade>(
                        new EfRepositoryContext<RepositoryTestContext>()));

            tradeKeys = new SortedSet<long>();
            tradeIds = new SortedSet<int>();


            for (int i = 0; i < 100; i++)
            {
                Trade trade = generator.CreateTrade();

                while (tradeIds.Contains(trade.TradeId))
                    trade = generator.CreateTrade();

                tradeIds.Add(trade.TradeId);
                trade = repository.InsertTrade(trade);
                tradeKeys.Add(trade.TradeKey);
            }

            repository.UnitOfWork.Commit();
        }

        [ClassCleanup]
        public static void
        ClassTearDown()
        {
            foreach (long tradeKey in tradeKeys)
            {
                Trade trade = null;
                trade = repository.GetTrade(tradeKey);

                if (trade == null)
                    throw new InvalidOperationException("Trade: " + tradeKey + " is null.");

                repository.RemoveTrade(trade);
            }

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
            int tradeId = tradeIds.First();
            bool expected = false;
#if DEBUG
            const double EXPECTED_DURATION =  130;
#else
            const double EXPECTED_DURATION = 100;
#endif

            start = DateTime.Now;
            expected = repository.HasTradeWithTradeId(tradeId);
            finish = DateTime.Now;
            duration = finish.Subtract(start);

            Assert.IsTrue(expected, "HasTradeWithTradeId returned false incorrectly.");
            Assert.IsTrue(
                duration.TotalMilliseconds < EXPECTED_DURATION,
                "HasTradeWithTradeId duration: " + duration.TotalMilliseconds + " milliseconds.");
        }

        [TestMethod]
        public void
        TestGetTradesWithManagerId()
        {
            Trade trade = repository.GetTrade(tradeKeys.Last());
            int managerId = trade.AccountAllocations.First().ManagerAllocations.First().ManagerId;
            IList<Trade> output;
            DateTime start;
            DateTime finish;
            TimeSpan duration;
#if DEBUG
            const double EXPECTED_DURATION =  100;
#else
            const double EXPECTED_DURATION = 75;
#endif

            start = DateTime.Now;
            output = repository.GetTradesWithManagerId(managerId);
            finish = DateTime.Now;
            duration = finish.Subtract(start);

            Assert.IsTrue(output.Count != 0);
            Assert.IsTrue(
                duration.TotalMilliseconds < EXPECTED_DURATION,
                "GetTradesWithManagerId duration: " + duration.TotalMilliseconds + " milliseconds.");
        }
    }
}
