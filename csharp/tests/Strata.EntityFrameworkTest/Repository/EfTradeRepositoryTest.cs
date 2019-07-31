using System.Data.Entity;
using NUnit.Framework;
using Strata.EntityFramework.Mapping;
using Strata.EntityFramework.UnitOfWork;
using Strata.Domain.Repository;
using Strata.Domain.TradeDomain;
using Strata.Domain.UnitOfWork;

namespace Strata.EntityFramework.Repository
{
    [TestFixture]
    public class EfTradeRepositoryTest :
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
        public override void TestGetTradeContainingAccountAllocation()
        {
            base.TestGetTradeContainingAccountAllocation();
        }

        [Ignore("Entity Framework 5: sql queries cannot map complex properties like Money")] 
        [Test]
        public override void TestGetTradesWithManagerId()
        {
            base.TestGetTradesWithManagerId();
        }
        
        protected override IUnitOfWorkProvider
        CreateUnitOfWorkProvider()
        {
            IEfUnitOfWorkProvider provider =
                new EfUnitOfWorkProvider<RepositoryTestContext>();

            Database
                .SetInitializer(
                    new EfDropCreateDatabaseIfModelChanges<RepositoryTestContext>());

            provider
                .InsertNamedQuery<Trade>(
                "Trade.GetTradesWithManagerId",
                @"  select 
                    distinct t.*
                    from     Trade t,AccountAllocation a,ManagerAllocation m
                    where    t.TradeKey = a.TradeKey and
                             a.PrimaryId = m.PrimaryId and
                             m.ManagerId = @id" );

            return provider;
        }
    }
}
