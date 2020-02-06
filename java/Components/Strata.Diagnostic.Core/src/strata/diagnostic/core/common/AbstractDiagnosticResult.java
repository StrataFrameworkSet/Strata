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

package strata.diagnostic.core.common;

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
	implements IDiagnosticResult
{
	private List<IDiagnosticReporter>  itsReporters;
	private List<DiagnosticOutput>    itsContents;
	private DiagnosticOutputGenerator itsGenerator;
	
	/************************************************************************
	 * Creates a new AbstractDiagnosticResult. 
	 *
	 */
	public 
	AbstractDiagnosticResult()
	{
		itsReporters = new LinkedList<IDiagnosticReporter>();
		itsContents  = new LinkedList<DiagnosticOutput>();
		itsGenerator = new DiagnosticOutputGenerator();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#attachReporter(IDiagnosticReporter)
	 */
	@Override
	public void 
	attachReporter(IDiagnosticReporter reporter)
	{
		if ( !hasReporter( reporter ) )
			itsReporters.add( reporter );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#detachReporter(IDiagnosticReporter)
	 */
	@Override
	public void 
	detachReporter(IDiagnosticReporter reporter)
	{
		itsReporters.remove( reporter );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#getReporters()
	 */
	@Override
	public List<IDiagnosticReporter> 
	getReporters()
	{
		return new LinkedList<IDiagnosticReporter>( itsReporters );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#hasReporters()
	 */
	@Override
	public boolean 
	hasReporters()
	{
		return !itsReporters.isEmpty();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#hasReporter(IDiagnosticReporter)
	 */
	@Override
	public boolean 
	hasReporter(IDiagnosticReporter reporter)
	{
		return itsReporters.contains( reporter );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#beginReports()
	 */
	@Override
	public void 
	beginReports()
	{
		for (IDiagnosticReporter reporter:getReporters())
			reporter.beginReport();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#endReports()
	 */
	@Override
	public void 
	endReports()
	{
		for (IDiagnosticReporter reporter:getReporters())
			reporter.endReport();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#beginDiagnostic(IDiagnostic)
	 */
	@Override
	public void 
	beginDiagnostic(IDiagnostic d)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setDiagnosticName( d.getName() );
		output = itsGenerator.generateOutput();
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.beginDiagnostic( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#endDiagnostic(IDiagnostic)
	 */
	@Override
	public void 
	endDiagnostic(IDiagnostic d)
	{
		DiagnosticOutput output =  itsGenerator.generateOutput();
		
		itsContents.add( output );
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.endDiagnostic( output );
		
		itsGenerator.clear();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#reportBeginFailure(
	 * 			IDiagnostic,
	 * 			DiagnosticAbortedException)
	 */
	@Override
	public void 
	reportBeginFailure(IDiagnostic d,DiagnosticAbortedException e)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.ABORTED );
		itsGenerator.setDescription( "IDiagnostic failed to run." );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.reportBeginFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#reportCheckSuccess(IDiagnostic,String)
	 */
	@Override
	public void 
	reportCheckSuccess(IDiagnostic d,String msg)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.SUCCEEDED );
		itsGenerator.setDescription( msg );
		itsGenerator.setException( null );
		
		output = itsGenerator.generateOutput();
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.reportCheckSuccess( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#reportCheckFailure(
	 * 			IDiagnostic,
	 * 			DiagnosticException)
	 */
	@Override
	public void 
	reportCheckFailure(IDiagnostic d,DiagnosticException e)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.FAILED );
		itsGenerator.setDescription( "IDiagnostic failed with exception:" );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.reportCheckFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#reportRecoverySuccess(IDiagnostic,String)
	 */
	@Override
	public void 
	reportRecoverySuccess(IDiagnostic d,String msg)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.RECOVERED );
		itsGenerator.setDescription( msg );
		itsGenerator.setException( null );
		
		output = itsGenerator.generateOutput();
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.reportRecoverySuccess( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#reportRecoveryFailure(
	 *			IDiagnostic,
	 *			DiagnosticException)
	 */
	@Override
	public void 
	reportRecoveryFailure(IDiagnostic d,DiagnosticException e)
	{
		DiagnosticOutput output = null;

		itsGenerator.setResultState( DiagnosticResultState.FAILED );
		itsGenerator.setDescription( 
		    "IDiagnostic recovery failed with exception:" );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.reportRecoveryFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReceiver#reportUnknownFailure(IDiagnostic,Exception)
	 */
	@Override
	public void 
	reportUnknownFailure(IDiagnostic d,Exception e)
	{
		DiagnosticOutput output = null;
		
		itsGenerator.setResultState( DiagnosticResultState.FAILED );
		itsGenerator.setDescription( "IDiagnostic failed with exception:" );
		itsGenerator.setException( e );
		
		output = itsGenerator.generateOutput();
		
		for (IDiagnosticReporter reporter:getReporters())
			reporter.reportUnknownFailure( output );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#getNumberOfDiagnostics()
	 */
	@Override
	public int 
	getNumberOfDiagnostics()
	{
		return itsContents.size();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticResult#getNumberOfAborts()
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
	 * @see IDiagnosticResult#getNumberOfSuccesses()
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
	 * @see IDiagnosticResult#getNumberOfFailures()
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
	 * @see IDiagnosticResult#getContents()
	 */
	@Override
	public List<DiagnosticOutput> 
	getContents()
	{
		return Collections.unmodifiableList( itsContents );
	}

	
}


// ##########################################################################
