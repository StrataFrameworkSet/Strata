//  ##########################################################################
//  # File Name: AbstractConsumer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractConsumer<T> :
        IConsumer<T>
    {
        protected ISelector<T>      itsSelector;
        protected IExceptionHandler itsHandler;

        protected 
        AbstractConsumer(): 
            this( null ) {}

        protected
        AbstractConsumer(ISelector<T> selector)
        {
            itsSelector = selector;
            itsHandler  = new NullExceptionHandler();
        }

        public IConsumer<T> 
        SetSelector(ISelector<T> selector)
        {
            itsSelector = selector;
            return this;
        }

        public IConsumer<T> 
        SetExceptionHandler(IExceptionHandler handler)
        {
            itsHandler = handler;
            return this;
        }

        public void 
        Consume(T element)
        {
            try
            {
                DoConsume(element);
            }
            catch (Exception e)
            {
                itsHandler.OnException(e);
            }
        }

        public ISelector<T> 
        GetSelector()
        {
            return itsSelector;
        }

        public IExceptionHandler 
        GetExceptionHandler()
        {
            return itsHandler;
        }

        public int 
        GetWaitingCount()
        {
            return 0;
        }

        protected abstract void
        DoConsume(T element);
    }
}

//  ##########################################################################
