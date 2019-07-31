//  ##########################################################################
//  # File Name: ITaskProducer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.ProducerConsumer;
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Mechanism for producing <c>ITask</c> instances.
    /// </summary>
    ///  
    public 
    interface ITaskProducer:
        IProducer<ITask> {}
}

//  ##########################################################################
