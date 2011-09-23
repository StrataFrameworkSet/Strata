// ##########################################################################
// # File Name:	ProcessExistsCheck.java
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

package strata1.inspector.processdiagnostic;

import strata1.inspector.diagnostic.DiagnosticCheck;
import strata1.inspector.diagnostic.DiagnosticException;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ProcessExistsCheck 
	extends DiagnosticCheck
{
	
	/************************************************************************
	 * Creates a new ProcessExistsCheck. 
	 *
	 * @param name
	 */
	public 
	ProcessExistsCheck(String name)
	{
		super( name );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticCheck#runCheck()
	 */
	@Override
	protected String 
	runCheck() 
		throws DiagnosticException
	{
		return null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see strata1.inspector.diagnostic.DiagnosticCheck#runRecovery()
	 */
	@Override
	protected String 
	runRecovery() 
		throws DiagnosticException
	{
		return null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see strata1.inspector.diagnostic.DiagnosticCheck#isRecoverable()
	 */
	@Override
	protected boolean 
	isRecoverable()
	{
		return false;
	}

}


// ##########################################################################
