//  ##########################################################################
//  # File Name: TaskConsumer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Foundation.Logging;
using Strata.Foundation.ProducerConsumer;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Concrete mechanism to consume (i.e. execute) <c>ITask</c> instances.
    /// </summary>
    ///  
    public 
    class TaskConsumer:
        AbstractConsumer<ITask>,
        ITaskConsumer
    {
        private ILogger Logger { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>TaskConsumer</c> instance.
        /// </summary>
        /// 
        /// <param name="selector">task selector</param>
        /// 
        public
        TaskConsumer(ITaskSelector selector,ILogger logger):
            base( selector )
        {
            Logger = logger;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        protected override void
        DoConsume(ITask task)
        {
            task.Execute();
        }
    }
}

//  ##########################################################################
