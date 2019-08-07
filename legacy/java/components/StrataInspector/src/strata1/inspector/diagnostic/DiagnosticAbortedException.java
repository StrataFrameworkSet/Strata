// ##########################################################################
// # File Name:	DiagnosticAbortedException.java
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
 * Indicates that a diagnostic check could not be performed.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DiagnosticAbortedException 
	extends Exception
{
	private static final long serialVersionUID = 0;
	
	/************************************************************************
	 * Creates a new DiagnosticAbortedException. 
	 *
	 */
	public 
	DiagnosticAbortedException()
	{
		super();
	}

	/************************************************************************
	 * Creates a new DiagnosticAbortedException. 
	 *
	 * @param msg
	 */
	public 
	DiagnosticAbortedException(String msg)
	{
		super( msg );
	}

	/************************************************************************
	 * Creates a new DiagnosticAbortedException. 
	 *
	 * @param msg
	 * @param e
	 */
	public 
	DiagnosticAbortedException(String msg,Throwable e)
	{
		super( msg,e );
	}

	/************************************************************************
	 * Creates a new DiagnosticAbortedException. 
	 *
	 * @param e
	 */
	public 
	DiagnosticAbortedException(Throwable e)
	{
		super( e );
	}

}
