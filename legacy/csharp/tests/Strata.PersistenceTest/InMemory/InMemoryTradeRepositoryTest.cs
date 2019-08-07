using NUnit.Framework;
using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Strata.Persistence.UnitOfWork;

namespace Strata.Persistence.InMemory
{
    [TestFixture]
    public class InMemoryTradeRepositoryTest:
        TradeRepositoryTest
    {
        [SetUp]
        public override void SetUp()
        {
            base.SetUp();
        }

        [TearDown]
        public override void TearDown()
        {
            base.TearDown();
        }

        [Test]
        public override void TestInsertTrade()
        {
            base.TestInsertTrade();
        }

        [Test]
        public override void TestUpdateTrade()
        {
            base.TestUpdateTrade();
        }

        [Test]
        public override void TestRemoveTrade()
        {
            base.TestRemoveTrade();
        }

        [Test]
        public override void TestGetTrade()
        {
            base.TestGetTrade();
        }

        [Test]
        public override void TestGetAllTrades()
        {
            base.TestGetAllTrades();
        }

        [Test]
        public override void TestGetTradeContainingAccountAllocation()
        {
            base.TestGetTradeContainingAccountAllocation();
        }

        [Test]
        public override void TestGetTradesWithManagerId()
        {
            base.TestGetTradesWithManagerId();
        }

        protected override IUnitOfWorkProvider
        CreateUnitOfWorkProvider()
        {
            InMemoryUnitOfWorkProvider provider =
                new InMemoryUnitOfWorkProvider();

            provider
                .InsertReplicator(new TradeReplicator())
                .InsertRetriever<long,Trade>(TradeRepository.GetKey)
                .InsertNamedQuery(
                    new InMemoryNamedQuery<Trade>(
                        provider,
                        TradeRepository.ManagerIdEqualsFinder,
                        new ManagerIdEqualsPredicate()));
        
            return provider;
        }
    }
}
