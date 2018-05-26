//  ##########################################################################
//  # File Name: DiagnosticSuite.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Diagnostic.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represents a suite of <c>IDiagnostic</c>s that are meant to be 
    /// run as a whole or as part of a larger suite. <c>DiagnosticSuite</c> 
    /// is a <a href=
    /// "http://en.wikipedia.org/wiki/Composite_pattern">Composite</a> of 
    /// <c>IDiagnostic</c>s.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class DiagnosticSuite:
        AbstractDiagnostic
    {
        private readonly IDictionary<String,IDiagnostic> diagnostics;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DiagnosticSuite</c> instance.
        /// </summary>
        /// 
        public 
        DiagnosticSuite(String name):
            base( name )
        {
            diagnostics = new Dictionary<String,IDiagnostic>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
	    public override void 
	    RunDiagnostic(IDiagnosticResult result)
	    {
		    try
		    {
			    result.BeginDiagnostic( this );
			    BeginDiagnosticMode();
			
			    foreach (IDiagnostic d in diagnostics.Values)
				    d.RunDiagnostic( result );
		    }
		    catch (DiagnosticAbortedException ae)
		    {
			    result.ReportBeginFailure( this,ae );
		    }
		    finally
		    {
			    EndDiagnosticMode();
			    result.EndDiagnostic( this );
		    }
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Adds a <c>IDiagnostic</c> to 
        /// the <c>DiagnosticSuite</c>.
        /// </summary>
        /// 
        /// <param name="d">diagnostic being added to suite</param>
        /// 
        /// <precondition>
        /// this.HasDiagnostic( d.Name ) == false
        /// </precondition>
        /// <postcondition>
        /// this.HasDiagnostic( d.Name ) == true
        /// </postcondition>
        /// <postcondition>
        /// this.GetDiagnostic( d.Name ) == d
        /// </postcondition>
        /// 
	    public void 
	    AddDiagnostic(IDiagnostic d)
	    {
		    diagnostics.Add( d.Name,d );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes the <c>IDiagnostic</c> with the specified 
        /// name from the <c>DiagnosticSuite</c>.
        /// </summary>
        /// 
        /// <param name="name">
        /// Name of diagnostic begin removed from suite
        /// </param>
        /// 
        /// <precondition>
        /// this.HasDiagnostic( name ) == true
        /// </precondition>
        /// <postcondition>
        /// this.HasDiagnostic( name ) == false
        /// </postcondition>
        /// 
	    public void 
	    RemoveDiagnostic(String name)
	    {
		    diagnostics.Remove( name );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the <c>IDiagnostic</c> associated 
        /// with the specified name.
        /// </summary>
        /// 
        /// <param name="name">Name of the diagnostic being queried</param>
        /// <returns>diagnostic associated with name</returns>
        /// 
	    public IDiagnostic 
	    GetDiagnostic(String name)
	    {
		    return diagnostics[name];
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Queries the <c>DiagnosticSuite</c> if it contains an
        /// <c>IDiagnostic</c> with the specified name.
        /// </summary>
        /// 
        /// <param name="name">Name of the diagnostic being queried</param>
        /// <returns>
        /// True if the suite contains a diagnostic with the specified name.
        /// </returns>
        /// 
	    public bool 
	    HasDiagnostic(String name)
	    {
		    return diagnostics.ContainsKey( name );
	    }

    }
}

//  ##########################################################################
