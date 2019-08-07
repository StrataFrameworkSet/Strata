using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Common.MoneyDomain
{
    [TestClass]
    public class DefaultCurrencyProviderTests
    {
        private ICurrencyProvider target;

        [TestInitialize]
        public void Initialize()
        {
            target = new DefaultCurrencyProvider();
        }

        [TestCleanup]
        public void Cleanup()
        {
            target = null;
        }

        [TestMethod]
        public void CanGetCurrencies()
        {
            var result = target.GetCurrencies();

            Assert.IsNotNull(result);
            Assert.IsTrue(result.Count > 0);

            var usd = result.FirstOrDefault(c => c.CurrencyCode == "USD");

            Assert.IsNotNull(usd);
        }
    }
}
