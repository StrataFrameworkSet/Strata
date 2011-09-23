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
 * Extends <code>AbstractPacket</code> with mechanisms for managing
 * a parameterized payload. A payload is a the data that gets transported.
 * 
 * @param	<T>	payload type
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@SuppressWarnings("serial")
public abstract
class PayloadPacket<T> 
	extends AbstractPacket
{
	private T    itsPayload;
	
	/************************************************************************
	 * Creates a new PayloadPacket. 
	 *
	 * @param 			sessionId	session identifier
	 * @param 			payload		data that gets transported
	 * @postcondition	this.getPayload() == payload
	 */
	public 
	PayloadPacket(SessionId sessionId,T payload)
	{
		super( sessionId );
		itsPayload = payload;
	}

	/************************************************************************
	 * Creates a new PayloadPacket. 
	 *
	 * @param 			session	source of session and user identifiers
	 * @param 			payload	data that gets transported
	 * @postcondition	this.getPayload() == payload
	 */
	public 
	PayloadPacket(SessionRep session,T payload)
	{
		super( session );
		itsPayload = payload;
	}

	/************************************************************************
	 * The <code>PayloadPacket</code>'s payload. 
	 *
	 * @return	payload object
	 */
	public T
	getPayload()
	{
		return itsPayload;
	}
}


// ##########################################################################
