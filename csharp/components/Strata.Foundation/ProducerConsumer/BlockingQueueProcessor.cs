//  ##########################################################################
//  # File Name: BlockingQueueProcessor.cs
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
    class BlockingQueueProcessor<T>
        where T:class
    {
        private BlockingCollection<Event<T>> source;
        private IConsumer<T>                 consumer;
        private AtomicBoolean                continueFlag;

        public
        BlockingQueueProcessor(
            BlockingCollection<Event<T>> src,
            IConsumer<T>                 cons,
            AtomicBoolean                contFlag)
        {
            source       = src;
            consumer     = cons;
            continueFlag = contFlag;
        }

        public void 
        Run()
        {
            while (continueFlag.Get())
            {
                try
                {
                    consumer.Consume( source.Take().Payload );
                }
                catch (InvalidOperationException)
                {
                    // queue is closed
                }
                catch (Exception e)
                {      
                    consumer.GetExceptionHandler().OnException( e );
                }
            }
        }
    }
}

//  ##########################################################################
