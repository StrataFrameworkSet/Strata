//  ##########################################################################
//  # File Name: AbstractActiveTaskProducer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.ProducerConsumer;
using System;
using System.Collections.Generic;
using System.Threading;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractActiveTaskProducer:
        AbstractActiveProducer<ITask>,
        ITaskProducer {}
}

//  ##########################################################################
