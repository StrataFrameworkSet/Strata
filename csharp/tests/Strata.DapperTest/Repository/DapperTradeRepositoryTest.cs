using NUnit.Framework;
using Strata.Dapper.UnitOfWork;
using Strata.Domain.Repository;
using Strata.Domain.TradeDomain;
using Strata.Domain.UnitOfWork;
using System.Configuration;

namespace Strata.Dapper.Repository
{
    [Ignore("Dapper does not support complex mappings")]
    [TestFixture]
    public class DapperTradeRepositoryTest:
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
            DapperUnitOfWorkProvider provider =
                new DapperUnitOfWorkProvider(
                    ConfigurationManager
                        .ConnectionStrings["RepositoryTestContext"]
                        .ToString());

            provider
                .InsertRetriever<long,Trade>( (trade) => trade.PrimaryId )
                .InsertAssigner<long,Trade>( (trade,key) => trade.PrimaryId = key )
                .InsertNamedQuery<Trade>(
                    "Trade.GetTradesWithManagerId",
                    @"  select 
                        distinct t.*
                        from     Trade t,AccountAllocation a,ManagerAllocation m
                        where    t.TradeKey = a.TradeKey and
                                 a.PrimaryId = m.PrimaryId and
                                 m.ManagerId = @id");

            return provider;
        }
    }
}
