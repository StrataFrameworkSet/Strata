//  ##########################################################################
//  # File Name: PrintStackTraceExceptionHandler.cs
//  # Copyright: 2016, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.ProducerConsumer
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
