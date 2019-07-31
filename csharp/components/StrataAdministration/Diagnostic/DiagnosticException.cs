//  ##########################################################################
//  # File Name: DiagnosticException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Administration.Diagnostic
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class DiagnosticException:
        Exception
    {
        public 
        DiagnosticException(String message):
            base(message) {}

        public 
        DiagnosticException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
