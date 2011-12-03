// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.entity.inmemoryrepository;

import java.util.*;

/**
 * Groups together the changes made through an InMemoryRepository
 * during an InMemoryTransaction. Changes refer to domain object insertion,
 * update, and removal.
 * 
 * @author 		
 *     Java Persistence Strategy Workgroup 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ChangeSetImp<K,T>
	implements ChangeSet<K,T>

{
	private Map<K,T>	itsInserted;
	private Map<K,T>	itsRemoved;
	private Map<K,T>	itsUpdated;

	/************************************************************************
	 * Creates a new ChangeSetImp. 
	 *
	 */
	public
	ChangeSetImp()
	{
		super();
		itsInserted = new HashMap<K,T>();
		itsUpdated  = new HashMap<K,T>();
		itsRemoved  = new HashMap<K,T>();
	}
	
	/************************************************************************
	 * Adds a new insertion change to the ChangeSetImp. 
	 *
	 * @param inserted	domain object being inserted
	 */
	@Override
	public void 
	addToInserted(K key,T inserted)
	{
		itsInserted.put( key,inserted );
	}

	/************************************************************************
	 * Adds a new removal change to the ChangeSetImp. 
	 *
	 * @param removed	domain object being removed
	 */
	@Override
	public void 
	addToRemoved(K key,T removed)
	{
		itsRemoved.put( key,removed );
		
	}

	/************************************************************************
	 * Adds a new update change to the ChangeSetImp. 
	 *
	 * @param updated	domain object being updated
	 */
	@Override
	public void 
	addToUpdated(K key,T updated)
	{
		itsUpdated.put( key,updated );		
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see ChangeSet#applyChanges(Map)
	 */
	@Override
	public void 
	applyChanges(Map<K,T> storage)
	{
		applyInserts( storage );
		applyUpdates( storage );
		applyRemoves( storage );
		itsInserted.clear();
		itsUpdated.clear();
		itsRemoved.clear();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see ChangeSet#discardChanges()
	 */
	@Override
	public void 
	discardChanges()
	{
		itsInserted.clear();
		itsUpdated.clear();
		itsRemoved.clear();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see ChangeSet#hasChanges()
	 */
	@Override
	public boolean 
	hasChanges()
	{
		return 
			!(itsInserted.isEmpty()||itsUpdated.isEmpty()||itsRemoved.isEmpty());
	}

	/************************************************************************
	 *  
	 *
	 * @param storage
	 */
	private void
	applyInserts(Map<K,T> storage)
	{
		storage.putAll( itsInserted );
	}

	/************************************************************************
	 *  
	 *
	 * @param storage
	 */
	private void
	applyUpdates(Map<K,T> storage)
	{
		for (Map.Entry<K,T> entry : itsUpdated.entrySet())
		{
			if ( storage.containsKey( entry.getKey() ) )
				storage.put( entry.getKey(),entry.getValue() );
		}
	}

	/************************************************************************
	 *  
	 *
	 * @param storage
	 */
	private void
	applyRemoves(Map<K,T> storage)
	{
		for (K key : itsRemoved.keySet())
		{
			if ( storage.containsKey( key ) )
				storage.remove( key );
		}
	}
}


// ##########################################################################
