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

import java.io.*;

/**
 * Base class of all packet objects: objects that are parameters to
 * <a href="http://martinfowler.com/eaaCatalog/gateway.html">Gateway</a>
 * methods and identifies the sender through session and user identifiers.
 * Subclasses of <code>AbstractPacket</code> are also <a href=
 * "http://java.sun.com/blueprints/corej2eepatterns/Patterns/TransferObject.html">
 * Transfer Object</a>s and <a href=
 * "http://www.refactoring.com/catalog/introduceParameterObject.html">
 * Parameter Object</a>s.
 *  
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@SuppressWarnings("serial")
public abstract
class AbstractPacket
	implements Serializable
{
	private SessionId itsSessionId;

	/************************************************************************
	 * Creates a new AbstractPacket. 
	 *
	 * @param sessionId	session identifier
	 */
	public 
	AbstractPacket(SessionId sessionId)
	{
		super();
		itsSessionId = sessionId;
	}

	/************************************************************************
	 * Creates a new AbstractPacket. 
	 *
	 * @param session	source of session identifier
	 */
	public 
	AbstractPacket(SessionRep session)
	{
		super();
		itsSessionId = session.getId();
	}

	/************************************************************************
	 * @return	The session identifier associated with 
	 * 			this <code>AbstractPacket</code>.
	 */
	public SessionId 
	getSessionId()
	{
		return itsSessionId;
	}

}

// ##########################################################################
