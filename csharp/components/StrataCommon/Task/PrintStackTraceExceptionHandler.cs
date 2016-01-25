//  ##########################################################################
//  # File Name: PrintStackTraceExceptionHandler.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class PrintStackTraceExceptionHandler:
        IExceptionHandler
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>PrintStackTraceExceptionHandler</c> instance.
        /// </summary>
        /// 
        public
        PrintStackTraceExceptionHandler() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public void
        OnException(Exception e)
        {
            Console
                .Error
                .WriteLine(e.StackTrace);
        }
    }
}

//  ##########################################################################
