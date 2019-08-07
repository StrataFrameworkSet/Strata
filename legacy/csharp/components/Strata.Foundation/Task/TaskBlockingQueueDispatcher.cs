//  ##########################################################################
//  # File Name: TaskBlockingQueueDispatcher.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.ProducerConsumer;
using System;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class TaskBlockingQueueDispatcher:
        BlockingQueueDispatcher<ITask>,
        ITaskDispatcher {}
}

//  ##########################################################################
