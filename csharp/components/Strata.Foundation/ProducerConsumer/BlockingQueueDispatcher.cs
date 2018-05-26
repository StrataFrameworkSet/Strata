//  ##########################################################################
//  # File Name: BlockingQueueDispatcher.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Threading;
using Strata.Foundation.Utility;

namespace Strata.Foundation.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class BlockingQueueDispatcher<T>:
        AbstractDispatcher<T>
        where T:class
    {
        private IMultiMap<
                    ISelector<T>,
                    BlockingCollection<Event<T>>> taskQueues;
        private AtomicBoolean                     dispatchingFlag;
        private AtomicLong                        sequence;
        private IDictionary<int,Thread>           threads;

        public IExceptionHandler ExceptionHandler { get; set; }
 
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        BlockingQueueDispatcher()
        {
            taskQueues = 
                new MultiMap<ISelector<T>,BlockingCollection<Event<T>>>();
            dispatchingFlag = new AtomicBoolean( false );
            sequence = new AtomicLong( 0L );
            threads = new ConcurrentDictionary<int,Thread>();
            ExceptionHandler = new NullExceptionHandler();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override void 
        Route(T payload)
        {
            DoDispatch( payload,DispatchKind.ROUTE );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override void 
        Broadcast(T payload)
        {
            DoDispatch( payload,DispatchKind.BROADCAST );
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
           
            foreach (ISelector<T> selector in GetConsumers().GetKeys())                
            {
                foreach (IConsumer<T> consumer in GetConsumers().Get( selector ))
                {
                    BlockingCollection<Event<T>> queue = 
                        new BlockingCollection<Event<T>>(
                            new ConcurrentQueue<Event<T>>());
                    Thread processor = 
                        new Thread( 
                            new BlockingQueueProcessor<T>(
                                queue,
                                consumer,
                                dispatchingFlag ).Run );

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
            
            foreach (ISelector<T> key in taskQueues.GetKeys())
                foreach (
                    BlockingCollection<Event<T>> queue in 
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
        DoDispatch(T payload,DispatchKind kind)
        {
            long seq;
              
            if ( !IsDispatching() )
                throw new InvalidOperationException("Not dispatching.");
        
            seq = sequence.GetAndIncrement();
        
            foreach (ISelector<T> selector in taskQueues.GetKeys())
            {
                if ( selector.Match( payload ) )
                {
                    BlockingCollection<Event<T>> queue = 
                        GetQueue( selector,seq );
                
                    try
                    {
                        Event<T> evnt = new Event<T>();

                        evnt.Kind = kind;
                        evnt.Payload = payload;
                        queue.Add( evnt );

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
            ISelector<T>                 selector,
            BlockingCollection<Event<T>> queue)
        {
            taskQueues.Insert( selector,queue );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private BlockingCollection<Event<T>>
        GetQueue(ISelector<T> selector,long seq)
        {
           int cardinality = taskQueues.GetCardinality( selector );
        
            return 
                taskQueues
                    .Get( selector,(int)(seq % cardinality) );
            
        }
    }
}

//  ##########################################################################
