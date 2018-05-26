//  ##########################################################################
//  # File Name: IDispatcher.cs
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
    interface IDispatcher<T>
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Attaches the specified consumer to the dispatcher.
        /// </summary>
        /// 
        /// <param name="consumer">consumer to be attached</param> 
        /// <returns>this dispatcher - for method chaining</returns>
        ///
        IDispatcher<T>
        AttachConsumer(IConsumer<T> consumer);

        IDispatcher<T>
        DetachConsumer(IConsumer<T> consumer);

        bool
        HasConsumer(IConsumer<T> consumer);

        void
        Route(T payload);

        void
        Broadcast(T payload);

        void
        StartDispatching();

        void
        StopDispatching();

        bool
        IsDispatching();
    }
}

//  ##########################################################################
