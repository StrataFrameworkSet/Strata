//  ##########################################################################
//  # File Name: IDiagnosticReporter.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################


namespace Strata.Diagnostic.Core.Common
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Emits a diagnostic report of some kind based on the diagnostic output
    /// resulting from running one or more diagnostics.
    /// </summary>
    ///  
    public 
    interface IDiagnosticReporter
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Signals the beginning of a diagnostic report.
        /// </summary>
        /// 
	    void
        BeginReport();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Signals the end of a diagnostic report.
        /// </summary>
        /// 
	    void
        EndReport();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures the beginning of a diagnostic. 
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
	    void
        BeginDiagnostic(DiagnosticOutput output);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures the completion of a diagnostic.
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
	    void
        EndDiagnostic(DiagnosticOutput output);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures failure of a diagnostic to begin running.
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
	    void
        ReportBeginFailure(DiagnosticOutput output);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures success of a diagnostic check. 
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
	    void
        ReportCheckSuccess(DiagnosticOutput output);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures failure of a diagnostic check.  
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
	    void
        ReportCheckFailure(DiagnosticOutput output);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures success of a diagnostic recovery. 
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
	    void
        ReportRecoverySuccess(DiagnosticOutput output);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures failure of a diagnostic recovery. 
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
	    void
        ReportRecoveryFailure(DiagnosticOutput output);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Captures an unknown failure (Exception) encountered during
        /// a diagnostic check or recovery.
        /// </summary>
        ///
        /// <param name="output">diagnostic output being reported</param>
        /// 
        void
        ReportUnknownFailure(DiagnosticOutput output);

    }
}

//  ##########################################################################
