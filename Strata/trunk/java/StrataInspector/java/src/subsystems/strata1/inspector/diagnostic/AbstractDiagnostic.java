// ##########################################################################
// # File Name:	AbstractDiagnostic.java
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
 * Base class for all {@code Diagnostic}s.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractDiagnostic 
	implements Diagnostic
{
	protected String itsName;

	/************************************************************************
	 * Creates a new AbstractDiagnostic. 
	 *
	 * @param name	The name of the diagnostic
	 */
	public 
	AbstractDiagnostic(String name)
	{
		super();
		itsName = name;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Diagnostic#getName()
	 */
	public String 
	getName()
	{
		return itsName;
	}

	/************************************************************************
	 * Subclasses override this method to implement mechanisms for
	 * transitioning the subject of the diagnostic (e.g. a subsystem, 
	 * component, or other resource) from its "normal" operating mode
	 * to a "diagnostic" mode in which the diagnostic can be run safely 
	 * without compromising the correctness of the enclosing system.
	 *  
	 * @throws	DiagnosticAbortedException	Indicates that the subject 
	 * 			of the diagnostic could not safely transition to its 
	 * 			diagnostic mode.
	 */
	protected void 
	beginDiagnosticMode()
		throws DiagnosticAbortedException
	{	
	}

	/************************************************************************
	 * Subclasses override this method to implement mechanisms for
	 * transitioning the subject of the diagnostic (e.g. a subsystem, 
	 * component, or other resource) from its "diagnostic" mode back
	 * to its "normal" operating mode.
	 */
	protected void 
	endDiagnosticMode()
	{
	}

}

//##########################################################################
