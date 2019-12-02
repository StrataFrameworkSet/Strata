//  ##########################################################################
//  # File Name: DiagnosticException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Diagnostic.Core.Common
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
        DiagnosticException(string message):
            base(message) {}

        public 
        DiagnosticException(string message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
