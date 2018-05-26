using NUnit.Framework;
using Strata.Foundation.Utility;
using Strata.Foundation.Value;
using Strata.Domain.TradeDomain;
using Strata.Domain.UnitOfWork;
using System.Collections.Generic;
using System.Linq;

namespace Strata.Domain.Repository
{
    [TestFixture]
    public abstract 
    class TradeRepositoryTest
    {
        protected ITradeRepository target;
        protected TradeGenerator   generator;
        protected List<long>       tradeKeys;

        public virtual void 
        SetUp()
        {
            target = 
                new TradeRepository( CreateUnitOfWorkProvider());

            generator = new TradeGenerator();
            tradeKeys = new List<long>();
        }

        public virtual void 
        TearDown()
        {
            IUnitOfWork unitOfWork = target.Provider.GetUnitOfWork();

            foreach (int tradeKey in tradeKeys)
            {
                Trade trade = target.GetTrade(tradeKey);

                if ( trade != null )
                    target.RemoveTrade(trade);
            }
                
            unitOfWork.Commit();
            target = null;
        }

        public virtual void 
        TestInsertTrade()
        {
            Trade expected = this.CreateTestTrade();
            Trade actual = null;

            Assert.IsFalse(target.HasTrade(expected.TradeKey));
            expected = target.InsertTrade(expected);
            target.Provider.GetUnitOfWork().Commit();
            Assert.IsTrue(target.HasTrade(expected.TradeKey));
            tradeKeys.Add(expected.TradeKey);
            actual = target.GetTrade(expected.TradeKey);
            Assert.IsNotNull(actual);

            //Assert.AreEqual(expected.TradeId,actual.TradeId);
            Assert.AreEqual(expected,actual);
            target.Provider.GetUnitOfWork().Commit();
        }

        public virtual void 
        TestUpdateTrade()
        {
            Trade expected = this.CreateTestTrade();
            Trade actual = null;
            Money managerAmount = null;

            Assert.IsFalse(target.HasTrade(expected.TradeKey));
            expected = target.InsertTrade(expected);
            target.Provider.GetUnitOfWork().Commit();
            Assert.IsTrue(target.HasTrade(expected.TradeKey));
            tradeKeys.Add(expected.TradeKey);
            actual = target.GetTrade(expected.TradeKey).Copy();
            Assert.IsNotNull(actual);
            Assert.AreEqual(expected,actual);
            expected.ExternalTradeId = generator.CreateExternalTradeId();
            Assert.AreNotEqual(expected,actual);
            expected = target.UpdateTrade(expected);
            actual = target.GetTrade(expected.TradeKey).Copy();
            Assert.IsNotNull(actual);
            Assert.AreEqual(expected,actual);
            target.Provider.GetUnitOfWork().Commit();

            expected = target.GetTrade( expected.TradeKey );

            expected.AccountAllocations[0].Comment = "New Comment";
            managerAmount = expected.AccountAllocations[0].ManagerAllocations[0].ManagerAmount;
            expected.AccountAllocations[0].ManagerAllocations[0].ManagerAmount = managerAmount + new Money(CurrencyManager.GetInstanceByCode("USD"),250.0m);
            expected.AccountAllocations[0].ManagerAllocations.RemoveAt(1);
            expected = target.UpdateTrade(expected);
            target.Provider.GetUnitOfWork().Commit();

            actual = target.GetTrade(expected.TradeKey);
            Assert.IsNotNull(actual);
            Assert.AreEqual(expected,actual);
            target.Provider.GetUnitOfWork().Commit();
        }

        public virtual void 
        TestRemoveTrade()
        {
            Trade trade = this.CreateTestTrade();
 
            Assert.IsFalse(target.HasTrade(trade.TradeKey));
            target.InsertTrade(trade);
            Assert.IsTrue(target.HasTrade(trade.TradeKey));
            target.RemoveTrade(trade);
            target.Provider.GetUnitOfWork().Commit();
            Assert.IsFalse(target.HasTrade(trade.TradeKey));
        }

        public virtual void 
        TestGetTrade()
        {
            Trade expected = this.CreateTestTrade();
            Trade actual = null;

            Assert.IsFalse(target.HasTrade(expected.TradeKey));
            expected = target.InsertTrade(expected);
            Assert.IsTrue(target.HasTrade(expected.TradeKey));
            tradeKeys.Add(expected.TradeKey);
            actual = target.GetTrade(expected.TradeKey).Copy();
            Assert.IsNotNull(actual);
            Assert.AreEqual(expected,actual);
            expected.ExternalTradeId = generator.CreateExternalTradeId();
            Assert.AreNotEqual(expected,actual);
            expected = target.UpdateTrade(expected);
            actual = target.GetTrade( expected.TradeKey );
            target.Provider.GetUnitOfWork().Commit();
            Assert.AreEqual(expected,actual);
        }

