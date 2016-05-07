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
 * Base class of all authentication related packets. Subclasses may
 * encapsulate authentication information such as:
 * <ul>
 *     <li>username and password</li>
 *     <li>certificates</li>
 *     <li>SAML Tokens</li>
 *     <li>Kerberos tickets</li>
 *     <li>etc</li>
 * </ul>
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AuthenticationPacket
{
	private SessionId itsExistingSessionId;
	
	/************************************************************************
	 * Creates a new AuthenticationPacket. 
	 * 
	 * @postcondition	this.hasExistingSessionId() == false
	 */
	public 
	AuthenticationPacket()
	{
		super();
		itsExistingSessionId = null;
	}

	/************************************************************************
	 * Creates a new AuthenticationPacket. 
	 *
	 * @param session	source of existing session identifer
	 * @postcondition	this.hasExistingSessionId() == true
	 */
	public 
	AuthenticationPacket(SessionRep session)
	{
		super();
		itsExistingSessionId = session.getId();
	}

	/************************************************************************
	 * @return	null of there is no existing session otherwise,
	 * 			identifer for the existing session
	 */
	public SessionId
	getExistingSessionId()
	{
		return itsExistingSessionId;
	}
	
	/************************************************************************
	 * @return	True if this <code>AuthenticationPacket</code> has been
	 * 			constructed with an existing session identifier. 
	 * 			This represents an authentication request to convert from
	 * 			an existing session (usually an anonymous session) to a 
	 * 			new session.
	 * 			<p>
	 * 			False if there is no existing session.
	 */
	public boolean
	hasExistingSesssionId()
	{
		return itsExistingSessionId != null;
	}
}


// ##########################################################################
