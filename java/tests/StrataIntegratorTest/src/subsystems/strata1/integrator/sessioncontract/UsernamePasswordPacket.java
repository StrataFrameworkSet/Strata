// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.integrator.sessioncontract;


/**
 * <a href=
 * "http://java.sun.com/blueprints/corej2eepatterns/Patterns/TransferObject.html">
 * Transport Object</a> for username and password.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class UsernamePasswordPacket 
	extends AuthenticationPacket
{
	private String	itsUsername;
	private String	itsPassword;

	/************************************************************************
	 * Creates a new UsernamePasswordPacket. 
	 *
	 * @param username
	 * @param password
	 */
	public 
	UsernamePasswordPacket(String username,String password)
	{
		super();
		itsUsername = username;
		itsPassword = password;
	}

	/************************************************************************
	 * Creates a new UsernamePasswordPacket. 
	 *
	 * @param session
	 * @param username
	 * @param password
	 */
	public 
	UsernamePasswordPacket(
		SessionRep session,
		String 			 username,
		String 			 password)
	{
		super( session );
		itsUsername = username;
		itsPassword = password;
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public String 
	getUsername()
	{
		return itsUsername;
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public String 
	getPassword()
	{
		return itsPassword;
	}

}


// ##########################################################################
