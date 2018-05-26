//  ##########################################################################
//  # File Name: DiagnosticResultState.cs
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
    enum DiagnosticResultState
    {
        NONE,
	    ABORTED,
	    SUCCEEDED,
	    FAILED,
	    RECOVERED
    }
}

//  ##########################################################################
