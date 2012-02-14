// ##########################################################################
// # File Name:	DiagnosticTask.java
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


/**
 * Encapsulates the execution of {@code Diagnostic}s so they
 * can be scheduled to run at specified times.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DiagnosticTask 
	implements Runnable
{
	private Diagnostic       itsDiagnostic;
	private DiagnosticResult itsResult;
	
	/************************************************************************
	 * Creates a new DiagnosticTask. 
	 *
	 * @param diagnostic
	 * @param result
	 */
	public 
	DiagnosticTask(Diagnostic diagnostic,DiagnosticResult result)
	{
		super();
		itsDiagnostic = diagnostic;
		itsResult     = result;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	run()
	{
		itsDiagnostic.runDiagnostic( itsResult );
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public String 
	getName()
	{
		return itsDiagnostic.getName();
	}
}

//##########################################################################
