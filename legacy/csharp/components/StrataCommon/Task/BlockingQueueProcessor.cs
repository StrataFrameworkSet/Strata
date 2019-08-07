//  ##########################################################################
//  # File Name: BlockingQueueProcessor.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
    class BlockingQueueProcessor
    {
        private BlockingCollection<TaskEvent> source;
        private ITaskConsumer                 consumer;
        private AtomicBoolean                 continueFlag;
        private IExceptionHandler             handler;

        public
        BlockingQueueProcessor(
            BlockingCollection<TaskEvent> src,
            ITaskConsumer                 cons,
            AtomicBoolean                 contFlag,
            IExceptionHandler             hndlr)
        {
            source       = src;
            consumer     = cons;
            continueFlag = contFlag;
            handler      = hndlr;
        }

        public void 
        Run()
        {
            while (continueFlag.Get())
            {
                try
                {
                    consumer.Consume( source.Take().Task );
                }
                catch (InvalidOperationException)
                {
                    // queue is closed
                }
                catch (Exception e)
                {      
                    handler.OnException( e );
                }
            }
        }
    }
}

//  ##########################################################################
