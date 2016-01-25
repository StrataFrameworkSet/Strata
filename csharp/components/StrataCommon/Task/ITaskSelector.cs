//  ##########################################################################
//  # File Name: ITaskSelector.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides a mechanism for matching characteristics of <c>ITask</c> 
    /// objects. Characteristics include <c>ITask</c> name or properties.
    /// </summary>
    ///  
    public 
    interface ITaskSelector
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if an <c>ITask</c> matches 
        /// the criteria of the <c>ITaskSelector</c>.
        /// </summary>
        /// 
        /// <param name="task">task being evaluated</param>
        /// 
        /// <returns>true if task matches, false otherwise</returns>
        /// 
        bool
        Match(ITask task);
    }
}

//  ##########################################################################
