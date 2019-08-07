using Strata.Integration.InMemoryMessaging;
using Strata.Integration.Messaging;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Integration.Requester
{
    [TestClass]
    public class InMemoryRequestTest:
        RequestTest
    {
        [TestInitialize]
        public override void TestInitialize()
        {
            base.TestInitialize();
        }

        [TestCleanup]
        public override void TestCleanup()
        {
            base.TestCleanup();
        }

        [TestMethod]
        public override void TestConstructor()
        {
            base.TestConstructor();
        }

        [TestMethod]
        public override void TestScenario1()
        {
            base.TestScenario1();
        }

        [TestMethod]
        public override void TestScenario2()
        {
            base.TestScenario2();
        }

        [TestMethod]
        public override void TestScenario3()
        {
            base.TestScenario3();
        }

        [TestMethod]
        public override void TestScenario4()
        {
            base.TestScenario4();
        }

        protected override IMessagingSession
        CreateMessaging()
        {
            return new MockMessagingSession();
        }

        protected override IRequestFactory 
        CreateRequestFactory()
        {
            return new InMemoryRequestFactory();
        }
    }
}
