//  ##########################################################################
//  # File Name: BlockingQueueTaskDispatcher.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Threading;
using Strata.Common.Utility;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class BlockingQueueTaskDispatcher:
        AbstractTaskDispatcher
    {
        private IMultiMap<
                    ITaskSelector,
                    BlockingCollection<TaskEvent>> taskQueues;
        private AtomicBoolean                      dispatchingFlag;
        private AtomicLong                         sequence;
        private IDictionary<int,Thread>            threads;
 
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        BlockingQueueTaskDispatcher()
        {
            taskQueues = 
                new MultiMap<ITaskSelector,BlockingCollection<TaskEvent>>();
            dispatchingFlag = new AtomicBoolean( false );
            sequence = new AtomicLong( 0L );
            threads = new ConcurrentDictionary<int,Thread>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override void 
        Route(ITask task)
        {
            DoDispatch( task,DispatchKind.ROUTE );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override void 
        Broadcast(ITask task)
        {
            DoDispatch( task,DispatchKind.BROADCAST );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override void 
        StartDispatching()
        {
            int i = 0;

            if ( IsDispatching() )
                return;
            
            dispatchingFlag.CompareAndSet( false,true );
           
            foreach (ITaskSelector selector in GetConsumers().GetKeys())                
            {
                foreach (ITaskConsumer consumer in GetConsumers().Get( selector ))
                {
                    BlockingCollection<TaskEvent> queue = 
                        new BlockingCollection<TaskEvent>(
                            new ConcurrentQueue<TaskEvent>());
                    Thread processor = 
                        new Thread( 
                            new BlockingQueueProcessor(
                                queue,
                                consumer,
                                dispatchingFlag,
                                ExceptionHandler ).Run );

                    AddToQueues( selector,queue );   
                    threads.Add( i++,processor );
                    processor.Start();
                }
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override void 
        StopDispatching()
        {
            if ( !IsDispatching() )
                return;

            dispatchingFlag.Set( false );
            
            foreach (ITaskSelector key in taskQueues.GetKeys())
                foreach (
                    BlockingCollection<TaskEvent> queue in 
                        taskQueues.Get(key))
                    queue.CompleteAdding();

            foreach (Thread processor in threads.Values)
                processor.Join();
            
            threads.Clear();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override bool 
        IsDispatching()
        {
            return 
                dispatchingFlag.Get() &&
                threads.Count != 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private void
        DoDispatch(ITask task,DispatchKind kind)
        {
            long seq;
              
            if ( !IsDispatching() )
                throw new InvalidOperationException("Not dispatching.");
        
            seq = sequence.GetAndIncrement();
        
            foreach (ITaskSelector selector in taskQueues.GetKeys())
            {
                if ( selector.Match( task ) )
                {
                    BlockingCollection<TaskEvent> queue = 
                        GetQueue( selector,seq );
                
                    try
                    {
                        TaskEvent taskEvent = new TaskEvent();

                        taskEvent.Kind = kind;
                        taskEvent.Task = task;
                        queue.Add( taskEvent );

                        if ( kind == DispatchKind.ROUTE )
                            return;
                    }
                    catch (Exception e)
                    {
                        ExceptionHandler.OnException( e );
                    }
                }
            }      
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private void 
        AddToQueues(
            ITaskSelector                 selector,
            BlockingCollection<TaskEvent> queue)
        {
            taskQueues.Insert( selector,queue );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private BlockingCollection<TaskEvent>
        GetQueue(ITaskSelector selector,long seq)
        {
           int cardinality = taskQueues.GetCardinality( selector );
        
            return 
                taskQueues
                    .Get( selector,(int)(seq % cardinality) );
            
        }
    }
}

//  ##########################################################################
