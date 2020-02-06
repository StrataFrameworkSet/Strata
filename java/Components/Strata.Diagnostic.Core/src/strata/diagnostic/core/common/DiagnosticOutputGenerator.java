// ##########################################################################
// # File Name:	DiagnosticOutputGenerator.java
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

package strata.diagnostic.core.common;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
class DiagnosticOutputGenerator
{
	private String 				    itsDiagnosticName;
	private String 					itsDescription;
	private DiagnosticResultState	itsResultState;
	private Exception				itsException;

	/************************************************************************
	 * Creates a new DiagnosticOutputGenerator. 
	 *
	 */
	public 
	DiagnosticOutputGenerator()
	{
		super();
		clear();
	}

	/************************************************************************
	 *  
	 *
	 * @param name
	 */
	public void
	setDiagnosticName(String name)
	{
		itsDiagnosticName = name;
	}
	
	/************************************************************************
	 *  
	 *
	 * @param description
	 */
	public void
	setDescription(String description)
	{
		itsDescription = description;
	}
	
	/************************************************************************
	 *  
	 *
	 * @param state
	 */
	public void
	setResultState(DiagnosticResultState state)
	{
		itsResultState = state;
	}

	/************************************************************************
	 *  
	 *
	 * @param exception
	 */
	public void
	setException(Exception exception)
	{
		itsException = exception;
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public DiagnosticOutput
	generateOutput()
	{
		return 
			new DiagnosticOutput(
			        itsDiagnosticName,
			        itsDescription,
			        itsResultState,
			        itsException );
	}

	/************************************************************************
	 *  
	 *
	 */
	public void 
	clear()
	{
		itsDiagnosticName = "";
		itsDescription    = "";
		itsResultState    = null;
		itsException      = null;

	}
}


// ##########################################################################
