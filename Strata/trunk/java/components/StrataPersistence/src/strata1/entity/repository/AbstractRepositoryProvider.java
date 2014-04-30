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

package strata1.entity.repository;

import java.io.Serializable;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 * @param <K>
 * @param <T>
 */
public abstract 
class AbstractRepositoryProvider<K extends Serializable,T>
	implements IRepositoryProvider<K,T>
{
	
	/************************************************************************
	 * Creates a new {@code AbstractRepositoryProvider}. 
	 *
	 */
	public 
	AbstractRepositoryProvider()
	{
		super();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public T 
	insertNew(T entity)
	    throws InsertFailedException
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
		    return doInsertNew(entity);
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public T 
	updateExisting(T object)
	    throws UpdateFailedException
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
			return doUpdateExisting( object );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	removeExisting(T object)
	    throws RemoveFailedException
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
			doRemoveExisting( object );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public T 
	getExisting(K key)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return doGetExisting( key );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public IFinder<T> 
	getFinder(String finderName)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return doGetFinder(finderName);
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public boolean 
	hasExisting(K key)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return doHasExisting( key );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public boolean 
	hasFinder(String finderName)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return doHasFinder( finderName );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 *  
	 *
	 * @param entity
	 * @return
	 * @throws InsertFailedException
	 */
	protected abstract T
	doInsertNew(T entity)
	    throws InsertFailedException;

	/************************************************************************
	 *  
	 *
	 * @param entity
	 * @return
	 * @throws UpdateFailedException
	 */
	protected abstract T
	doUpdateExisting(T entity)
	    throws UpdateFailedException;

	/************************************************************************
	 *  
	 *
	 * @param entity
	 * @throws RemoveFailedException
	 */
	protected abstract void
	doRemoveExisting(T entity)
	    throws RemoveFailedException;

	/************************************************************************
	 *  
	 *
	 * @param key
	 * @return
	 */
	protected abstract T
	doGetExisting(K key);
	
	/************************************************************************
	 *  
	 *
	 * @param finderName
	 * @return
	 */
	protected abstract IFinder<T>
	doGetFinder(String finderName);
	
	/************************************************************************
	 *  
	 *
	 * @param key
	 * @return
	 */
	protected abstract boolean
	doHasExisting(K key);
	
	/************************************************************************
	 *  
	 *
	 * @param finderName
	 * @return
	 */
	protected abstract boolean
	doHasFinder(String finderName);
	
}


// ##########################################################################
