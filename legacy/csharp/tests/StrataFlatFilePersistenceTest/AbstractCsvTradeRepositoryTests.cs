using System.Collections.Generic;
using System.Linq;
using Strata.Common.Utility;
using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.FlatFilePersistence
{
    public abstract class AbstractCsvTradeRepositoryTests : TradeRepositoryTest
    {
        public override void SetUp()
        {
            base.SetUp();
        }

        public override void TearDown()
        {
            base.TearDown();
        }

        public override void TestInsertTrade()
        {
            // Doubled the number of inserts to exercise the code handling multiple
            // updates to the repository in a single 'transaction.'Trade expected = CreateTestTrade();
            Trade expected = CreateTestTrade();
            Trade expected2 = CreateTestTrade();
            expected.AccountAllocations = new List<AccountAllocation>();
            expected2.AccountAllocations = new List<AccountAllocation>();

            Assert.IsFalse(target.HasTrade(expected.TradeKey));
            Assert.IsFalse(target.HasTrade(expected2.TradeKey));

            expected = target.InsertTrade(expected);
            expected2 = target.InsertTrade(expected2);

            target.UnitOfWork.Commit();

            Assert.IsTrue(target.HasTrade(expected.TradeKey));
            Assert.IsTrue(target.HasTrade(expected2.TradeKey));

            tradeKeys.Add(expected.TradeKey);
            tradeKeys.Add(expected2.TradeKey);

            Trade actual = target.GetTrade(expected.TradeKey);
            Trade actual2 = target.GetTrade(expected2.TradeKey);

            Assert.IsNotNull(actual);
            Assert.IsNotNull(actual2);

            Assert.AreEqual(expected, actual);
            Assert.AreEqual(expected2, actual2);

            target.UnitOfWork.Commit();
        }

        public override void TestUpdateTrade()
        {
            Trade expected = CreateTestTrade();
            expected.AccountAllocations = new List<AccountAllocation>();

            Assert.IsFalse(target.HasTrade(expected.TradeKey));

            expected = target.InsertTrade(expected);
            target.UnitOfWork.Commit();

            Assert.IsTrue(target.HasTrade(expected.TradeKey));

            tradeKeys.Add(expected.TradeKey);

            Trade actual = target.GetTrade(expected.TradeKey).Copy();

            Assert.IsNotNull(actual);
            Assert.AreEqual(expected, actual);

            expected.ExternalTradeId = generator.CreateExternalTradeId();

            Assert.AreNotEqual(expected, actual);

            expected = target.UpdateTrade(expected);
            target.UnitOfWork.Commit();

            actual = target.GetTrade(expected.TradeKey).Copy();

            Assert.IsNotNull(actual);
            Assert.AreEqual(expected, actual);

            target.UnitOfWork.Commit();
        }

        public override void TestRemoveTrade()
        {
            Trade expected = CreateTestTrade();
            expected.AccountAllocations = new List<AccountAllocation>();

            Assert.IsFalse(target.HasTrade(expected.TradeKey));

            target.InsertTrade(expected);
            target.UnitOfWork.Commit();

            Assert.IsTrue(target.HasTrade(expected.TradeKey));

            target.RemoveTrade(expected);
            target.UnitOfWork.Commit();

            Assert.IsFalse(target.HasTrade(expected.TradeKey));

            target.UnitOfWork.Commit();
        }

        public override void TestGetTrade()
        {
            Trade expected = CreateTestTrade();
            expected.AccountAllocations = new List<AccountAllocation>();

            Assert.IsFalse(target.HasTrade(expected.TradeKey));

            expected = target.InsertTrade(expected);
            target.UnitOfWork.Commit();

            Assert.IsTrue(target.HasTrade(expected.TradeKey));

            tradeKeys.Add(expected.TradeKey);
            Trade actual = target.GetTrade(expected.TradeKey).Copy();

            Assert.IsNotNull(actual);
            Assert.AreEqual(expected, actual);

            expected.ExternalTradeId = generator.CreateExternalTradeId();

            Assert.AreNotEqual(expected, actual);

            expected = target.UpdateTrade(expected);
            target.UnitOfWork.Commit();
            actual = target.GetTrade(expected.TradeKey);

            Assert.AreEqual(expected, actual);
        }

        public void TestGetTradeByTradeIdPredicate()
        {
            Trade expected = CreateTestTrade();
            expected.AccountAllocations = new List<AccountAllocation>();

            Assert.IsFalse(target.HasTrade(expected.TradeKey));

            expected = target.InsertTrade(expected);
            target.UnitOfWork.Commit();

            Assert.IsTrue(target.HasTrade(expected.TradeKey));

            tradeKeys.Add(expected.TradeKey);

            Trade actual = target.GetTradesWithTradeId(expected.TradeId).FirstOrDefault();

            Assert.IsNotNull(actual);
            Assert.AreEqual(expected, actual);
            target.UnitOfWork.Commit();
        }
    }
}
