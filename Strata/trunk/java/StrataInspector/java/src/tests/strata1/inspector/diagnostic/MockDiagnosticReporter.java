// ##########################################################################
// # File Name:	MockDiagnosticReporter.java
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

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class MockDiagnosticReporter 
	implements DiagnosticReporter
{

	/************************************************************************
	 * Creates a new MockDiagnosticReporter. 
	 *
	 */
	public 
	MockDiagnosticReporter()
	{
		// TODO Auto-generated constructor stub
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see strata1.inspector.diagnostic.DiagnosticReporter#beginReport()
	 */
	@Override
	public void 
	beginReport()
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see strata1.inspector.diagnostic.DiagnosticReporter#endReport()
	 */
	@Override
	public void 
	endReport()
	{
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see strata1.inspector.diagnostic.DiagnosticReporter#beginDiagnostic(strata1.inspector.diagnostic.DiagnosticOutput)
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

}


// ##########################################################################
