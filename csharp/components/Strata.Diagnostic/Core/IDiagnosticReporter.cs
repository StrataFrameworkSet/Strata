//  ##########################################################################
//  # File Name: IDiagnosticReporter.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################


namespace Strata.Diagnostic.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IDiagnosticReporter
    {
	    /************************************************************************
	     *  
	     *
	     */
	    void
	    BeginReport();
	
	    /************************************************************************
	     *  
	     *
	     */
	    void
	    EndReport();
	
	    /************************************************************************
	     * Captures the beginning of a diagnostic. 
	     *
	     * @param output
	     */
	    void 
	    BeginDiagnostic(DiagnosticOutput output);
	
	    /************************************************************************
	     * Captures the completion of a diagnostic.
	     *
	     * @param d
	     */
	    void 
	    EndDiagnostic(DiagnosticOutput output);
	
	    /************************************************************************
	     * Captures failure of a diagnostic to begin running. 
	     *
	     * @param d
	     * @param e
	     */
	    void 
	    ReportBeginFailure(DiagnosticOutput output);
	
	    /************************************************************************
	     * Captures success of a diagnostic check. 
	     *
	     * @param d
	     * @param msg
	     */
	    void 
	    ReportCheckSuccess(DiagnosticOutput output);
	
	    /************************************************************************
	     * Captures failure of a diagnostic check. 
	     *
	     * @param d
	     * @param e
	     */
	    void 
	    ReportCheckFailure(DiagnosticOutput output);
	
	    /************************************************************************
	     * Captures success of a diagnostic recovery. 
	     *
	     * @param d
	     * @param msg
	     */
	    void 
	    ReportRecoverySuccess(DiagnosticOutput output);
	
	    /************************************************************************
	     * Captures failure of a diagnostic recovery. 
	     *
	     * @param d
	     * @param e
	     */
	    void 
	    ReportRecoveryFailure(DiagnosticOutput output);
	
	    /************************************************************************
	     * Captures an unknown failure (Exception) encountered during
	     * a diagnostic check or recovery. 
	     *
	     * @param d
	     * @param e
	     */
	    void 
	    ReportUnknownFailure(DiagnosticOutput output);

    }
}

//  ##########################################################################
