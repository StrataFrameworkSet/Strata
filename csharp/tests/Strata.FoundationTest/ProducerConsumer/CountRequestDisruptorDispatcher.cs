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
