//  ##########################################################################
//  # File Name: TaskConsumer.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Logging;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Concrete mechanism to consume (i.e. execute) <c>ITask</c> instances.
    /// </summary>
    ///  
    public 
    class TaskConsumer:
        ITaskConsumer
    {
        public ITaskSelector     Selector { get; set; }
        public IExceptionHandler ExceptionHandler { get; set; }
        public ILogger           Logger { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>TaskConsumer</c> instance.
        /// </summary>
        /// 
        /// <param name="selector">task selector</param>
        /// 
        public
        TaskConsumer(ITaskSelector selector,ILogger logger)
        {
            Selector         = selector;
            ExceptionHandler = new PrintStackTraceExceptionHandler();
            Logger           = logger;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public void
        Consume(ITask task)
        {
            try
            {
                task.Execute();
            }
            catch (Exception e)
            {
                ExceptionHandler.OnException( e );
            }
        }
    }
}

//  ##########################################################################
