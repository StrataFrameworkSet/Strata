//  ##########################################################################
//  # File Name: MockDiagnosticCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Threading.Tasks;
using Strata.Diagnostic.Core.Evaluation;

namespace Strata.Diagnostic.Core.Common
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class MockDiagnosticCheck:
        DiagnosticCheck
    {
        public bool CheckFlag { get; set; }
        public bool RecoveryFlag { get; set; }
        public bool CanRecoverFlag { get; set; }
        public bool UnknownFlag { get; set; }


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DirectoryExistsCheck</c> instance.
        /// </summary>
        /// 
        public 
        MockDiagnosticCheck(
            string name,
            bool   check,
            bool   recover,
            bool   canRecover,
            bool   unknown):
            base( name )
        {
            CheckFlag      = check;
            RecoveryFlag   = recover;
            CanRecoverFlag = canRecover;
            UnknownFlag    = unknown;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the specified directory path exists.
        /// </summary>
        /// 
        protected override Task<string>
        RunCheck()
        {
		    if ( !CheckFlag )
		    {
			    if ( !UnknownFlag )
				    throw new DiagnosticException( GetCheckFailureMessage() );
			    else
				    throw new Exception( "Check: Unknown Exception" );
		    }
		
		    return Task.FromResult(GetCheckSuccessMessage());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This diagnostic check does not perform recovery.
        /// </summary>
        /// 
        protected override Task<string> 
        RunRecovery()
        {
		    if ( !RecoveryFlag )
		    {
			    if ( !UnknownFlag )
				    throw new DiagnosticException( GetRecoveryFailureMessage() );
			    else
				    throw new Exception( "Recovery: Unknown Exception" );
		    }	
			
		    return Task.FromResult(GetRecoverySuccessMessage());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This diagnostic check does not perform recovery.
        /// </summary>
        /// 
        protected override bool 
        IsRecoverable()
        {
            return CanRecoverFlag;
        }

	    /************************************************************************
	     * Returns the success message associated with the diagnostic check. 
	     *
	     * @return	success message
	     */
	    public string 
        GetCheckSuccessMessage()
	    {
		    return Name + ": Check has succeeded.";
	    }

	    /************************************************************************
	     * Returns the failure message associated with the diagnostic check. 
	     *
	     * @return	failure message
	     */
	    public string 
        GetCheckFailureMessage()
	    {
		    return Name + ": Check has failed.";
	    }

	    /************************************************************************
	     * Returns the success message associated with the diagnostic recovery. 
	     *
	     * @return	success message
	     */
	    public string 
        GetRecoverySuccessMessage()
	    {
		    return Name + ": Recovery has succeeded.";
	    }

	    /************************************************************************
	     * Returns the failure message associated with the diagnostic recovery. 
	     *
	     * @return	failure message
	     */
	    public string 
        GetRecoveryFailureMessage()
	    {
		    return Name + ": Recovery has failed.";
	    }

    }
}

//  ##########################################################################
