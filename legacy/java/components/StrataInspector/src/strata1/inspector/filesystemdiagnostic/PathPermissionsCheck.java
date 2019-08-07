// ##########################################################################
// # File Name:	PathPermissionCheck.java
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PathPermissionsCheck 
	extends DiagnosticCheck
{
	private String     		itsPath;
	private Set<Permission> itsPermissions;
	
	/************************************************************************
	 * Creates a new PathPermissionCheck. 
	 *
	 * @param name
	 */
	public 
	PathPermissionsCheck(String name)
	{
		super( name );
		itsPath        = "";
		itsPermissions = new HashSet<Permission>();
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
	 *  
	 *
	 * @param permissions
	 */
	public void 
	setPermissions(Set<Permission> permissions)
	{
		itsPermissions = new HashSet<Permission>(permissions);
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public Set<Permission>
	getPermissions()
	{
		return Collections.unmodifiableSet( itsPermissions );
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
		File          path    = new File( itsPath );
		StringBuilder success = new StringBuilder();
		StringBuilder failure = new StringBuilder();
		boolean       passed  = true;

		if ( !path.exists() )
			throw new DiagnosticException( 
				"Path " + itsPath + " does not exist." );
		
		if ( itsPermissions.contains( Permission.READ ) )
			passed &= isReadable( path,success,failure );
		
		if ( itsPermissions.contains( Permission.WRITE ) )
			passed &= isWritable( path,success,failure );
		
		if ( itsPermissions.contains( Permission.EXECUTE ) )
			passed &= isExecutable( path,success,failure );
		
		if ( passed )
			return success.toString();
		else
			throw new DiagnosticException( failure.toString() );
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

	/************************************************************************
	 *  
	 *
	 * @param path
	 * @param success
	 * @param failure
	 * @return
	 */
	private boolean 
	isReadable(
		File 		  path,
		StringBuilder success,
		StringBuilder failure)
	{
		if ( path.canRead() )
		{
			success
				.append( "Path " )
				.append( itsPath )
				.append( " is readable." );
			return true;
		}
		else
		{
			failure
				.append( "Path " )
				.append( itsPath )
				.append( " is not readable." );
			return false;
		}
	}

	/************************************************************************
	 *  
	 *
	 * @param path
	 * @param success
	 * @param failure
	 * @return
	 */
	private boolean 
	isWritable(
		File 		  path,
		StringBuilder success,
		StringBuilder failure)
	{
		if ( path.canWrite() )
		{
			success
				.append( "Path " )
				.append( itsPath )
				.append( " is writable." );
			return true;
		}
		else
		{
			failure
				.append( "Path " )
				.append( itsPath )
				.append( " is not writable." );
			return false;
		}
	}

	/************************************************************************
	 *  
	 *
	 * @param path
	 * @param success
	 * @param failure
	 * @return
	 */
	private boolean 
	isExecutable(
		File          path,
		StringBuilder success,
		StringBuilder failure)
	{
		if ( path.canExecute() )
		{
			success
				.append( "Path " )
				.append( itsPath )
				.append( " is executable." );
			return true;
		}
		else
		{
			failure
				.append( "Path " )
				.append( itsPath )
				.append( " is not executable." );
			return false;
		}
	}

}


// ##########################################################################
