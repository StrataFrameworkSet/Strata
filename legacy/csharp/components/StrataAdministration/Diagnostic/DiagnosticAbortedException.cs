//  ##########################################################################
//  # File Name: DiagnosticAbortedException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Administration.Diagnostic
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates that removing an entity 
    /// from a repository has failed.
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class DiagnosticAbortedException:
        DiagnosticException
    {
        public 
        DiagnosticAbortedException(String message):
            base(message) {}

        public 
        DiagnosticAbortedException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
