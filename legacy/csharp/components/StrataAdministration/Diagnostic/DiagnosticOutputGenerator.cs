//  ##########################################################################
//  # File Name: DiagnosticOutputGenerator.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

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
    class DiagnosticOutputGenerator
    {
        public String                DiagnosticName { get; set; }
        public String                Description { get; set; }
        public DiagnosticResultState ResultState { get; set; }
        public Exception             Exception { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DiagnosticOutputGenerator</c> instance.
        /// </summary>
        /// 
	    public 
	    DiagnosticOutputGenerator()
	    {
		    Clear();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Generates a new <c>DiagnosticOutput</c> instance.
        /// </summary>
        /// 
	    public DiagnosticOutput
	    GenerateOutput()
	    {
		    return 
			    new DiagnosticOutput(
			            DiagnosticName,
			            Description,
			            ResultState,
			            Exception );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Clears all fields.
        /// </summary>
        /// 
	    public void 
	    Clear()
	    {
		    DiagnosticName = String.Empty;
		    Description    = String.Empty;
		    ResultState    = DiagnosticResultState.NONE;
		    Exception      = null;

	    }

    }
}

//  ##########################################################################
