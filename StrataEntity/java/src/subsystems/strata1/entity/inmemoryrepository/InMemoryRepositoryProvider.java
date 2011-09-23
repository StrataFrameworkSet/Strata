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

import java.io.*;
import java.util.*;

import strata1.entity.repository.*;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryRepositoryProvider<K extends Serializable,T> 
	extends 	AbstractRepositoryProvider<K,T>
	implements 	TransactionParticipant
{
	private InMemoryRepositoryContext itsContext;
	private Map<K,T>                  itsEntities;
	private ChangeSet<K,T>            itsChanges;
	private KeyRetriever<K,T>		  itsKeyRetriever;
	
	/************************************************************************
	 * Creates a new InMemoryRepositoryImp. 
	 *
	 */
	public 
	InMemoryRepositoryProvider(
		InMemoryRepositoryContext context,
		KeyRetriever<K,T>         keyRetriever)
	{
		super();
		itsContext      = context;
		itsEntities     = new HashMap<K,T>();
		itsChanges      = new ChangeSetImp<K,T>();
		itsKeyRetriever = keyRetriever;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryImp#getContext()
	 */
	@Override
	public InMemoryRepositoryContext 
	getContext()
	{
		return itsContext;
	}

	/************************************************************************
	 * Apply the change set to the repository's internal storage. 
	 * 
	 */
	public void 
	applyChanges()
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{			
			if ( !itsChanges.hasChanges() )
				return;
			
			itsChanges.applyChanges( itsEntities );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * Discard the change set. 
	 *
	 */
	public void 
	discardChanges()
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{			
			if ( !itsChanges.hasChanges() )
				return;
			
			itsChanges.discardChanges();
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doInsert(Object)
	 */
	@Override
	protected void 
	doInsert(T entity)
	{
		K key = itsKeyRetriever.getKey( entity );
		
		if ( doHas( key ) )
			return;
		
		if ( getContext().hasActiveTransaction() )
		{
			joinTransaction();
			itsChanges.addToInserted( key,entity );
		}
		else
			itsEntities.put( key,entity );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doUpdate(Object)
	 */
	@Override
	protected void 
	doUpdate(T entity)
	{
		K key = itsKeyRetriever.getKey( entity );
		
		if ( getContext().hasActiveTransaction() )
		{
			joinTransaction();
			itsChanges.addToUpdated( key,entity );
		}
		else
			itsEntities.put( key,entity );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doRemove(Object)
	 */
	@Override
	protected void 
	doRemove(T entity)
	{
		K key = itsKeyRetriever.getKey( entity );
		
		if ( !doHas( key ) )
			return;
		
		if ( getContext().hasActiveTransaction() )
		{
			joinTransaction();
			itsChanges.addToRemoved( key,entity );
		}
		else
			itsEntities.remove( key );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doGet(Serializable)
	 */
	@Override
	protected T 
	doGet(K key)
	{
		return itsEntities.get( key );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doHas(Serializable)
	 */
	@Override
	protected boolean 
	doHas(K key)
	{
		return itsEntities.containsKey( key );
	}
	
	/************************************************************************
	 *  
	 *
	 */
	private void
	joinTransaction()
	{
		InMemoryTransaction t = 
			getContext().getTransaction();
		
		t.joinTransaction( this );
	}

}


// ##########################################################################
