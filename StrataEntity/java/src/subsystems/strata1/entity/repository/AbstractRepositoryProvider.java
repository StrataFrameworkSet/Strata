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

import java.util.*;
import java.io.*;

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
	implements RepositoryProvider<K,T>
{
	private Map<String,Finder<T>> itsFinders;
	
	/************************************************************************
	 * Creates a new AbstractRepositoryImp. 
	 *
	 */
	public 
	AbstractRepositoryProvider()
	{
		super();
		itsFinders = new HashMap<String,Finder<T>>();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#insertNew(Object)
	 */
	@Override
	public void 
	insertNew(T object)
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
			doInsert( object );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#updateExisting(Object)
	 */
	@Override
	public void 
	updateExisting(T object)
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
			doUpdate( object );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#removeExisting(Object)
	 */
	@Override
	public void 
	removeExisting(T object)
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
			doRemove( object );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#insertFinder(Finder)
	 */
	@Override
	public void 
	insertFinder(Finder<T> finder)
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
			itsFinders.put( finder.getName(),finder );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#removeFinder(String)
	 */
	@Override
	public void 
	removeFinder(String finderName)
	{
		getContext().getSynchronizer().lockForWriting();
		
		try
		{
			if ( itsFinders.containsKey( finderName ) )
				itsFinders.remove( finderName );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#getExisting(Object)
	 */
	@Override
	public T 
	getExisting(K key)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return doGet( key );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#getFinder(String)
	 */
	@Override
	public Finder<T> 
	getFinder(String finderName)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return itsFinders.get( finderName );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#hasExisting(Object)
	 */
	@Override
	public boolean 
	hasExisting(K key)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return doHas( key );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryProvider#hasFinder(String)
	 */
	@Override
	public boolean 
	hasFinder(String finderName)
	{
		getContext().getSynchronizer().lockForReading();
		
		try
		{
			return itsFinders.containsKey( finderName );
		}
		finally
		{
			getContext().getSynchronizer().unlockFromReading();
		}
	}

	protected abstract void
	doInsert(T entity);

	protected abstract void
	doUpdate(T entity);

	protected abstract void
	doRemove(T entity);

	protected abstract T
	doGet(K key);
	
	protected abstract boolean
	doHas(K key);
}


// ##########################################################################
