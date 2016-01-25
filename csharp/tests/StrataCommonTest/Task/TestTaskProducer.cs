//  ##########################################################################
//  # File Name: TestTaskProducer.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class TestTaskProducer:
        AbstractActiveTaskProducer
    {
        protected Queue<ITask> Tasks { get; private set; }
        public DispatchKind Kind { get; private set; }

        public
        TestTaskProducer(DispatchKind kind)
        {
            Tasks = new Queue<ITask>();
            Kind  = kind;
        }

        public TestTaskProducer
        InsertTask(ITask task)
        {
            Tasks.Enqueue( task );
            return this;
        }

        protected override void
        DoStartProducing()
        {
            if ( Dispatcher == null )
                throw new NullReferenceException("Dispatcher is null");

            foreach (ITask task in Tasks)
            {
                switch (Kind)
                {
                    case DispatchKind.ROUTE:
                        Dispatcher.Route( task );
                        break;

                    case DispatchKind.BROADCAST:
                        Dispatcher.Broadcast( task );
                        break;
                }
            }
        }

        protected override void
        DoStopProducing() {}
    }
}

//  ##########################################################################
