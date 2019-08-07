//  ##########################################################################
//  # File Name: ITaskSelector.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.ProducerConsumer;
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides a mechanism for matching characteristics of <c>ITask</c> 
    /// objects. Characteristics include <c>ITask</c> name or properties.
    /// </summary>
    ///  
    public 
    interface ITaskSelector:
        ISelector<ITask> {}
}

//  ##########################################################################
