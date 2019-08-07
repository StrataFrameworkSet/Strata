//  ##########################################################################
//  # File Name: ITaskProducer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Mechanism for producing <c>ITask</c> instances.
    /// </summary>
    ///  
    public 
    interface ITaskProducer
    {
        ITaskDispatcher Dispatcher { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Starts the process of producing <c>ITask</c> instances
        /// and dispatches them to consumers.
        /// </summary>
        /// 
        void
        StartProducing();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Stops the process of producing <c>ITask</c> instances.
        /// </summary>
        /// 
        void
        StopProducing();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if this <c>ITaskProducer</c> is 
        /// currently producing <c>ITask</c> instances.
        /// </summary>
        /// 
        /// <returns>true if producing, false otherwise</returns>
        /// 
        bool
        IsProducing();
    }
}

//  ##########################################################################
