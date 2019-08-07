//  ##########################################################################
//  # File Name: AtomicsBooleanTest.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Common.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    [TestClass]
    public 
    class AtomicsBooleanTest
    {
        private AtomicBoolean target;

        [TestInitialize]
        public void
        SetUp()
        {
            target = new AtomicBoolean( false );
        }

        [TestCleanup]
        public void
        TearDown()
        {
            target = null;
        }

        [TestMethod]
        public void
        TestSet()
        {
            Assert.IsFalse( target.Get() );
            target.Set( true );
            Assert.IsTrue( target.Get() );
            target.Set( false );
            Assert.IsFalse( target.Get() );
        }

        [TestMethod]
        public void
        TestCompareAndSet()
        {
            Assert.IsFalse( target.Get() );
            Assert.IsTrue( target.CompareAndSet( false,true ));
            Assert.IsTrue( target.Get() );
            Assert.IsTrue( target.CompareAndSet( true,false ));
            Assert.IsFalse( target.Get() );
        }
    }
}

//  ##########################################################################
