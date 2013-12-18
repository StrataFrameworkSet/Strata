// ##########################################################################
// # File Name:		MockDiagnosticCheck.java
// # Copyright(C):	2005, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.inspector.diagnostic;


/**
 * 
 * @author 		
 *     ASG 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class MockDiagnosticCheck 
	extends DiagnosticCheck
{
	private boolean itsCheckFlag;
	private boolean itsRecoveryFlag;
	private boolean itsCanRecoverFlag;
	private boolean itsUnknownFlag;
	
	/************************************************************************
	 * Creates a new MockDiagnosticCheck. 
	 *
	 * @param name
	 */
	public MockDiagnosticCheck(
		String 	name,
		boolean check,
		boolean recovery,
		boolean canRecover,
		boolean unknown)
	{
		super( name );
		itsCheckFlag      = check;
		itsRecoveryFlag   = recovery;
		itsCanRecoverFlag = canRecover;
		itsUnknownFlag    = unknown;
	}
	
	/************************************************************************
	 * Returns the success message associated with the diagnostic check. 
	 *
	 * @return	success message
	 */
	public String getCheckSuccessMessage()
	{
		return getName() + ": Check has succeeded.";
	}

	/************************************************************************
	 * Returns the failure message associated with the diagnostic check. 
	 *
	 * @return	failure message
	 */
	public String getCheckFailureMessage()
	{
		return getName() + ": Check has failed.";
	}

	/************************************************************************
	 * Returns the success message associated with the diagnostic recovery. 
	 *
	 * @return	success message
	 */
	public String getRecoverySuccessMessage()
	{
		return getName() + ": Recovery has succeeded.";
	}

	/************************************************************************
	 * Returns the failure message associated with the diagnostic recovery. 
	 *
	 * @return	failure message
	 */
	public String getRecoveryFailureMessage()
	{
		return getName() + ": Recovery has failed.";
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see com.capgroup.af.solar.application.DiagnosticCheck#runCheck()
	 */
	protected String runCheck() throws DiagnosticException
	{
		if ( !itsCheckFlag )
		{
			if ( !itsUnknownFlag )
				throw new DiagnosticException( getCheckFailureMessage() );
			else
				throw new RuntimeException( "Check: Unknown Exception" );
		}
		
		return getCheckSuccessMessage();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see com.capgroup.af.solar.application.DiagnosticCheck#runRecovery()
	 */
	protected String runRecovery() throws DiagnosticException
	{
		if ( !itsRecoveryFlag )
		{
			if ( !itsUnknownFlag )
				throw new DiagnosticException( getRecoveryFailureMessage() );
			else
				throw new RuntimeException( "Recovery: Unknown Exception" );
		}	
			
		return getRecoverySuccessMessage();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see com.capgroup.af.solar.application.DiagnosticCheck#isRecoverable()
	 */
	protected boolean isRecoverable()
	{
		return itsCanRecoverFlag;
	}

}


// ##########################################################################
