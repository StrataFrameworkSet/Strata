//  ##########################################################################
//  # File Name: CountRequestDisruptorDispatcher.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class CountRequestDisruptorDispatcher:
        DisruptorDispatcher<CountRequest>,
        ICountRequestDispatcher
    {
        public
        CountRequestDisruptorDispatcher(int bufferSize): 
            base( Create,bufferSize ) {}

        public static Event<CountRequest>
        Create()
        {
            return new Event<CountRequest>();
        }
    }
}

//  ##########################################################################
