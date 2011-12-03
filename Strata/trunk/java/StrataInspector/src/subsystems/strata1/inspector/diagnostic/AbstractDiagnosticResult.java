// ##########################################################################
// # File Name:	AbstractDiagnosticResult.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataInspector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInspector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInspector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.inspector.diagnostic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractDiagnosticResult 
	implements DiagnosticResult
{
	private List<DiagnosticReporter>  itsReporters;
	private List<DiagnosticOutput>    itsContents;
	private DiagnosticOutputGenerator itsGenerator;
	
	/************************************************************************
	 * Creates a new AbstractDiagnosticResult. 
	 *
	 */
	public 
	AbstractDiagnosticResult()
	{
		itsReporters = new LinkedList<DiagnosticReporter>();
		itsContents  = new LinkedList<DiagnosticOutput>();
		itsGenerator = new DiagnosticOutputGenerator();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#attachReporter(DiagnosticReporter)
	 */
	@Override
	public void 
	attachReporter(DiagnosticReporter reporter)
	{
		if ( !hasReporter( reporter ) )
			itsReporters.add( reporter );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#detachReporter(DiagnosticReporter)
	 */
	@Override
	public void 
	detachReporter(DiagnosticReporter reporter)
	{
		itsReporters.remove( reporter );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#getReporters()
	 */
	@Override
	public List<DiagnosticReporter> 
	getReporters()
	{
		return new LinkedList<DiagnosticReporter>( itsReporters );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#hasReporters()
	 */
	@Override
	public boolean 
	hasReporters()
	{
		return !itsReporters.isEmpty();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#hasReporter(DiagnosticReporter)
	 */
	@Override
	public boolean 
	hasReporter(DiagnosticReporter reporter)
	{
		return itsReporters.contains( reporter );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#beginReports()
	 */
	@Override
	public void 
	beginReports()
	{
		for (DiagnosticReporter reporter:getReporters())
			reporter.beginReport();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#endReports()
	 */
	@Override
	public void 
	endReports()
	{
		for (DiagnosticReporter reporter:getReporters())
			reporter.endReport();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#beginDiagnostic(Diagnostic)
	 */
	@Override
	public void 
	beginDiagnostic(Diagnostic d)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setDiagnosticName( d.getName() );
		output = itsGenerator.generateOutput();
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.beginDiagnostic( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#endDiagnostic(Diagnostic)
	 */
	@Override
	public void 
	endDiagnostic(Diagnostic d)
	{
		DiagnosticOutput output =  itsGenerator.generateOutput();
		
		itsContents.add( output );
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.endDiagnostic( output );
		
		itsGenerator.clear();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#reportBeginFailure(
	 * 			Diagnostic,
	 * 			DiagnosticAbortedException)
	 */
	@Override
	public void 
	reportBeginFailure(Diagnostic d,DiagnosticAbortedException e)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.ABORTED );
		itsGenerator.setDescription( "Diagnostic failed to run." );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.reportBeginFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#reportCheckSuccess(Diagnostic,String)
	 */
	@Override
	public void 
	reportCheckSuccess(Diagnostic d,String msg)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.SUCCEEDED );
		itsGenerator.setDescription( msg );
		itsGenerator.setException( null );
		
		output = itsGenerator.generateOutput();
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.reportCheckSuccess( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#reportCheckFailure(
	 * 			Diagnostic,
	 * 			DiagnosticException)
	 */
	@Override
	public void 
	reportCheckFailure(Diagnostic d,DiagnosticException e)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.FAILED );
		itsGenerator.setDescription( "Diagnostic failed with exception:" );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.reportCheckFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#reportRecoverySuccess(Diagnostic,String)
	 */
	@Override
	public void 
	reportRecoverySuccess(Diagnostic d,String msg)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.RECOVERED );
		itsGenerator.setDescription( msg );
		itsGenerator.setException( null );
		
		output = itsGenerator.generateOutput();
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.reportRecoverySuccess( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#reportRecoveryFailure(
	 *			Diagnostic,
	 *			DiagnosticException)
	 */
	@Override
	public void 
	reportRecoveryFailure(Diagnostic d,DiagnosticException e)
	{
		DiagnosticOutput output = null;

		itsGenerator.setResultState( DiagnosticResultState.FAILED );
		itsGenerator.setDescription( "Diagnostic recovery failed with exception:" );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.reportRecoveryFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReceiver#reportUnknownFailure(Diagnostic,Exception)
	 */
	@Override
	public void 
	reportUnknownFailure(Diagnostic d,Exception e)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.FAILED );
		itsGenerator.setDescription( "Diagnostic failed with exception:" );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (DiagnosticReporter reporter:getReporters())
			reporter.reportUnknownFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#getNumberOfDiagnostics()
	 */
	@Override
	public int 
	getNumberOfDiagnostics()
	{
		return itsContents.size();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#getNumberOfAborts()
	 */
	@Override
	public int 
	getNumberOfAborts()
	{
		int aborts = 0;
		
		for (DiagnosticOutput output:getContents())
			if ( output.getResultState() == DiagnosticResultState.ABORTED )
				++aborts;
		
		return aborts;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#getNumberOfSuccesses()
	 */
	@Override
	public int 
	getNumberOfSuccesses()
	{
		int successes = 0;
		
		for (DiagnosticOutput output:getContents())
			if ( output.getResultState() == DiagnosticResultState.SUCCEEDED )
				++successes;
		
		return successes;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#getNumberOfFailures()
	 */
	@Override
	public int 
	getNumberOfFailures()
	{
		int failures = 0;
		
		for (DiagnosticOutput output:getContents())
			if ( output.getResultState() == DiagnosticResultState.FAILED )
				++failures;
		
		return failures;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticResult#getContents()
	 */
	@Override
	public List<DiagnosticOutput> 
	getContents()
	{
		return Collections.unmodifiableList( itsContents );
	}

	
}


// ##########################################################################
