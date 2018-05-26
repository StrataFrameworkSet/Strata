//  ##########################################################################
//  # File Name: TaskDispatcherTest.cs
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
    class TaskDispatcherTest
    {
        private ITaskDispatcher  itsTarget;
        private TestTaskProducer itsProducer1;
        private TestTaskProducer itsProducer2;
        private ILogger          itsLogger;
        private ITaskConsumer    itsConsumer1;
        private ITaskConsumer    itsConsumer2;
        private ITaskConsumer    itsConsumer3a;
        private ITaskConsumer    itsConsumer3b;
        private StringWriter     itsWriter;
        private AtomicLong       counter;

        [TestInitialize]
        public void 
        SetUp()
        {
            //itsTarget = new DisruptorTaskDispatcher(1024);
            itsTarget = new BlockingQueueTaskDispatcher();
        
            itsWriter = new StringWriter();       
            itsLogger = CreateLogger();
            counter   = new AtomicLong( 0L );

            itsProducer1 = new TestTaskProducer(DispatchKind.ROUTE);
            itsProducer2 = new TestTaskProducer(DispatchKind.ROUTE);
        
            itsProducer1
                .InsertTask( new TestTask("TestA",1,itsLogger,counter) )
                .InsertTask( new TestTask("TestB",2,itsLogger,counter) )
                .InsertTask( new TestTask("TestC",3,itsLogger,counter) );
        
            itsProducer2
                .InsertTask( new TestTask("TestD",1,itsLogger,counter) )
                .InsertTask( new TestTask("TestE",2,itsLogger,counter) )
                .InsertTask( new TestTask("TestF",3,itsLogger,counter) );
       
            itsProducer1.Dispatcher = itsTarget;
            itsProducer2.Dispatcher = itsTarget;
        
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


        [TestMethod]
        public void
        testDispatch1()
        {
            itsTarget.StartDispatching();
        
            itsProducer1.StartProducing();
            itsProducer2.StartProducing();

            Thread.Sleep( TimeSpan.FromSeconds( 5 ) );

            Console
                .Out
                .WriteLine(itsWriter.ToString());
        }

        protected ILogger
        CreateLogger()
        {
            itsWriter = new StringWriter();
            return new TextWriterLogger( itsWriter );
        }
    }
}

//  ##########################################################################
