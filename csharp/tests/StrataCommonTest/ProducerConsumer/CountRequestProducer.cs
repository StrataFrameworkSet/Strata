//  ##########################################################################
//  # File Name: CountRequestProducer.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Common.Utility;
using System;
using System.Threading;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class CountRequestProducer:
        AbstractActiveProducer<CountRequest>,
        ICountRequestProducer
    {
        private readonly int           itsTypeId;
        private readonly long          itsMaxCount;
        private readonly DispatchKind  itsKind;
        private AtomicLong             itsCurrentCount;

        public 
        CountRequestProducer(int typeId,long maxCount,DispatchKind kind)
        {
            itsTypeId   = typeId;
            itsMaxCount = maxCount;
            itsKind     = kind;
            itsCurrentCount = new AtomicLong(0L);
        }

        public long
        GetCount()
        {
            return itsCurrentCount.Get();
        }

        protected override void 
        DoStartProducing()
        {
            for (int i = 0; i < itsMaxCount; i++)
            {
                itsCurrentCount.IncrementAndGet();

                if (itsKind == DispatchKind.ROUTE)
                    GetDispatcher().Route(new CountRequest(itsTypeId));
                else
                    GetDispatcher().Broadcast(new CountRequest(itsTypeId));
            }

            Console.WriteLine(
                "debug: Producer " + itsTypeId + " " +
                "dispatched all " + itsMaxCount + " requests");
        }

        protected override void 
        DoStopProducing()
        {
            if (IsProducing())
                while (itsCurrentCount.Get() < itsMaxCount)
                    Thread.Sleep(100);
        }
    }
}

//  ##########################################################################
