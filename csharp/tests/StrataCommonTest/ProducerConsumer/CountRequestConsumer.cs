//  ##########################################################################
//  # File Name: CountRequestConsumer.cs
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
    public
    class CountRequestConsumer:
        AbstractConsumer<CountRequest>,
        ICountRequestConsumer
    {
        private AtomicLong itsCount;

        public
        CountRequestConsumer(int typeId):
            base( new CountRequestSelector(typeId) )
        {
            SetExceptionHandler(new PrintStackTraceExceptionHandler());
            itsCount = new AtomicLong(0L);
        }

        public long
        GetCount()
        {
            return itsCount.Get();
        }

        protected override void
        DoConsume(CountRequest request)
        {
            itsCount.IncrementAndGet();
        }
    }
}

//  ##########################################################################
