//  ##########################################################################
//  # File Name: AbstractDiagnosticResult.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;

namespace Strata.Administration.Diagnostic
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>IDiagnosticResult</c> types.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract 
    class AbstractDiagnosticResult:
        IDiagnosticResult
    {
	    private readonly IList<IDiagnosticReporter> reporters;
	    private readonly IList<DiagnosticOutput>    contents;
	    private readonly DiagnosticOutputGenerator  generator;
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractDiagnosticResult</c> instance.
        /// </summary>
        /// 
	    protected 
	    AbstractDiagnosticResult()
	    {
		    reporters = new List<IDiagnosticReporter>();
		    contents  = new List<DiagnosticOutput>();
		    generator = new DiagnosticOutputGenerator();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see 
        /// cref="IDiagnosticResult.AttachReporter(IDiagnosticReporter)"/>
        /// </summary>
        /// 
	    public void 
	    AttachReporter(IDiagnosticReporter reporter)
	    {
		    if ( !HasReporter( reporter ) )
			    reporters.Add( reporter );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see 
        /// cref="IDiagnosticResult.DetachReporter(IDiagnosticReporter)"/>
        /// </summary>
        /// 
	    public void 
	    DetachReporter(IDiagnosticReporter reporter)
	    {
		    reporters.Remove( reporter );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.GetReporters()"/>
        /// </summary>
        /// 
	    public IList<IDiagnosticReporter> 
	    GetReporters()
	    {
		    return new ReadOnlyCollection<IDiagnosticReporter>( reporters );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.HasReporters()"/>
        /// </summary>
        /// 
	    public bool
	    HasReporters()
	    {
		    return reporters.Count != 0;
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.HasReporter(IDiagnosticReporter)"/>
        /// </summary>
        /// 
	    public bool 
	    HasReporter(IDiagnosticReporter reporter)
	    {
		    return reporters.Contains( reporter );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.BeginReports()"/>
        /// </summary>
        /// 
	    public virtual void 
	    BeginReports()
	    {
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.BeginReport();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.EndReports()"/>
        /// </summary>
        /// 
	    public virtual void 
	    EndReports()
	    {
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.EndReport();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver.BeginDiagnostic(IDiagnostic)"/>
        /// </summary>
        /// 
	    public virtual void 
	    BeginDiagnostic(IDiagnostic d)
	    {
		    DiagnosticOutput output = null;
		
		    generator.DiagnosticName = d.Name;
		    output = generator.GenerateOutput();
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.BeginDiagnostic( output );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver.EndDiagnostic(IDiagnostic)"/>
        /// </summary>
        /// 
	    public virtual void 
	    EndDiagnostic(IDiagnostic d)
	    {
		    DiagnosticOutput output =  generator.GenerateOutput();
		
		    contents.Add( output );
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.EndDiagnostic( output );
		
		    generator.Clear();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver
        ///                .ReportBeginFailure(
        ///                    IDiagnostic,DiagnosticAbortedException)"/>
        /// </summary>
        /// 
	    public virtual void 
	    ReportBeginFailure(IDiagnostic d,DiagnosticAbortedException e)
	    {
		    DiagnosticOutput output = null;
		
		    generator.ResultState = DiagnosticResultState.ABORTED;
		    generator.Description = "diagnostic failed to run.";
		    generator.Exception = e ;
		
		    output = generator.GenerateOutput();
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.ReportBeginFailure( output );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver
        ///                .ReportCheckSuccess(IDiagnostic,String)"/>
        /// </summary>
        /// 
	    public virtual void 
	    ReportCheckSuccess(IDiagnostic d,String msg)
	    {
		    DiagnosticOutput output = null;
		
		    generator.ResultState = DiagnosticResultState.SUCCEEDED;
		    generator.Description = msg;
		    generator.Exception = null;
		
		    output = generator.GenerateOutput();
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.ReportCheckSuccess( output );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver
        ///                .ReportCheckFailure(
        ///                    IDiagnostic,DiagnosticException)"/>
        /// </summary>
        /// 
	    public virtual void 
	    ReportCheckFailure(IDiagnostic d,DiagnosticException e)
	    {
		    DiagnosticOutput output = null;
		
		    generator.ResultState = DiagnosticResultState.FAILED;
		    generator.Description = "diagnostic failed with exception:";
		    generator.Exception   = e ;
		
		    output = generator.GenerateOutput();
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.ReportCheckFailure( output );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver
        ///                .ReportRecoverySuccess(
        ///                    IDiagnostic,String)"/>
        /// </summary>
        /// 
	    public virtual void 
	    ReportRecoverySuccess(IDiagnostic d,String msg)
	    {
		    DiagnosticOutput output = null;
		
		    generator.ResultState = DiagnosticResultState.RECOVERED;
		    generator.Description = msg;
		    generator.Exception   = null;
		
		    output = generator.GenerateOutput();
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.ReportRecoverySuccess( output );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver
        ///                .ReportRecoveryFailure(
        ///                    IDiagnostic,DiagnosticException)"/>
        /// </summary>
        /// 
	    public virtual void 
	    ReportRecoveryFailure(IDiagnostic d,DiagnosticException e)
	    {
		    DiagnosticOutput output = null;

		    generator.ResultState = DiagnosticResultState.FAILED;
		    generator.Description =  
		        "diagnostic recovery failed with exception:";
		    generator.Exception   = e;
		
		    output = generator.GenerateOutput();
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.ReportRecoveryFailure( output );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticReceiver
        ///                .ReportUnknownFailure(IDiagnostic d,Exception)"/>
        /// </summary>
        /// 
	    public virtual void 
	    ReportUnknownFailure(IDiagnostic d,Exception e)
	    {
		    DiagnosticOutput output = null;
		
		    generator.ResultState = DiagnosticResultState.FAILED;
		    generator.Description = "diagnostic failed with exception:";
		    generator.Exception   = e;
		
		    output = generator.GenerateOutput();
		
		    foreach (IDiagnosticReporter reporter in reporters)
			    reporter.ReportUnknownFailure( output );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.GetNumberOfDiagnostics()"/>
        /// </summary>
        /// 
	    public int 
	    GetNumberOfDiagnostics()
	    {
		    return contents.Count;
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.GetNumberOfAborts()"/>
        /// </summary>
        /// 
	    public int 
	    GetNumberOfAborts()
	    {
            return 
                contents
                    .Count(
                        o => o.ResultState == DiagnosticResultState.ABORTED);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.GetNumberOfSuccesses()"/>
        /// </summary>
        /// 
	    public int 
	    GetNumberOfSuccesses()
	    {
            return 
                contents
                    .Count(
                        o => o.ResultState == DiagnosticResultState.SUCCEEDED);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.GetNumberOfFailures()"/>
        /// </summary>
        /// 
	    public int 
	    GetNumberOfFailures()
	    {
            return 
                contents
                    .Count(
                        o => o.ResultState == DiagnosticResultState.FAILED);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnosticResult.GetContents()"/>
        /// </summary>
        /// 
	    public IList<DiagnosticOutput> 
	    GetContents()
	    {
		    return new ReadOnlyCollection<DiagnosticOutput>(contents);
	    }

    }
}

//  ##########################################################################
