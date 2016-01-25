//  ##########################################################################
//  # File Name: ITaskConsumer.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Mechanism to consume (i.e. execute) <c>ITask</c> instances.
    /// </summary>
    ///  
    public 
    interface ITaskConsumer
    {
        ITaskSelector     Selector { get;set; }
        IExceptionHandler ExceptionHandler { get;set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Consumes the specified <c>ITask</c> instance.
        /// </summary>
        /// 
        /// <param name="task">task being consumed</param>
        /// 
        void
        Consume(ITask task);
    }
}

//  ##########################################################################
