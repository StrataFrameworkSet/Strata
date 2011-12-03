// ##########################################################################
// # File Name:		MockDiagnosticResult.java
// # Copyright(C):	2005, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.inspector.diagnostic;

import java.util.*;



/**
 * 
 * @author 		
 *     ASG 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class MockDiagnosticResult 
	extends AbstractDiagnosticResult
{
	private Set<String> itsActualRun;
	
	/************************************************************************
	 * Creates a new MockDiagnosticResult. 
	 *
	 */
	public MockDiagnosticResult()
	{
		super();
		itsActualRun = new TreeSet<String>();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see com.capgroup.af.solar.application.DiagnosticResult#beginDiagnostic(com.capgroup.af.solar.application.Diagnostic)
	 */

	public void 
	beginDiagnostic(Diagnostic d)
	{
		itsActualRun.add( d.getName() );
		super.beginDiagnostic( d );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */

	public void 
	endDiagnostic(Diagnostic d)
	{
		super.endDiagnostic( d );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	reportBeginFailure(Diagnostic d,DiagnosticAbortedException e)
	{
		super.reportBeginFailure( d,e );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */

	public void 
	reportCheckSuccess(Diagnostic d,String msg)
	{
		super.reportCheckSuccess( d,msg );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */

	public void 
	reportCheckFailure(Diagnostic d,DiagnosticException e)
	{
		super.reportCheckFailure( d,e );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */

	public void 
	reportRecoverySuccess(Diagnostic d,String msg)
	{
		super.reportRecoverySuccess( d,msg );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */

	public void 
	reportRecoveryFailure(Diagnostic d,DiagnosticException e)
	{
		super.reportRecoveryFailure( d,e );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public void 
	reportUnknownFailure(Diagnostic d,Exception e)
	{
		super.reportUnknownFailure( d,e );
	}

	
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	beginReports()
	{
		// TODO Auto-generated method stub
		
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	endReports()
	{
		// TODO Auto-generated method stub
		
	}

	/************************************************************************
	 * Verifies the actual results of a diagnostic check against 
	 * the specified expected results. 
	 *
	 */
	public void verify()
	{
		
	}
	
	/************************************************************************
	 * Returns the set of diagnostics that were run to 
	 * compose the current result. 
	 *
	 * @return	The set of diagnostics run for this result
	 */
	public Set<String> getActualRun()
	{
		return itsActualRun;
	}
	
	/************************************************************************
	 * Clears the current state of the result. 
	 *
	 */
	public void clear()
	{
		itsActualRun.clear();
	}

}


// ##########################################################################
