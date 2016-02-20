//  ##########################################################################
//  # File Name: DisruptorDispatcher.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using Disruptor;
using Disruptor.Dsl;
using Disruptor.Scheduler;
using Strata.Common.ProducerConsumer;
using Strata.Common.Utility;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class DisruptorDispatcher<T>:
        AbstractDispatcher<T>
        where T : class
    {
        private Disruptor<Event<T>> itsDisruptor;
        private AtomicBoolean       itsDispatchingIndicator;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        DisruptorDispatcher(Func<Event<T>> eventFactory, int bufferSize):
            this(eventFactory, bufferSize, 8) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        DisruptorDispatcher(
            Func<Event<T>> eventFactory,
            int            bufferSize,
            int            numThreads)
        {
            itsDisruptor =
                new Disruptor<Event<T>>(
                    eventFactory,
                    new MultiThreadedLowContentionClaimStrategy(bufferSize),
                    new BusySpinWaitStrategy(),
                    new RoundRobinThreadAffinedTaskScheduler(numThreads));

            itsDisruptor.HandleExceptionsWith(
                new DisruptorExceptionHandler(
                    new PrintStackTraceExceptionHandler()));

            itsDispatchingIndicator = new AtomicBoolean(false);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void
        Route(T payload)
        {
            RingBuffer<Event<T>> buffer   = itsDisruptor.RingBuffer;
            long                 sequence = buffer.Next();
            Event<T>             evnt     = buffer[sequence];

            evnt.Kind = DispatchKind.ROUTE;
            evnt.Payload = payload;
            buffer.Publish(sequence);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void
        Broadcast(T payload)
        {
            RingBuffer<Event<T>> buffer   = itsDisruptor.RingBuffer;
            long                 sequence = buffer.Next();
            Event<T>             evnt     = buffer[sequence];

            evnt.Kind = DispatchKind.BROADCAST;
            evnt.Payload = payload;
            buffer.Publish(sequence);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void
        StartDispatching()
        {
            IList<IEventHandler<Event<T>>> eventHandlers =
                new List<IEventHandler<Event<T>>>();

            foreach (
                IList<IConsumer<T>> consumers
                    in GetConsumers().GetValues())
            {
                long index = 0;
                long cardinality = consumers.Count;

                foreach (IConsumer<T> consumer in consumers)
                    eventHandlers.Add(
                        CreateHandler(
                            index++,
                            cardinality,
                            consumer));
            }

            itsDisruptor.HandleEventsWith(eventHandlers.ToArray());
            itsDisruptor.Start();
            itsDispatchingIndicator.Set(true);

        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void
        StopDispatching()
        {
            itsDisruptor.Shutdown();
            itsDispatchingIndicator.Set(false);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool
        IsDispatching()
        {
            return itsDispatchingIndicator.Get();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private DisruptorEventHandler<T>
        CreateHandler(long index, long cardinality, IConsumer<T> consumer)
        {
            return
                new DisruptorEventHandler<T>(
                    index,
                    cardinality,
                    consumer);
        }

    }
}

//  ##########################################################################
