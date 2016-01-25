using System;
using System.IO;
using System.Reflection;
using Strata.NhibernatePersistence.NhibernateRepository;
using Strata.Persistence.InMemoryRepository;
using Strata.Persistence.Repository;
using Strata.Persistence.TradeDomain;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using NHibernate;
using NHibernate.Cfg;
using NHibernate.Dialect;
using NHibernate.Tool.hbm2ddl;

namespace Strata.NhibernatePeristence.NhibernateRepository
{
    [TestClass]
    public class NhibernateTradeRepositoryTest:
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

        [TestMethod]
        public override void TestGetTradesWithManagerId()
        {
            base.TestGetTradesWithManagerId();
        }

        [TestMethod]
        public override void TestGetAllTrades()
        {
            base.TestGetAllTrades();
        }

        [Ignore]
        [TestMethod]
        public override void 
        GenerateSchema()
        {
            Configuration config = new Configuration();

            config.AddAssembly("StrataPersistenceTest");         
            config.AddAssembly("StrataNhibernatePersistenceTest");   
            config.Configure();

            new SchemaExport(config).Create( true,true );
        }

        protected override IRepositoryProvider<long,Trade> 
        CreateRepositoryProvider()
        {
            return
                new NhibernateRepositoryProvider<long,Trade>(
                    new NhibernateRepositoryContext(
                        new String[]{ "StrataPersistenceTest","StrataNhibernatePersistenceTest" },true ) );
        }
    }
}
