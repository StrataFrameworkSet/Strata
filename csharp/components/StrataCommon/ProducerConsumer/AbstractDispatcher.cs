//  ##########################################################################
//  # File Name: AbstractDispatcher.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Common.Utility;
using System;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractDispatcher<T>:
        IDispatcher<T>
    {
        private IMultiMap<ISelector<T>,IConsumer<T>> itsConsumers;

        protected
        AbstractDispatcher()
        {
            itsConsumers = new MultiMap<ISelector<T>,IConsumer<T>>();
        }

        public IDispatcher<T>
        AttachConsumer(IConsumer<T> consumer)
        {
            itsConsumers.Insert(consumer.GetSelector(),consumer);
            return this;
        }

        public IDispatcher<T>
        DetachConsumer(IConsumer<T> consumer)
        {
            itsConsumers.Remove(consumer.GetSelector(),consumer);
            return this;
        }

        public bool
        HasConsumer(IConsumer<T> consumer)
        {
            return itsConsumers.ContainsValue(consumer.GetSelector(),consumer);
        }

        public abstract void
        Route(T payload);

        public abstract void
        Broadcast(T payload);

        public abstract void
        StartDispatching();

        public abstract void
        StopDispatching();

        public abstract bool
        IsDispatching();

        protected IMultiMap<ISelector<T>,IConsumer<T>>
        GetConsumers()
        {
            return itsConsumers;
        }

    }
}

//  ##########################################################################
