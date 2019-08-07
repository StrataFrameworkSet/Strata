//  ##########################################################################
//  # File Name: MappableDictionaryTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;
using System;
using System.Diagnostics;

namespace Strata.Foundation.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class MappableDictionaryTest
    {
        private MappableDictionary<string,decimal> itsTarget;

        [SetUp]
        public void
        SetUp()
        {
            itsTarget = new MappableDictionary<string,decimal>();

            itsTarget.Add("aaa",2.3m);
            itsTarget.Add("bbb",3.5m);
            itsTarget.Add("ccc",5.7m);
            itsTarget.Add("ddd",7.11m);
            itsTarget.Add("eee",11.13m);
        }

        [TearDown]
        public void
        TearDown()
        {
            itsTarget.Clear();
            itsTarget = null;
        }

        [Test]
        public void
        TestContentsGet()
        {
            string expected = "{\"$type\":\"System.Collections.Generic.Dictionary`2[[System.String, mscorlib],[System.Decimal, mscorlib]], mscorlib\",\"aaa\":2.3,\"bbb\":3.5,\"ccc\":5.7,\"ddd\":7.11,\"eee\":11.13}";

            Debug.WriteLine(itsTarget.Contents);
            Assert.AreEqual(expected,itsTarget.Contents);
        }

        [Test]
        public void
        TestContentsSet()
        {
            string                             expected = itsTarget.Contents;
            MappableDictionary<string,decimal> actual = new MappableDictionary<string,decimal>();

            actual.Contents = expected;
            Assert.AreEqual(expected,actual.Contents);
            //Assert.True(actual.SetEquals(itsTarget));
        }
    }
}

//  ##########################################################################
