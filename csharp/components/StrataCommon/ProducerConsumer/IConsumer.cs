//  ##########################################################################
//  # File Name: IConsumer.cs
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
    interface IConsumer<T>
    {
        IConsumer<T>
        SetSelector(ISelector<T> selector);

        IConsumer<T>
        SetExceptionHandler(IExceptionHandler handler);

        void
        Consume(T element);

        ISelector<T>
        GetSelector();

        IExceptionHandler
        GetExceptionHandler();

        int
        GetWaitingCount();
    }
}

//  ##########################################################################
