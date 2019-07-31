using NHibernate.Cfg;
using NHibernate.Tool.hbm2ddl;
using NUnit.Framework;
using Strata.Nhibernate.UnitOfWork;
using Strata.Domain.Repository;
using Strata.Domain.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Strata.Nhibernate.Repository
{
    [TestFixture]
    public class NhibernateTradeRepositoryTest:
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

        [Test]
        public override void TestGetTradesWithManagerId()
        {
            base.TestGetTradesWithManagerId();
        }

        [Test]
        public override void TestGetAllTrades()
        {
            base.TestGetAllTrades();
        }

        [Test]
        public void
        TestIsCollectionType()
        {
            IList<string> inputs = new List<string>();

            Assert.IsTrue(IsCollectionType(inputs));
            Assert.IsFalse(IsCollectionType("foo"));
            Assert.IsTrue(IsCollectionType(new string[] {}));
            Assert.IsFalse(IsCollectionType(true));
            Assert.IsFalse(IsCollectionType(DateTime.Now));

        }

        [Ignore("use when needed")]
        [Test]
        public void 
        GenerateSchema()
        {
            Configuration config = new Configuration();

            config.AddAssembly("Strata.NhibernateTest");         
            config.Configure();

            new SchemaExport(config)
                .SetOutputFile(@"C:\Temp\StataAutomatedTest.sql")
                .Create( true,true );
        }

        protected override IUnitOfWorkProvider
        CreateUnitOfWorkProvider()
        {
            return
                new NhibernateUnitOfWorkProvider(
                    new String[]{ "Strata.NhibernateTest" } );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool
        IsCollectionType(object input)
        {
            return
                input
                    .GetType()
                    .GetInterfaces()
                    .Where(i => i.IsGenericType)
                    .Any(
                        i => i.GetGenericTypeDefinition() == 
                        typeof(ICollection<>));
        }

    }
}
