//  ##########################################################################
//  # File Name: ITaskConsumer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.ProducerConsumer;
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Mechanism to consume (i.e. execute) <c>ITask</c> instances.
    /// </summary>
    ///  
    public 
    interface ITaskConsumer:
        IConsumer<ITask> {}
}

//  ##########################################################################
