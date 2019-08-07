//  ##########################################################################
//  # File Name: ITaskDispatcher.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.ProducerConsumer;
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Dispatches <c>ITask</c>s to <c>ITaskConsumer</c>s by
    /// routing (one consumer receives the task) or by broadcasting 
    /// (all consumers receive the same task).
    /// </summary>
    ///  
    public
    interface ITaskDispatcher:
        IDispatcher<ITask> {}
}

//  ##########################################################################
