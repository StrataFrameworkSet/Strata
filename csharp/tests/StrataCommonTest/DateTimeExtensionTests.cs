using System;
using Strata.Common.Utility;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Common
{
    [TestClass]
    public class DateTimeExtensionTests
    {
        [TestMethod]
        public void TestTruncateMilliseconds()
        {
            var target = new DateTime(2000, 1, 1, 1, 1, 1, 555);
            var expected = new DateTime(2000, 1, 1, 1, 1, 1, 0);

            // Truncates to the nearest second.
            DateTime actual = target.Truncate(TimeSpan.FromSeconds(1));

            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void TestTruncateSeconds()
        {
            var target = new DateTime(2000, 1, 1, 1, 1, 35, 555);
            var expected = new DateTime(2000, 1, 1, 1, 1, 0, 0);

            // Truncates to the nearest minute.
            DateTime actual = target.Truncate(TimeSpan.FromMinutes(1));

            Assert.AreEqual(expected, actual);
        }
    }
}
