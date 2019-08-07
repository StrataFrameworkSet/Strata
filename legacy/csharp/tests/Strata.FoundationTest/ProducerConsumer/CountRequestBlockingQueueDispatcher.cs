//  ##########################################################################
//  # File Name: CountRequestDisruptorDispatcher.cs
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
    class CountRequestBlockingQueueDispatcher:
        BlockingQueueDispatcher<CountRequest>,
        ICountRequestDispatcher {}
}

//  ##########################################################################