        public virtual void 
        TestGetTradeContainingAccountAllocation()
        {
            Trade expected = this.CreateTestTrade();
            Trade actual = null;
            int allocationId = expected.AccountAllocations.First().AccountAllocationId;

            expected = target.InsertTrade(expected);
            Assert.IsTrue(target.HasTrade(expected.TradeKey));
            tradeKeys.Add(expected.TradeKey);
            target.Provider.GetUnitOfWork().Commit();

            actual = 
                target.GetTradeContainingAccountAllocation(allocationId);

            Assert.IsNotNull(actual);
            Assert.AreEqual(expected,actual);
            target.Provider.GetUnitOfWork().Commit();

            expected = target.GetTrade( actual.TradeKey );

            expected.AccountAllocations.Clear();
            expected = target.UpdateTrade(expected);
            target.Provider.GetUnitOfWork().Commit();

            actual = 
                target.GetTradeContainingAccountAllocation(allocationId);
            Assert.IsNull(actual);
            target.Provider.GetUnitOfWork().Commit();
        }


        public virtual void 
        TestGetTradesWithManagerId()
        {
            Trade t = this.CreateTestTrade();
            AccountAllocation a = t.AccountAllocations.First();
            ManagerAllocation m = generator.CreateManagerAllocation( a );
            IList<Trade> expected = null;

            a.ManagerAllocations.Add( m );
            t = target.InsertTrade(t);
            Assert.IsTrue(target.HasTrade(t.TradeKey));
            tradeKeys.Add(t.TradeKey);
            target.Provider.GetUnitOfWork().Commit();

            expected = target.GetTradesWithManagerId( m.ManagerId );
            Assert.IsNotNull( expected );
            Assert.IsTrue( expected.Any( x=>x.AccountAllocations.Any(y=>y.ManagerAllocations.Any(z=>z.ManagerId == m.ManagerId))) );
        }

        [Test]
        public virtual void
        TestRollback()
        {
            Trade expected = this.CreateTestTrade();
            Trade actual = null;
            int   counter = 0;

            Assert.IsFalse(target.HasTrade(expected.TradeKey));
            expected = target.InsertTrade(expected);
            target.Provider.GetUnitOfWork().Commit();

            Assert.IsTrue(target.HasTrade(expected.TradeKey));
            tradeKeys.Add(expected.TradeKey);
            actual = target.GetTrade(expected.TradeKey).Copy();
            target.Provider.GetUnitOfWork().Commit();
            Assert.IsNotNull(actual);
            Assert.AreEqual(expected,actual);
            expected.ExternalTradeId = generator.CreateExternalTradeId();
            Assert.AreNotEqual(expected,actual);
            target.Provider.GetUnitOfWork().PushRollbackAction(() => { ++counter; });
            expected = target.UpdateTrade(expected);
            target.Provider.GetUnitOfWork().Rollback();

            Assert.AreEqual(1,counter);
            expected = actual;
            actual = target.GetTrade(expected.TradeKey).Copy();
            Assert.IsNotNull(actual);
            Assert.AreEqual(expected,actual);
         }

        //[Test]
        public virtual void 
        GenerateSchema()
        {
        }

        public virtual void TestGetAllTrades()
        {
            Trade expected = CreateTestTrade();
            Trade expected2 = CreateTestTrade();

            expected.AccountAllocations = new List<AccountAllocation>();
            expected2.AccountAllocations = new List<AccountAllocation>();

            Assert.IsFalse(target.HasTrade(expected.TradeKey));
            Assert.IsFalse(target.HasTrade(expected2.TradeKey));

            expected = target.InsertTrade(expected);
            expected2 = target.InsertTrade(expected2);

            target.Provider.GetUnitOfWork().Commit();

            Assert.IsTrue(target.HasTrade(expected.TradeKey));
            Assert.IsTrue(target.HasTrade(expected2.TradeKey));

            tradeKeys.Add(expected.TradeKey);
            tradeKeys.Add(expected2.TradeKey);

            var actual = target.GetAllTrades();

            Assert.IsNotNull(actual);
            Assert.AreEqual(2,actual.Count);
        }

        protected abstract IUnitOfWorkProvider
        CreateUnitOfWorkProvider();

        protected Trade 
        CreateTestTrade()
        {
            return generator.CreateTrade();
        }

    }
}
