//  ##########################################################################
//  # File Name: ITaskDispatcher.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Dispatches <c>ITask</c>s to <c>ITaskConsumer</c>s by
    /// routing (one consumer receives the task) or by broadcasting 
    /// (all consumers receive the same task).
    /// </summary>
    ///  
    public 
    interface ITaskDispatcher
    {
        IExceptionHandler ExceptionHandler { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Attaches the specified <c>ITaskConsumer</c> 
        /// to this <c>ITaskDispatcher</c>.
        /// </summary>
        /// 
        /// <param name="consumer">consumer being attached</param>
        /// <returns>
        /// this <c>ITaskDispatcher</c> to enable method chaining
        /// </returns>
        /// 
        ITaskDispatcher
        AttachConsumer(ITaskConsumer consumer);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Detaches the specified <c>ITaskConsumer</c> 
        /// from this <c>ITaskDispatcher</c>.
        /// </summary>
        /// 
        /// <param name="consumer">consumer being detached</param>
        /// <returns>
        /// this <c>ITaskDispatcher</c> to enable method chaining
        /// </returns>
        /// 
        ITaskDispatcher
        DetachConsumer(ITaskConsumer consumer);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Routes the specified <c>ITask</c> to zero or one of the 
        /// currently attached<c>ITaskConsumer</c> using the <c>ITask</c>'s
        /// characteristics and the consumers' <c>ITaskSelector</c>s.
        /// </summary>
        /// 
        /// <param name="task">task being routed</param>
        /// 
        void
        Route(ITask task);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Broadcasts the specified <c>ITask</c> to zero or more of the 
        /// currently attached<c>ITaskConsumer</c> using the <c>ITask</c>'s
        /// characteristics and the consumers' <c>ITaskSelector</c>s.
        /// </summary>
        /// 
        /// <param name="task">task being routed</param>
        /// 
        void
        Broadcast(ITask task);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the specified <c>ITaskConsumer</c> is currently
        /// attached to this <c>ITaskDispatcher</c>.
        /// </summary>
        /// 
        /// <param name="consumer">a consumer</param>
        /// <returns>true if consumer is attached, false otherwise</returns>
        /// 
        bool
        HasConsumer(ITaskConsumer consumer);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Starts dispatching tasks to consumers.
        /// </summary>
        /// 
        void
        StartDispatching();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Stops dispatching tasks to consumers.
        /// </summary>
        /// 
        void
        StopDispatching();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if this <c>ITaskDispatcher</c> is dispatching.
        /// </summary>
        /// 
        /// <returns>true if dispatching, false otherwise</returns>
        /// 
        bool
        IsDispatching();
    }
}

//  ##########################################################################
