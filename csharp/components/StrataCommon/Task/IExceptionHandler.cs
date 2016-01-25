//  ##########################################################################
//  # File Name: IExceptionHandler.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Mechanism to handle exceptions generated in the Task subsystem.
    /// </summary>
    ///  
    public 
    interface IExceptionHandler
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Callback to handle exceptions.
        /// </summary>
        /// 
        /// <param name="e">exception being handled</param>
        /// 
        void
        OnException(Exception e);
    }
}

//  ##########################################################################
