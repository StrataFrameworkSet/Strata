//  ##########################################################################
//  # File Name: DisruptorExceptionHandler.cs
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
    public
    class DisruptorExceptionHandler<T>:
        Disruptor.IExceptionHandler<T>
    {
        private IExceptionHandler itsAdaptee;

        public 
        DisruptorExceptionHandler(IExceptionHandler adaptee)
        {
            itsAdaptee = adaptee;
        }

        public void 
        HandleEventException(Exception ex,long sequence,T evt)
        {
            itsAdaptee.OnException(ex);
        }

        public void 
        HandleOnShutdownException(Exception ex)
        {
            itsAdaptee.OnException(ex);
        }

        public void 
        HandleOnStartException(Exception ex)
        {
            itsAdaptee.OnException(ex);
        }
    }
}

//  ##########################################################################
