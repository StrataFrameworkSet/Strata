using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Persistence.InMemoryRepository
{
    [TestClass]
    public class InMemoryTradeRepositoryTest:
        TradeRepositoryTest
    {
        [TestInitialize]
        public override void SetUp()
        {
            base.SetUp();
        }

        [TestCleanup]
        public override void TearDown()
        {
            base.TearDown();
        }

        [TestMethod]
        public override void TestInsertTrade()
        {
            base.TestInsertTrade();
        }

        [TestMethod]
        public override void TestUpdateTrade()
        {
            base.TestUpdateTrade();
        }

        [TestMethod]
        public override void TestRemoveTrade()
        {
            base.TestRemoveTrade();
        }

        [TestMethod]
        public override void TestGetTrade()
        {
            base.TestGetTrade();
        }

        [TestMethod]
        public override void TestGetAllTrades()
        {
            base.TestGetAllTrades();
        }

        [TestMethod]
        public override void TestGetTradeContainingAccountAllocation()
        {
            base.TestGetTradeContainingAccountAllocation();
        }

        [TestMethod]
        public override void TestGetTradesWithManagerId()
        {
            base.TestGetTradesWithManagerId();
        }

        protected override IRepositoryProvider<long,Trade> 
        CreateRepositoryProvider()
        {
            var p = new InMemoryRepositoryProvider<long, Trade>(
                new InMemoryRepositoryContext(),
                new TradeReplicator(),
                TradeRepository.GetKey);

            p.CreateFinder( 
                TradeRepository.ManagerIdEqualsFinder,
                new ManagerIdEqualsPredicate() );
            return p;
        }
    }
}
