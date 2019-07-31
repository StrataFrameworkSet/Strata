//  ##########################################################################
//  # File Name: MockDiagnosticCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

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
            String name,
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
        protected override String 
        RunCheck()
        {
		    if ( !CheckFlag )
		    {
			    if ( !UnknownFlag )
				    throw new DiagnosticException( GetCheckFailureMessage() );
			    else
				    throw new Exception( "Check: Unknown Exception" );
		    }
		
		    return GetCheckSuccessMessage();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This diagnostic check does not perform recovery.
        /// </summary>
        /// 
        protected override String 
        RunRecovery()
        {
		    if ( !RecoveryFlag )
		    {
			    if ( !UnknownFlag )
				    throw new DiagnosticException( GetRecoveryFailureMessage() );
			    else
				    throw new Exception( "Recovery: Unknown Exception" );
		    }	
			
		    return GetRecoverySuccessMessage();
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
	    public String 
        GetCheckSuccessMessage()
	    {
		    return Name + ": Check has succeeded.";
	    }

	    /************************************************************************
	     * Returns the failure message associated with the diagnostic check. 
	     *
	     * @return	failure message
	     */
	    public String 
        GetCheckFailureMessage()
	    {
		    return Name + ": Check has failed.";
	    }

	    /************************************************************************
	     * Returns the success message associated with the diagnostic recovery. 
	     *
	     * @return	success message
	     */
	    public String 
        GetRecoverySuccessMessage()
	    {
		    return Name + ": Recovery has succeeded.";
	    }

	    /************************************************************************
	     * Returns the failure message associated with the diagnostic recovery. 
	     *
	     * @return	failure message
	     */
	    public String 
        GetRecoveryFailureMessage()
	    {
		    return Name + ": Recovery has failed.";
	    }

    }
}

//  ##########################################################################
