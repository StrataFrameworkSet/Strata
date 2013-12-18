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

import java.util.*;

/**
 * Provides a data representation of users that can be transfered between
 * client and server components.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class UserRep
	extends UserProjection
{
	private static final long serialVersionUID = 0;
	
	private Set<String> itsRoles;
	
	/************************************************************************
	 * Creates a new UserAccountEnvoy. 
	 *
	 */
	public 
	UserRep(UserId userId,Set<String> roles)
	{
		super( userId );
		itsRoles = roles;
	}

	/************************************************************************
	 * Returns the authorized roles of the user. 
	 *
	 * @return	authorized roles
	 */
	public Set<String>
	getRoles()
	{
		return itsRoles;
	}
}


// ##########################################################################
