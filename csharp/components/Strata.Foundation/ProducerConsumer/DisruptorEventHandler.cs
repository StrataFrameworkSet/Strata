//  ##########################################################################
//  # File Name: DisruptorEventHandler.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Disruptor;
using System;

namespace Strata.Foundation.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class DisruptorEventHandler<T>:
        IEventHandler<Event<T>>
        where T : class
    {
        private long              itsIndex;
        private long              itsCardinality;
        private IConsumer<T>      itsConsumer;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>DisruptorEventHandler{T}</c> instance.
        /// </summary>
        /// 
        /// <param name="index">
        /// consumer's index within a list of like consumers
        /// </param>
        /// <param name="cardinality">
        /// cardinality (count) of a list of like consumers
        /// </param>
        /// <param name="consumer">
        /// consumer
        /// </param>
        /// 
        public
        DisruptorEventHandler(
            long         index,
            long         cardinality,
            IConsumer<T> consumer)
        {
            itsIndex = index;
            itsCardinality = cardinality;
            itsConsumer = consumer;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        OnEvent(Event<T> data,long sequence,bool endOfBatch)
        {
            OnNext(data,sequence,endOfBatch);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        OnNext(Event<T> evnt, long sequence, bool endOfBatch)
        {
            if (MustConsume(evnt, sequence))
                itsConsumer.Consume(evnt.Payload);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool
        MustConsume(Event<T> evnt, long sequence)
        {
            ISelector<T> selector = itsConsumer.GetSelector();

            if (evnt.Kind == DispatchKind.ROUTE)
                return
                    selector.Match(evnt.Payload) &&
                    itsIndex == (sequence % itsCardinality);

            return selector.Match(evnt.Payload);

        }
    }
}

//  ##########################################################################
