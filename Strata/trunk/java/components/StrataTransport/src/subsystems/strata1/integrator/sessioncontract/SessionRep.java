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
 * Provides a data representation of sessions that can be transfered between
 * client and server components.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SessionRep
	extends	SessionProjection
{
	private static final long serialVersionUID = 0;
	private UserRep 	      itsUser;
	
	/************************************************************************
	 * Creates a new SessionSummary. 
	 *
	 * @param 			sessionId	identifies the session
	 * @param 			user		represents the user of the session
	 * @postcondition	this.getUser() == user
	 */
	public 
	SessionRep(SessionId sessionId,UserRep user)
	{
		super( sessionId );
		itsUser = user;
	}
	
	/************************************************************************
	 * Returns the user representation associated with this session. 
	 *
	 * @return user representation
	 */
	public UserRep
	getUser()
	{
		return itsUser;
	}

}


// ##########################################################################
