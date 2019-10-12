﻿//  ##########################################################################
//  # File Name: MappableSetTest.cs
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
    class MappableSetTest
    {
        private MappableSet<decimal> itsTarget;

        [SetUp]
        public void
        SetUp()
        {
            itsTarget = new MappableSet<decimal>();

            itsTarget.Add(2.3m);
            itsTarget.Add(3.5m);
            itsTarget.Add(5.7m);
            itsTarget.Add(7.11m);
            itsTarget.Add(11.13m);
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
            string expected = "[2.3,3.5,5.7,7.11,11.13]";

            Debug.WriteLine(itsTarget.Contents);
            Assert.AreEqual(expected,itsTarget.Contents);
        }

        [Test]
        public void
        TestContentsSet()
        {
            string               expected = itsTarget.Contents;
            MappableSet<decimal> actual = new MappableSet<decimal>();

            actual.Contents = expected;
            Assert.AreEqual(expected,actual.Contents);
            Assert.True(actual.SetEquals(itsTarget));
        }
    }
}

//  ##########################################################################