//  ##########################################################################
//  # File Name: IDiagnosticReceiver.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Diagnostic.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Captures events or conditions that occur when an 
    /// <c>IDiagnostic</c> is run.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IDiagnosticReceiver
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures the beginning of a diagnostic.
        /// </summary>
        /// 
        /// <param name="d">diagnostic that has begun</param>
        /// 
	    void 
	    BeginDiagnostic(IDiagnostic d);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures the completion of a diagnostic.
        /// </summary>
        /// 
        /// <param name="d">diagnostic that has ended</param>
        /// 
	    void 
	    EndDiagnostic(IDiagnostic d);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures failure of a diagnostic to begin running.
        /// </summary>
        /// 
        /// <param name="d">diagnostic that has failed to begin</param>
        /// <param name="e">exception that caused the failure</param>
        /// 
	    void 
	    ReportBeginFailure(IDiagnostic d,DiagnosticAbortedException e);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures success of a diagnostic check.
        /// </summary>
        /// 
        /// <param name="d">diagnostic that has succeeded</param>
        /// <param name="msg">success message</param>
        /// 
	    void 
	    ReportCheckSuccess(IDiagnostic d,String msg);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures failure of a diagnostic check.
        /// </summary>
        /// 
        /// <param name="d">diagnostic that has failed</param>
        /// <param name="e">exception that caused the failure</param>
        /// 
	    void 
	    ReportCheckFailure(IDiagnostic d,DiagnosticException e);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures success of a diagnostic recovery.
        /// </summary>
        /// 
        /// <param name="d">diagnostic whose recovery succeeded</param>
        /// <param name="msg">success message</param>
        /// 
	    void 
	    ReportRecoverySuccess(IDiagnostic d,String msg);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures failure of a diagnostic recovery.
        /// </summary>
        /// 
        /// <param name="d">diagnostic that has failed to recover</param>
        /// <param name="e">exception that caused the failure</param>
        /// 
	    void 
	    ReportRecoveryFailure(IDiagnostic d,DiagnosticException e);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures an unknown failure (Exception) encountered during
	    /// a diagnostic check or recovery.
        /// </summary>
        /// 
        /// <param name="d">diagnostic that has failed</param>
        /// <param name="e">exception that caused the failure</param>
        /// 
	    void 
	    ReportUnknownFailure(IDiagnostic d,Exception e);

    }
}

//  ##########################################################################
