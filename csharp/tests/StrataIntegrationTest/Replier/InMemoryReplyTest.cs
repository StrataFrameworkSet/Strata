//  ##########################################################################
//  # File Name: InMemoryReplyTest.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Integration.InMemoryMessaging;
using Strata.Integration.Messaging;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    [TestClass]
    public 
    class InMemoryReplyTest:
        ReplyTest
    {
        [TestInitialize]
        public override void 
        TestInitialize()
        {
            base.TestInitialize();
        }

        [TestCleanup]
        public override void 
        TestCleanup()
        {
            base.TestCleanup();
        }

        [TestMethod]
        public override void 
        TestConstructor()
        {
            base.TestConstructor();
        }

        [TestMethod]
        public override void 
        TestScenario1()
        {
            base.TestScenario1();
        }

        [TestMethod]
        public override void 
        TestScenario2()
        {
            base.TestScenario2();
        }

        [TestMethod]
        public override void 
        TestScenario3()
        {
            base.TestScenario3();
        }

        protected override IMessagingSession 
        CreateMessaging()
        {
            return new MockMessagingSession();
        }

        protected override IReplyFactory 
        CreateReplyFactory()
        {
            return new InMemoryReplyFactory();
        }
    }
}

//  ##########################################################################
