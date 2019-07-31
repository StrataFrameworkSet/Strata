//  ##########################################################################
//  # File Name: AbstractActiveProducer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Threading;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractActiveProducer<T>:
        AbstractProducer<T>
    {
        private Thread itsExecutor;

        protected
        AbstractActiveProducer()
        {
            itsExecutor = null;
        }

        public override void
        StartProducing()
        {
            if (itsExecutor == null)
            {
                itsExecutor = new Thread(DoStartProducing);
                itsExecutor.Start();
            }
        }

        public override void
        StopProducing()
        {
            DoStopProducing();

            if (itsExecutor != null)
            {
                itsExecutor.Join();
                itsExecutor = null;
            }
        }

        public override bool
        IsProducing()
        {
            return 
                itsExecutor != null &&
                itsExecutor.IsAlive;
        }

        protected abstract void
        DoStartProducing();

        protected abstract void
        DoStopProducing();
    }
}

//  ##########################################################################
