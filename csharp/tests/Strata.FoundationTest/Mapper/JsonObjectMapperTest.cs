//  ##########################################################################
//  # File Name: JsonObjectMapperTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;
using Strata.Foundation.Injection;
using Strata.Foundation.Value;
using System;
using System.Diagnostics;

namespace Strata.Foundation.Mapper
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class JsonObjectMapperTest
    {
        private JsonObjectMapper<IFooBar> target;

        [SetUp]
        public void 
        SetUp()
        {
            target = new JsonObjectMapper<IFooBar>();
        }

        [TearDown]
        public void
        TearDown()
        {
            target = null;
        }

        [Test]
        public void
        TestToPayload()
        {
            IFooBar source = new FooBar(new Foo("test") { Created = new Date(2018,9,5) });
            string expected = "{\"$type\":\"Strata.Foundation.Injection.FooBar, Strata.FoundationTest\",\"Foo\":{\"$type\":\"Strata.Foundation.Injection.Foo, Strata.FoundationTest\",\"FooName\":\"test\",\"Created\":\"2018-09-05T00:00:00\"},\"Bar\":null,\"Baz\":null}";
            string actual = target.ToPayload(source);

            Debug.WriteLine("Actual: " + actual);
            Assert.AreEqual(expected,actual);
        }

        [Test]
        public void
        TestToPayloadToObject()
        {
            IFooBar expected = new FooBar(new Foo("test"));
            IFooBar actual   = target.ToObject<FooBar>(target.ToPayload(expected));

            Assert.AreEqual(expected.Foo.FooName,actual.Foo.FooName);
        }
    }
}

//  ##########################################################################
