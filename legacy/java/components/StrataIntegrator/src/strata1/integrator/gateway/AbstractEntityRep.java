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

package strata1.integrator.gateway;

import java.io.Serializable;

/**
 * @param <Id> id type
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@SuppressWarnings("serial")
public abstract 
class AbstractEntityRep<Id extends Comparable<Id>> 
	implements 	Comparable<AbstractEntityRep<Id>>,
				Serializable
{
	private Id itsId;
	
	/************************************************************************
	 * Creates a new EntityProjection. 
	 *
	 */
	public 
	AbstractEntityRep(Id id)
	{
		super();
		itsId = id;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Comparable#compareTo(EntityProjection<Id> other)
	 */
	@Override
	public int 
	compareTo(AbstractEntityRep<Id> other)
	{
		return itsId.compareTo( other.itsId );
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public Id
	getId()
	{
		return itsId;
	}
}


// ##########################################################################
