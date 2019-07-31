//  ##########################################################################
//  # File Name: PrintStackTraceExceptionHandler.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class PrintStackTraceExceptionHandler:
        IExceptionHandler
    {
        public
        PrintStackTraceExceptionHandler() {}

        public void
        OnException(Exception e)
        {
            Console.WriteLine(e);
        }
    }
}

//  ##########################################################################
