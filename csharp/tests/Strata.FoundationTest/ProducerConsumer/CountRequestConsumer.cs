//  ##########################################################################
//  # File Name: CountRequestConsumer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Utility;
using System;

namespace Strata.Foundation.ProducerConsumer
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
