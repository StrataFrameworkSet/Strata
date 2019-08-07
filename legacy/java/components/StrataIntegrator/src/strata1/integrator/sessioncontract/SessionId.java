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

import java.io.Serializable;

/**
 * Identifies sessions.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SessionId
	implements	Comparable<SessionId>,
				Serializable
{
	private static final long serialVersionUID = 0;
	
	private Long itsSessionId;
	
	/************************************************************************
	 * Creates a new SessionId. 
	 *
	 * @param 			sessionId	numerical identifier
	 * @postcondition	this.getSessionId() == sessionId
	 */
	public 
	SessionId(Long sessionId)
	{
		itsSessionId = sessionId;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int 
	compareTo(SessionId other)
	{
		return itsSessionId.compareTo( other.itsSessionId );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean 
	equals(Object other)
	{
		return 
			(other instanceof SessionId) && 
			itsSessionId.equals( ((SessionId)other).itsSessionId );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int 
	hashCode()
	{
		int hash = 7;
		
		hash = 31 * hash + itsSessionId.hashCode();
		return hash;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String 
	toString()
	{
		return itsSessionId.toString();
	}

	/************************************************************************
	 * Returns the <code>SessionId</code>'s numerical identifier. 
	 *
	 * @return	numerical identifier
	 */
	public Long
	toLong()
	{
		return itsSessionId;
	}
}


// ##########################################################################
