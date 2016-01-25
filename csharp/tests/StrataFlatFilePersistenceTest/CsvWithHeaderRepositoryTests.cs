using Strata.FlatFilePersistence.CsvRepository;
using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.FlatFilePersistence
{
    [TestClass]
    public class CsvWithHeaderRepositoryTests : AbstractCsvTradeRepositoryTests
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

        protected override IRepositoryProvider<long, Trade> CreateRepositoryProvider()
        {
            var p = new CsvRepositoryProvider<long, Trade, CsvTradeAdapter>(
                new CsvRepositoryContext<long, Trade, CsvTradeAdapter>(
                    string.Empty,
                    "trades.csv",
                    initializeClean: true,
                    readHdrStyle: ReadHeaderStyles.HeaderRowAtBeginning,
                    writeHdrStyle: WriteHeaderStyles.HeaderRowAtBeginning),
                TradeRepository.GetKey);
            p.CreateFinder(
                TradeRepository.TradeIdEqualsFinder,
                new TradeIdEqualsPredicate());
            return p;
        }
    }
}
