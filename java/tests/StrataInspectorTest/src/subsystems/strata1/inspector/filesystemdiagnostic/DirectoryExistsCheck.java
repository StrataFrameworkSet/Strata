// ##########################################################################
// # File Name:	FileExistsCheck.java
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

package strata1.inspector.filesystemdiagnostic;

import strata1.inspector.diagnostic.DiagnosticCheck;
import strata1.inspector.diagnostic.DiagnosticException;

import java.io.File;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DirectoryExistsCheck 
	extends DiagnosticCheck
{
	private String itsPath;
	
	/************************************************************************
	 * Creates a new FileExistsCheck. 
	 *
	 * @param name
	 */
	public 
	DirectoryExistsCheck(String name)
	{
		super( name );
		itsPath = "";
	}

	/************************************************************************
	 *  
	 *
	 * @param path
	 */
	public void
	setPath(String path)
	{
		itsPath = path;
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public String
	getPath()
	{
		return itsPath;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see strata1.inspector.diagnostic.DiagnosticCheck#runCheck()
	 */
	@Override
	protected String 
	runCheck() 
		throws DiagnosticException
	{
		File file = new File( itsPath );

		if ( !file.exists() )
			throw new DiagnosticException( 
				"Directory " + itsPath + " does not exist." );
		
		if ( !file.isDirectory() )
			throw new DiagnosticException( 
				"Path " + itsPath + " does not specify a directory." );
		
		return "Directory " + itsPath + " exists.";
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticCheck#runRecovery()
	 */
	@Override
	protected String 
	runRecovery() 
		throws DiagnosticException
	{
		throw 
			new DiagnosticException(
				"This diagnostic check is not recoverable." );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticCheck#isRecoverable()
	 */
	@Override
	protected boolean 
	isRecoverable()
	{
		return false;
	}
}


// ##########################################################################
