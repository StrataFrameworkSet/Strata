//  ##########################################################################
//  # File Name: TaskDisruptorDispatcher.cs
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
    class TaskDisruptorDispatcher:
        DisruptorDispatcher<ITask>,
        ITaskDispatcher
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>TaskDisruptorDispatcher</c> instance.
        /// </summary>
        /// 
        public
        TaskDisruptorDispatcher(int bufferSize) :
            base(() => new Event<ITask>(),bufferSize) {}
    }
}

//  ##########################################################################
