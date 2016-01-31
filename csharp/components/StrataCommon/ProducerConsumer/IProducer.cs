﻿//  ##########################################################################
//  # File Name: IProducer.cs
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
    interface IProducer<T>
    {
        IProducer<T>
        SetDispatcher(IDispatcher<T> router);

        IProducer<T>
        ClearDispatcher();

        IDispatcher<T>
        GetDispatcher();

        void
        StartProducing();

        void
        StopProducing();

        bool
        IsProducing();

    }
}

//  ##########################################################################
