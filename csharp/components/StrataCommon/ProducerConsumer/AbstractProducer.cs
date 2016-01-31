//  ##########################################################################
//  # File Name: AbstractProducer.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractProducer<T> :
        IProducer<T>
    {
        private IDispatcher<T> itsDispatcher;

        protected 
        AbstractProducer()
        {
            itsDispatcher = null;
        }

        public IProducer<T> 
        SetDispatcher(IDispatcher<T> dispatcher)
        {
            itsDispatcher = dispatcher;
            return this;
        }

        public IProducer<T> 
        ClearDispatcher()
        {
            itsDispatcher = null;
            return this;
        }

        public IDispatcher<T> 
        GetDispatcher()
        {
            return itsDispatcher;
        }

        public abstract void
        StartProducing();

        public abstract void
        StopProducing();

        public abstract bool
        IsProducing();
    }
}

//  ##########################################################################
