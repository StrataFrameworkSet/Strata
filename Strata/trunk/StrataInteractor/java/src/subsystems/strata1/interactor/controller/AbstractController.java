// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.interactor.controller;

import strata1.interactor.model.UpdateProcessor;
import strata1.interactor.view.ActionProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractController<A,U> 
	implements 	ActionProcessor<A>,
				UpdateProcessor<U>
{
	private Map<A,Handler<A>> itsActions;
	private Map<U,Handler<U>> itsUpdates;
	
	public
	AbstractController()
	{
		super();
		itsActions = new HashMap<A,Handler<A>>();
		itsUpdates = new HashMap<U,Handler<U>>();
	}
	/************************************************************************
	 * {@inheritDoc} 
	 * @see ActionProcessor#processAction(Object)
	 */
	@Override
	public void 
	processAction(A action)
	{
		itsActions.get( action ).handle( action );
	}
	/************************************************************************
	 * {@inheritDoc} 
	 * @see UpdateProcessor#processUpdateObject)
	 */
	@Override
	public void 
	processUpdate(U update)
	{
		itsUpdates.get( update ).handle( update );
	}

	/************************************************************************
	 *  
	 *
	 * @param action
	 * @param handler
	 */
	protected void
	setAction(A action,Handler<A> handler)
	{
		itsActions.put( action,handler );
	}
	
	/************************************************************************
	 *  
	 *
	 * @param update
	 * @param handler
	 */
	protected void
	setUpdate(U update,Handler<U> handler)
	{
		itsUpdates.put( update,handler );
	}
}


// ##########################################################################
