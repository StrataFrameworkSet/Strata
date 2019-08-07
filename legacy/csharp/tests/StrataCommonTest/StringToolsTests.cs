using Strata.Common.Utility;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Common
{
    [TestClass]
    public class StringToolsTests
    {
        [TestMethod]
        public void GivenStringWithWhitespace_ReturnsTrimmedString()
        {
            var test = "test     ";
            string expected = "test";
            var actual = StringTools.TryTrim(test);

            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void GivenNull_ReturnsEmptyString()
        {
            string test = null;
            string expected = string.Empty;
            var actual = StringTools.TryTrim(test);

            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void GivenEmptyString_ReturnsEmptyString()
        {
            string test = string.Empty;
            string expected = string.Empty;
            var actual = StringTools.TryTrim(test);

            Assert.AreEqual(expected, actual);
        }
    }
}
