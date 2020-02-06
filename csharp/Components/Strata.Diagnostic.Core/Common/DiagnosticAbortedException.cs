//  ##########################################################################
//  # File Name: DiagnosticAbortedException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Diagnostic.Core.Common
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates that a diagnostic was aborted.
    /// </summary>
    ///  
    public
    class DiagnosticAbortedException:
        DiagnosticException
    {
        public 
        DiagnosticAbortedException(string message):
            base(message) {}

        public 
        DiagnosticAbortedException(string message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
