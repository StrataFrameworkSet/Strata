using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace Strata.Common.MoneyDomain
{
    [TestClass]
    public class CurrencyManagerTests
    {
        private readonly Currency USD = new Currency(1, "USD",  "$");

        [TestInitialize]
        public void Initialize()
        {
            var testProviderMock = new Mock<ICurrencyProvider>();
            testProviderMock.Setup(p => p.GetCurrencies()).Returns(new List<Currency> {USD});
            CurrencyManager.SetDataSource(testProviderMock.Object.GetCurrencies());
        }

        [TestCleanup]
        public void Cleanup()
        {
        }

        [TestMethod]
        public void GetInstanceById()
        {
            Assert.AreEqual(USD, CurrencyManager.GetInstanceById(USD.CurrencyId));
        }

        [TestMethod]
        public void GetInstanceByCode()
        {
            Assert.AreEqual(USD, CurrencyManager.GetInstanceByCode(USD.CurrencyCode));
        }

        [TestMethod]
        public void GetInstanceBySymbol()
        {
            Assert.AreEqual(USD, CurrencyManager.GetInstanceBySymbol(USD.Symbol));
        }
    }
}
