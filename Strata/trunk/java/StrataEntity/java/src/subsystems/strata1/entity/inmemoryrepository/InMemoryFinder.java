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

import strata1.common.utility.Copyable;
import strata1.entity.repository.Finder;
import strata1.entity.repository.InvalidInputException;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class InMemoryFinder<K,T> 
	implements Finder<T>
{
	protected InMemoryRepositoryContext itsContext;
	protected Predicate<T>				itsPredicate;
	protected Map<K,T>				    itsObjects;
	protected Map<String,Object>		itsNamedInputs;
	protected List<T>				    itsResults;
	protected Iterator<T>				itsIterator;
	
	/************************************************************************
	 * Creates a new InMemoryFinder. 
	 *
	 */
	public 
	InMemoryFinder(
		InMemoryRepositoryContext context,
		Predicate<T>			  predicate,
		Map<K,T>                  objects)
	{
		super();
		itsContext       = context;
		itsPredicate	 = predicate;
		itsObjects       = objects;
		itsNamedInputs 	 = new HashMap<String,Object>();
		itsResults       = null;
		itsIterator      = null;
	}

	/************************************************************************
	 * Creates a new InMemoryFinder. 
	 *
	 */
	public 
	InMemoryFinder(InMemoryFinder<K,T> other)
	{
		super();
		itsContext       = other.itsContext;
		itsPredicate	 = other.itsPredicate;
		itsObjects       = other.itsObjects;
		itsNamedInputs   = new HashMap<String,Object>();
		itsResults       = null;
		itsIterator      = null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Copyable#copy()
	 */
	@Override
	public abstract InMemoryFinder<K,T> 
	copy();

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#setInput(String,Object)
	 */
	@Override
	public void 
	setInput(String name,Object input) throws InvalidInputException
	{
		itsNamedInputs.put( name,input );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#setInput(int,Object)
	 */
	@Override
	public void 
	setInput(int index,Object input) 
		throws InvalidInputException
	{
		throw new InvalidInputException( "Not supported" );
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#setInput(Collection)
	 */
	@Override
	public void 
	setInput(Collection<Object> inputs)
			throws InvalidInputException
	{
		throw new InvalidInputException( "Not supported" );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#getUnique()
	 */
	@Override
	public T 
	getUnique()
	{
		execute();
		return itsResults.get( 0 );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#getAll()
	 */
	@Override
	public Collection<T> 
	getAll()
	{
		execute();
		return itsResults;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#getNext()
	 */
	@Override
	public T 
	getNext()
	{
		execute();
		return itsIterator.next();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see Finder#hasNext()
	 */
	@Override
	public boolean 
	hasNext()
	{
		execute();
		return itsIterator.hasNext();
	}

	/************************************************************************
	 *  
	 *
	 */
	protected void
	execute()
	{
		if ( itsResults != null )
			return;
		
		itsResults  = getResults( itsNamedInputs,itsObjects );
		itsIterator = itsResults.iterator();
	}
	
	/************************************************************************
	 *  
	 *
	 * @param inputs
	 * @param objects
	 * @return
	 */
	protected List<T> 
	getResults(Map<String,Object> inputs,Map<K,T> objects)
	{
		List<T> output = new ArrayList<T>();
		
		for (T entry : objects.values())
		{
			if ( itsPredicate.evaluate( entry,inputs ) )
				output.add( entry );
		}
		
		return output;
	}
}


// ##########################################################################
