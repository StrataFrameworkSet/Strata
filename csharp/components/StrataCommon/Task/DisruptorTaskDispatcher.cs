//  ##########################################################################
//  # File Name: DisruptorTaskDispatcher.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Linq;
using Disruptor;
using Disruptor.Dsl;
using Disruptor.Scheduler;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class DisruptorTaskDispatcher:
        AbstractTaskDispatcher
    {
        private Disruptor<TaskEvent> disruptor;
        private bool                 dispatchingIndicator;
 
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>DisruptorTaskDispatcher</c> instance.
        /// </summary>
        /// 
        /// <param name="name">task name</param>
        /// 
        public
        DisruptorTaskDispatcher(int bufferSize)
        {
            /*
            disruptor = 
                new Disruptor<TaskEvent>( 
                    TaskEvent.createTaskEvent,
                    bufferSize,
                    new RoundRobinThreadAffinedTaskScheduler(4));
             */
            disruptor = 
                new Disruptor<TaskEvent>(
                    TaskEvent.createTaskEvent,
                    new MultiThreadedLowContentionClaimStrategy(bufferSize),
                    new SleepingWaitStrategy(),
                    new RoundRobinThreadAffinedTaskScheduler(4));

            dispatchingIndicator = false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public override void
        Route(ITask task)
        {
            RingBuffer<TaskEvent> buffer    = disruptor.RingBuffer;
	        long                  sequence  = buffer.Next();
	        TaskEvent             taskEvent = buffer[sequence];
	        
	        taskEvent.Kind = DispatchKind.ROUTE;
	        taskEvent.Task = task;        
	        buffer.Publish(sequence);	             
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public override void 
        Broadcast(ITask task)
        {
            RingBuffer<TaskEvent> buffer    = disruptor.RingBuffer;
	        long                  sequence  = buffer.Next();
	        TaskEvent             taskEvent = buffer[sequence];
	        
	        taskEvent.Kind = DispatchKind.BROADCAST;
	        taskEvent.Task = task;        
	        buffer.Publish(sequence);	                   
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public override void 
        StartDispatching()
        {
	        IList<IEventHandler<TaskEvent>> eventHandlers = 
	            new List<IEventHandler<TaskEvent>>();
	        
	        foreach (
                IList<ITaskConsumer> consumers 
                    in GetConsumers().GetValues())
	        {
	            long index       = 0;
	            long cardinality = consumers.Count;
	            
	            foreach (ITaskConsumer consumer in consumers)
	                eventHandlers.Add( 
	                    CreateHandler(
	                        index++,
	                        cardinality,
	                        consumer ) );
	        }
	        
	        disruptor.HandleEventsWith( eventHandlers.ToArray() );      
	        disruptor.Start();
	        dispatchingIndicator = true;
            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public override void 
        StopDispatching()
        {
            disruptor.Shutdown();
            dispatchingIndicator = false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public override bool 
        IsDispatching()
        {
            return dispatchingIndicator;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Factory method to create event handlers.
        /// </summary>
        /// 
        private DisruptorTaskEventHandler 
        CreateHandler(long index,long cardinality,ITaskConsumer consumer)
        {
            return 
                new DisruptorTaskEventHandler( 
                    index,
                    cardinality,
                    consumer,
                    ExceptionHandler );
        }
    }
}

//  ##########################################################################
