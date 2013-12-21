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

package strata1.integrator.useraccountcontract;

import java.util.*;

import strata1.integrator.sessioncontract.*;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class UserAccountRepListPacket 
	extends PayloadPacket<Collection<UserAccountRep>>
{
	private static final long serialVersionUID = 0;
	
	/************************************************************************
	 * Creates a new UserAccountEnvoyListPacket. 
	 *
	 * @param session
	 * @param payload
	 */
	public 
	UserAccountRepListPacket(
		SessionRep                 session,
		Collection<UserAccountRep> payload)
	{
		super( session,payload );
	}

}


// ##########################################################################
