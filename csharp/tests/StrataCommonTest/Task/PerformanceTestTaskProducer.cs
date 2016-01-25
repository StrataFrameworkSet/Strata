//  ##########################################################################
//  # File Name: PerformanceTestTaskProducer.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Logging;
using Strata.Common.Utility;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class PerformanceTestTaskProducer:
        AbstractActiveTaskProducer
    {
        public int          TaskCount { get; private set; }
        public int          TaskId { get; private set; }
        public DispatchKind Kind { get; private set; }
        public ILogger      Logger { get; private set; }
        public AtomicLong   Counter { get; private set; }

        public
        PerformanceTestTaskProducer(int taskCount,int taskId,DispatchKind kind,ILogger logger,AtomicLong counter)
        {
            TaskCount = taskCount;
            TaskId    = taskId;
            Kind      = kind;
            Logger    = logger;
            Counter   = counter;
        }

        protected override void
        DoStartProducing()
        {
            if ( Dispatcher == null )
                throw new NullReferenceException("Dispatcher is null");

            for (int i=0;i<TaskCount;i++)
            {
                switch (Kind)
                {
                    case DispatchKind.ROUTE:
                        Dispatcher.Route( CreateTask() );
                        break;

                    case DispatchKind.BROADCAST:
                        Dispatcher.Broadcast( CreateTask() );
                        break;
                }
            }
        }

        protected override void
        DoStopProducing() {}

        protected ITask
        CreateTask()
        {
            return new TestTask( "PerformanceTest",TaskId,Logger,Counter );
        }
    }
}

//  ##########################################################################
