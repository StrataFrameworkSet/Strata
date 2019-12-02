//  ##########################################################################
//  # File Name: IDiagnosticResult.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;

namespace Strata.Diagnostic.Core.Common
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Extends <c>IDiagnosticReceiver</c> with methods for 
    /// attaching/detaching <c>IDiagnosticReporter</c>s, collecting
    /// diagnostic output, and tracking diagnostic metrics such as 
    /// number of diagnostics run, number of aborts, number of failures, 
    /// and number of successes.
    /// </summary>
    ///  
    public 
    interface IDiagnosticResult:
        IDiagnosticReceiver
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Attaches a <c>IDiagnosticReporter</c> to this 
        /// <c>IDiagnosticResult</c> object.
        /// </summary>
        /// 
        /// <param name="reporter">reporter being attached</param>
        /// <postcondition>
        /// this.HasReporter( reporter ) == true
        /// </postcondition>
        /// 
	    void
	    AttachReporter(IDiagnosticReporter reporter);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Detaches the specified <c>IDiagnosticReporter</c>  
        /// from this <c>IDiagnosticResult</c> object.
        /// </summary>
        /// 
        /// <param name="reporter">reporter being detached</param>
        /// <postcondition>
        /// this.HasReporter( reporter ) == false
        /// </postcondition>
        /// 
	    void
	    DetachReporter(IDiagnosticReporter reporter);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the <c>IDiagnosticReporter</c>s currently 
        /// attached to this <c>IDiagnosticResult</c> object.
        /// </summary>
        /// 
        /// <returns>currently attached reporters</returns>
        /// 
	    IList<IDiagnosticReporter>
	    GetReporters();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if this <c>IDiagnosticResult</c> object 
        /// has attached <c>IDiagnosticReporter</c>s.
        /// </summary>
        /// 
        /// <returns>true if reporters are currently attached</returns>
        /// 
	    bool
	    HasReporters();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if the specified <c>IDiagnosticReporter</c>
        /// is attached to this <c>IDiagnosticResult</c> object.
        /// </summary>
        /// 
        /// <param name="reporter">specified reporter</param>
        /// <returns>true if specified reporter is attached</returns>
        /// 
	    bool
	    HasReporter(IDiagnosticReporter reporter);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Tells all attached <c>IDiagnosticReporter</c>s 
        /// to begin reporting.
        /// </summary>
        /// 
	    void
	    BeginReports();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Tells all attached <c>IDiagnosticReporter</c>s 
        /// to end reporting.
        /// </summary>
        /// 
	    void
	    EndReports();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the number of diagnostics that were run.
        /// </summary>
        /// 
        /// <returns>number of diagnostics run</returns>
        /// 
	    int
	    GetNumberOfDiagnostics();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the number of diagnostics that were aborted.
        /// </summary>
        /// 
        /// <returns>number of diagnostics aborted</returns>
        /// 
	    int
	    GetNumberOfAborts();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the number of diagnostics that succeeded.
        /// </summary>
        /// 
        /// <returns>number of diagnostics successes</returns>
        /// 
	    int
	    GetNumberOfSuccesses();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the number of diagnostics that failed.
        /// </summary>
        /// 
        /// <returns>number of diagnostics failures</returns>
        /// 
	    int
	    GetNumberOfFailures();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the detailed contents of 
        /// this <c>IDiagnosticResult</c>.
        /// </summary>
        /// 
        /// <returns>contents of diagnostics</returns>
        /// 
	    IList<DiagnosticOutput>
	    GetContents();

    }
}

//  ##########################################################################
