//  ##########################################################################
//  # File Name: DisruptorExceptionHandler.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.ProducerConsumer
{
    using IDisruptorExceptionHandler = Disruptor.IExceptionHandler;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class DisruptorExceptionHandler :
        IDisruptorExceptionHandler
    {
        private IExceptionHandler itsAdaptee;

        public 
        DisruptorExceptionHandler(IExceptionHandler adaptee)
        {
            itsAdaptee = adaptee;
        }

        public void 
        HandleEventException(Exception ex, long sequence, object evt)
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
