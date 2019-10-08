// ##########################################################################
// # File Name:	InMemoryNamedQuery.java
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

package strata.domain.redis.namedquery;


import strata.domain.core.namedquery.AbstractNamedQuery;
import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.namedquery.NotUniqueException;
import strata.domain.redis.unitofwork.IPredicate;
import strata.domain.redis.unitofwork.RedisUnitOfWorkProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

/****************************************************************************
 *
 * @param <T> entity type
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class RedisNamedQuery<T>
    extends AbstractNamedQuery<T>
	implements INamedQuery<T>
{
	private RedisUnitOfWorkProvider itsProvider;
	private Class<T> itsType;
	private IPredicate<T> itsPredicate;
	private List<T> itsObjects;
	private List<T> itsResults;
	private Iterator<T> itsIterator;

	/************************************************************************
	 * Creates a new InMemoryNamedQuery.
	 * @param type TODO
	 *
	 */
	public RedisNamedQuery(
		RedisUnitOfWorkProvider provider,
		String name,
		Class<T> type,
		IPredicate<T>			   predicate)
	{
		super(type.getCanonicalName() + "." + name);
		itsProvider      = provider;
		itsType          = type;
		itsPredicate	 = predicate;
		itsObjects       = itsProvider.getEntitiesByType( itsType );
		itsResults       = null;
		itsIterator      = null;

		itsProvider.insertNamedQuery(type,this);
	}

	/************************************************************************
	 * Creates a new InMemoryNamedQuery.
	 *
	 */
	public
	RedisNamedQuery(RedisNamedQuery<T> other)
	{
		super(other);
		itsProvider       = other.itsProvider;
		itsType          = other.itsType;
		itsPredicate	 = other.itsPredicate;
		itsObjects       = itsProvider.getEntitiesByType( itsType );
		itsResults       = null;
		itsIterator      = null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public RedisNamedQuery<T>
	copy()
	{
	    return new RedisNamedQuery<T>( this );
	}

	/************************************************************************
     * {@inheritDoc} 
	 * @return 
     */
    @Override
    public INamedQuery<T> 
    clear()
    {
        itsObjects = itsProvider.getEntitiesByType( itsType );
        itsResults = null;
        itsIterator = null;
        return this;
    }

    /************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public CompletionStage<Optional<T>>
	getUnique() 
	{
		return
			CompletableFuture.supplyAsync(
				() ->
				{
					execute();

					if ( itsResults.size() > 1 )
						throw
							new CompletionException(
								new NotUniqueException(
									"result is not unique"));

					if ( itsResults.isEmpty() )
						return Optional.empty();

					return Optional.ofNullable(itsResults.get(0));
				});
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public CompletionStage<Collection<T>>
	getAll()
	{
		return
			CompletableFuture.supplyAsync(
				() ->
				{
					execute();
					return itsResults;
				});
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public CompletionStage<Optional<T>>
	getNext()
	{
		return
			CompletableFuture.supplyAsync(
				() ->
				{
					execute();
					return Optional.ofNullable(itsIterator.next());
				});

	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public CompletionStage<Boolean>
	hasNext()
	{
		return
			CompletableFuture.supplyAsync(
				() ->
				{
					execute();
					return itsIterator.hasNext();
				});

	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
    public CompletionStage<Boolean>
    hasUnique() 
    {
    	return
			CompletableFuture.supplyAsync(
				() ->
				{
					execute();
					return itsResults.size() == 1;
				});
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasAny()
    {
		return
			CompletableFuture.supplyAsync(
				() ->
				{
					execute();
					return !itsResults.isEmpty();
				});
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
		
		itsObjects  = itsProvider.getEntitiesByType( itsType );
		itsResults  = getResults( getInputs().getNamedInputs(),itsObjects );
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
	getResults(Map<String,Object> inputs,List<T> objects)
	{
		List<T> output = new ArrayList<T>();
		
		for (T entry : objects)
			if ( itsPredicate.evaluate( entry,inputs ) )
				output.add( entry );
		
		return output;
	}
}


// ##########################################################################
