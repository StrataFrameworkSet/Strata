//  ##########################################################################
//  # File Name: TaskDispatcherPerformanceTest.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using Strata.Common.Logging;
using Strata.Common.Utility;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    [TestClass]
    public 
    class TaskDispatcherPerformanceTest
    {
        private ITaskDispatcher             itsTarget;
        private PerformanceTestTaskProducer itsProducer1;
        private PerformanceTestTaskProducer itsProducer2;
        private PerformanceTestTaskProducer itsProducer3;
        private ILogger                     itsLogger;
        private ITaskConsumer               itsConsumer1;
        private ITaskConsumer               itsConsumer2;
        private ITaskConsumer               itsConsumer3a;
        private ITaskConsumer               itsConsumer3b;
        private AtomicLong                  counter;
        private const int                   NUM_TASKS = 1000000;
        private const int                   TOTAL_TASKS = 3*NUM_TASKS;

        [TestInitialize]
        public void 
        SetUp()
        {
            itsTarget = new DisruptorTaskDispatcher(1024*256);
            //itsTarget = new BlockingQueueTaskDispatcher();
        
            itsLogger = CreateLogger();
            counter = new AtomicLong( 0L );
            itsProducer1 = new PerformanceTestTaskProducer(NUM_TASKS,1,DispatchKind.ROUTE,itsLogger,counter);
            itsProducer2 = new PerformanceTestTaskProducer(NUM_TASKS,2,DispatchKind.ROUTE,itsLogger,counter);
            itsProducer3 = new PerformanceTestTaskProducer(NUM_TASKS,3,DispatchKind.ROUTE,itsLogger,counter);


            itsProducer1.Dispatcher = itsTarget;
            itsProducer2.Dispatcher = itsTarget;
            itsProducer3.Dispatcher = itsTarget;
        
            itsConsumer1 = new TaskConsumer(new TestTaskSelector(1),itsLogger);
            itsConsumer2 = new TaskConsumer(new TestTaskSelector(2),itsLogger);
            itsConsumer3a = new TaskConsumer(new TestTaskSelector(3),itsLogger);
            itsConsumer3b = new TaskConsumer(new TestTaskSelector(3),itsLogger);
        
            itsTarget
                .AttachConsumer( itsConsumer1 )
                .AttachConsumer( itsConsumer2 )
                .AttachConsumer( itsConsumer3a )
                .AttachConsumer( itsConsumer3b );
            
        }

        [TestCleanup]
        public void
        TearDown()
        {
            itsTarget.StopDispatching();
        }

        //[Ignore]
        [TestMethod]
        public void
        testDispatch1()
        {
            DateTime start = DateTime.Now;
            DateTime finish;

            itsTarget.StartDispatching();
        

            itsProducer1.StartProducing();
            itsProducer2.StartProducing();
            itsProducer3.StartProducing();

            while ( counter.Get() < TOTAL_TASKS )
                Thread.Sleep( 250 );

            itsTarget.StopDispatching();
            finish = DateTime.Now;

            Assert.AreEqual( TOTAL_TASKS,counter.Get() );

            Console
                .Out
                .WriteLine("# Tests: " + TOTAL_TASKS + " in " + finish.Subtract( start ).TotalSeconds + " seconds");
        }

        protected ILogger
        CreateLogger()
        {
            return new NullLogger();
        }
    }
}

//  ##########################################################################
