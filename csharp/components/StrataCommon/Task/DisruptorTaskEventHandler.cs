//  ##########################################################################
//  # File Name: DisruptorTaskEventHandler.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Disruptor;

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
    class DisruptorTaskEventHandler:
        IEventHandler<TaskEvent>
    {
        private long              index;
        private long              cardinality;
        private ITaskConsumer     consumer;
        private IExceptionHandler handler;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>DisruptorTaskEventHandler</c> instance.
        /// </summary>
        /// 
        /// <param name="ind">
        /// consumer's index within a list of like consumers
        /// </param>
        /// <param name="card">
        /// cardinality (count) of a list of like consumers
        /// </param>
        /// <param name="con">
        /// task consumer
        /// </param>
        /// <param name="hand">
        /// exception handler
        /// </param>
        /// 
        public 
        DisruptorTaskEventHandler(
            long              ind,
            long              card,
            ITaskConsumer     con,
            IExceptionHandler hand)
        {
            index       = ind;
            cardinality = card;
            consumer    = con;
            handler     = hand;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// Processes a task event by sending it to a consumer.
        /// </summary>
        /// 
        public void
        OnNext(TaskEvent task,long sequence,bool endOfBatch)
        {
	        try
	        {
	            if ( MustConsume( task,sequence ) )
	                consumer.Consume( task.Task );
	        }
	        catch (Exception e)
	        {
	            handler.OnException( e );
	        }	            
        }

        private bool
        MustConsume(TaskEvent task,long sequence)
        {
            ITaskSelector selector = consumer.Selector;
	
	        if ( task.Kind == DispatchKind.ROUTE )
	            return
	                selector.Match( task.Task ) &&
	                index == (sequence % cardinality);
	        
	        return selector.Match( task.Task );
	            
        }
    }
}

//  ##########################################################################
