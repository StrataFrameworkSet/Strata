// ##########################################################################
// # File Name:	AbstractMessageBasedDiagnosticReporter.java
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

package strata1.inspector.diagnostic;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractMessageBasedDiagnosticReporter
	implements DiagnosticReporter
{
	private PrintWriter itsWriter;
	
	/************************************************************************
	 * Creates a new AbstractMessageBasedDiagnosticReporter. 
	 *
	 */
	public 
	AbstractMessageBasedDiagnosticReporter()
	{
		super();
		itsWriter = new PrintWriter(new StringWriter());		
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#beginReport()
	 */
	@Override
	public void 
	beginReport()
	{
		itsWriter = new PrintWriter(new StringWriter());
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#endReport()
	 */
	@Override
	public void 
	endReport()
	{
		
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#beginDiagnostic(DiagnosticOutput)
	 */
	@Override
	public void 
	beginDiagnostic(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#endDiagnostic(DiagnosticOutput)
	 */
	@Override
	public void 
	endDiagnostic(DiagnosticOutput output)
	{
		switch ( output.getResultState() )
		{
		case ABORTED:
			itsWriter.format( 
				"Aborted: %1$s %1$s %1$s\n\n",
				output.getDiagnosticName(),
				output.getDescription(),
				output.getException().getMessage() );
			break;
			
		case FAILED:
			itsWriter.format( 
				"Failed: %1$s %1$s %1$s\n\n",
				output.getDiagnosticName(),
				output.getDescription(),
				output.getException().getMessage() );
			break;
			
		default:
			break;
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#reportBeginFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportBeginFailure(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#reportCheckSuccess(DiagnosticOutput)
	 */
	@Override
	public void 
	reportCheckSuccess(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#reportCheckFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportCheckFailure(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#reportRecoverySuccess(DiagnosticOutput)
	 */
	@Override
	public void 
	reportRecoverySuccess(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#reportRecoveryFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportRecoveryFailure(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticReporter#reportUnknownFailure(DiagnosticOutput)
	 */
	@Override
	public void 
	reportUnknownFailure(DiagnosticOutput output)
	{
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	protected String
	getMessage()
	{
		return itsWriter.toString();
	}
}


// ##########################################################################
