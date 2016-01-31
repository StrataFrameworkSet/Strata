//  ##########################################################################
//  # File Name: Event.cs
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
    class Event<T>
        where T: class
    {
        public T            Payload { get; set; }
        public DispatchKind Kind { get; set; }

        public 
        Event()
        {
            Payload = null;
        }
    }
}

//  ##########################################################################
