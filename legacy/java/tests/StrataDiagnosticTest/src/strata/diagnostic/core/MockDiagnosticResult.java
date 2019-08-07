// ##########################################################################
// # File Name:		MockDiagnosticResult.java
// # Copyright(C):	2005, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata.diagnostic.core;

import java.util.Set;
import java.util.TreeSet;



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
	 */
	@Override
	public void 
	beginDiagnostic(IDiagnostic d)
	{
		itsActualRun.add( d.getName() );
		super.beginDiagnostic( d );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public void 
	endDiagnostic(IDiagnostic d)
	{
		super.endDiagnostic( d );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	reportBeginFailure(IDiagnostic d,DiagnosticAbortedException e)
	{
		super.reportBeginFailure( d,e );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public void 
	reportCheckSuccess(IDiagnostic d,String msg)
	{
		super.reportCheckSuccess( d,msg );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public void 
	reportCheckFailure(IDiagnostic d,DiagnosticException e)
	{
		super.reportCheckFailure( d,e );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public void 
	reportRecoverySuccess(IDiagnostic d,String msg)
	{
		super.reportRecoverySuccess( d,msg );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public void 
	reportRecoveryFailure(IDiagnostic d,DiagnosticException e)
	{
		super.reportRecoveryFailure( d,e );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public void 
	reportUnknownFailure(IDiagnostic d,Exception e)
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
