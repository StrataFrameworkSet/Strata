// ##########################################################################
// # File Name:	PrintWriterDiagnosticReporter.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInspector Framework.
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

package strata1.inspector.outputstreamdiagnosticreporter;

import strata1.inspector.diagnostic.DiagnosticOutput;
import strata1.inspector.diagnostic.IDiagnosticReporter;

import java.io.PrintStream;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PrintStreamDiagnosticReporter 
	implements IDiagnosticReporter
{
	private PrintStream itsWriter;
	
	/************************************************************************
	 * Creates a new PrintWriterDiagnosticReporter. 
	 *
	 */
	public 
	PrintStreamDiagnosticReporter()
	{
		itsWriter = System.out;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#beginReport()
	 */
	@Override
	public void 
	beginReport()
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#endReport()
	 */
	@Override
	public void 
	endReport()
	{
		//itsWriter.flush();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#beginDiagnostic(DiagnosticOutput)
	 */
	@Override
	public void 
	beginDiagnostic(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#endDiagnostic(DiagnosticOutput)
	 */
	@Override
	public void 
	endDiagnostic(DiagnosticOutput output)
	{
		switch ( output.getResultState() )
		{
		case SUCCEEDED:
			itsWriter.printf( 
				"Succeeded: <%1$s> %2$s\n\n",
				output.getDiagnosticName(),
				output.getDescription() );
			break;
			
		case FAILED:
			itsWriter.printf( 
				"Failed: <%1$s> %2$s %3$s\n\n",
				output.getDiagnosticName(),
				output.getDescription(),
				output.getException().getMessage() );
			break;
			
		case RECOVERED:
			itsWriter.printf( 
				"Recovered: <%1$s> %2$s\n\n",
				output.getDiagnosticName(),
				output.getDescription() );
			break;
			
		case ABORTED:
			itsWriter.printf( 
				"Aborted: <%1$s> %2$s %3$s\n\n",
				output.getDiagnosticName(),
				output.getDescription(),
				output.getException().getMessage() );
			break;
			
		default:
			throw new IllegalStateException();
		}
		
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#reportBeginFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportBeginFailure(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#reportCheckSuccess(DiagnosticOutput)
	 */
	@Override
	public void 
	reportCheckSuccess(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#reportCheckFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportCheckFailure(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#reportRecoverySuccess(DiagnosticOutput)
	 */
	@Override
	public void 
	reportRecoverySuccess(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#reportRecoveryFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportRecoveryFailure(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#reportUnknownFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportUnknownFailure(DiagnosticOutput output)
	{
	}

}


// ##########################################################################
