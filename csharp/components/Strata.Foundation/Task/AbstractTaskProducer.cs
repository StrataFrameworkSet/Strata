//  ##########################################################################
//  # File Name: AbstractTaskProducer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.ProducerConsumer;
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractTaskProducer:
        AbstractProducer<ITask>,
        ITaskProducer {}
}

//  ##########################################################################
