// ##########################################################################
// # File Name:	DatabaseConnectionCheck.java
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

package strata.diagnostic.core.evaluation;

import strata.diagnostic.core.common.DiagnosticException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DatabaseConnectionCheck 
	extends DiagnosticCheck
{
	private String itsDatabaseUrl;
	private String itsUserName;
	private String itsPassword;
	
	/************************************************************************
	 * Creates a new DatabaseConnectionCheck. 
	 *
	 * @param name
	 */
	public 
	DatabaseConnectionCheck(String name)
	{
		super( name );
		itsDatabaseUrl = "";
		itsUserName    = "";
		itsPassword    = "";
	}

	/************************************************************************
	 *  
	 *
	 * @param dburl
	 */
	public void 
	setDatabaseUrl(String dburl)
	{
		itsDatabaseUrl = dburl;
	}
	
	/************************************************************************
	 *  
	 *
	 * @param userName
	 */
	public void
	setUserName(String userName)
	{
		itsUserName = userName;
	}
	
	/************************************************************************
	 *  
	 *
	 * @param password
	 */
	public void
	setPassword(String password)
	{
		itsPassword = password;
	}
	
	public String
	getDatabaseUrl()
	{
		return itsDatabaseUrl;
	}
	
	public String
	getUserName()
	{
		return itsUserName;
	}
	
	public String
	getPassword()
	{
		return itsPassword;
	}
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	protected String 
	runCheck() 
		throws DiagnosticException
	{
		@SuppressWarnings("unused")
		Connection connection = null;
		
		try
		{
			connection = DriverManager.getConnection( 
				itsDatabaseUrl,
				itsUserName,
				itsPassword );
		}
		catch (SQLException e)
		{
			throw new DiagnosticException( e );
		}
		
		return "Connected to " + itsDatabaseUrl + " as user " + itsUserName;
	}

	/************************************************************************
	 * {@inheritDoc} 
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
	 */
	@Override
	protected boolean 
	isRecoverable()
	{
		return false;
	}

}


// ##########################################################################
