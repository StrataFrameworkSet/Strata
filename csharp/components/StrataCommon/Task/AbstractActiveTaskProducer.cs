//  ##########################################################################
//  # File Name: AbstractActiveTaskProducer.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Threading;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractActiveTaskProducer:
        AbstractTaskProducer
    {
        private Thread executor;

        protected
        AbstractActiveTaskProducer()
        {
            executor = null;
        }

        public override void
        StartProducing()
        {
            if (executor == null)
            {
                executor = new Thread( DoStartProducing );
                executor.Start();
            }
        }

        public override void 
        StopProducing()
        {
            DoStopProducing();

            if (executor != null)
            {
                executor.Join();
                executor = null;
            }
        }

        public override bool 
        IsProducing()
        {
            return executor != null;
        }

        protected abstract void
        DoStartProducing();

        protected abstract void
        DoStopProducing();
    }
}

//  ##########################################################################
