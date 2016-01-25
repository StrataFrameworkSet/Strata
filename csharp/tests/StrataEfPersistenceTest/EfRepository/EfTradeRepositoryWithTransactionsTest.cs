using System.Data.Entity;
using Strata.EfPersistence.TradeDomain;
using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.EfPersistence.EfRepository
{
    [TestClass]
    public class EfTradeRepositoryWithTransactionsTest :
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
        public override void TestGetTradeContainingAccountAllocation()
        {
            base.TestGetTradeContainingAccountAllocation();
        }

        [Ignore] // Entity Framework 5: sql queries cannot map complex properties like Money
        [TestMethod]
        public override void TestGetTradesWithManagerId()
        {
            base.TestGetTradesWithManagerId();
        }
        
        protected override IRepositoryProvider<long,Trade>
        CreateRepositoryProvider()
        {
            IEfRepositoryContext context =
                new EfRepositoryContext<RepositoryTestContext>();

            Database
                .SetInitializer(
                    new EfDropCreateDatabaseIfModelChanges<RepositoryTestContext>() );

            new EfFinder<Trade>( 
                context,
                "Trade.GetTradesWithManagerId",
                @"  select 
                    distinct t.*
                    from     Trade t,AccountAllocation a,ManagerAllocation m
                    where    t.TradeKey = a.TradeKey and
                             a.AccountAllocationKey = m.AccountAllocationKey and
                             m.ManagerId = @id" );

            return
                new EfRepositoryProvider<long,Trade>(context);
        }
    }
}
