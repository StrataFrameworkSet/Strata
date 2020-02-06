//  ##########################################################################
//  # File Name: DiagnosticOutputGenerator.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Diagnostic.Core.Common
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class DiagnosticOutputGenerator
    {
        public string                DiagnosticName { get; set; }
        public string                Description { get; set; }
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
