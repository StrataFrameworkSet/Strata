//  ##########################################################################
//  # File Name: HashedStringTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;

namespace Strata.Foundation.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class HashedStringTest
    {
        [TestCase("foobar")]
        [TestCase("!@$#%%#@@$^%^SSSS")]
        [TestCase("1234567890")]
        [TestCase("____2020gxfclaq0ghj?????202222")]
        [TestCase("password")]
        [TestCase("x")]
        [TestCase("")]
        public void
        TestMatches(string unhashed)
        {
            HashedString hashed = new HashedString(unhashed);

            Assert.IsTrue(hashed.Matches(unhashed));
        }

        [TestCase("foobar")]
        [TestCase("!@$#%%#@@$^%^SSSS")]
        [TestCase("1234567890")]
        [TestCase("____2020gxfclaq0ghj?????202222")]
        [TestCase("password")]
        [TestCase("x")]
        [TestCase("")]
        public void
        TestToString(string unhashed)
        {
            HashedString hashed = new HashedString(unhashed);

            Assert.Warn(
                string.Format(
                    "\n" +
                    "unhashed = {0}\n" +
                    "  hashed = {1}",
                    unhashed,
                    hashed.ToString()));
        }

        [Test]
        public void
        TestContructor()
        {
            HashedString x = new HashedString("foobar");
            HashedString y = new HashedString(x.Value,x.Salt,true);

            Assert.AreEqual(x,y);
        }
    }
}

//  ##########################################################################
