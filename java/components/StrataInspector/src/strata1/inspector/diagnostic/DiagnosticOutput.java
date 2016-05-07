// ##########################################################################
// # File Name:	DiagnosticOutput.java
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

import java.io.Serializable;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DiagnosticOutput
	implements Serializable
{
	private static final long		    serialVersionUID=5357142351413038395L;
	
	private final String 			    itsDiagnosticName;
	private final String 				itsDescription;
	private final DiagnosticResultState	itsResultState;
	private final Exception				itsException;
	
	/************************************************************************
	 * Creates a new DiagnosticOutput. 
	 *
	 * @param name
	 * @param description
	 * @param state
	 * @param exception
	 */
	public 
	DiagnosticOutput
	(
		String 				  name,
		String 				  description,
		DiagnosticResultState state,
		Exception             exception)
	{
		super();
		itsDiagnosticName = name;
		itsDescription    = description;
		itsResultState    = state;
		itsException      = exception;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public String
	getDiagnosticName()
	{
		return itsDiagnosticName;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public String
	getDescription()
	{
		return itsDescription;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public DiagnosticResultState
	getResultState()
	{
		return itsResultState;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public Exception
	getException()
	{
		return itsException;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public boolean
	hasException()
	{
		return itsException != null;
	}
}


// ##########################################################################
