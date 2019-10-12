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
import strata.domain.redis.unitofwork.IMembershipCheck;
import strata.domain.redis.unitofwork.IReadOnlyUnitOfWork;
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
class RedisGetListNamedQuery<K,T>
    extends AbstractNamedQuery<T>
	implements IRedisNamedQuery<T>
{
	private RedisUnitOfWorkProvider itsProvider;
	private Class<K> 				itsKeyType;
	private Class<T> 				itsType;
	private String 					itsIdsParameterName;
	private IMembershipCheck<K,T>   itsMembershipCheck;
	private List<T>   		        itsResults;
	private Iterator<T> 			itsIterator;

	/************************************************************************
	 * Creates a new InMemoryNamedQuery.
	 * @param type TODO
	 *
	 */
	public
	RedisGetListNamedQuery(
		RedisUnitOfWorkProvider provider,
		String 					name,
		Class<K>				keyType,
		Class<T> 				type,
		String                  idsParameterName,
		IMembershipCheck<K,T>   membershipCheck)
	{
		super(type.getCanonicalName() + "." + name);
		itsProvider      = provider;
		itsKeyType 		 = keyType;
		itsType          = type;
		itsIdsParameterName = idsParameterName;
		itsMembershipCheck = membershipCheck;
		itsResults       = null;
		itsIterator      = null;

		itsProvider.insertNamedQuery(type,this);
	}

	/************************************************************************
	 * Creates a new InMemoryNamedQuery.
	 *
	 */
	public
	RedisGetListNamedQuery(RedisGetListNamedQuery<K,T> other)
	{
		super(other);
		itsProvider       = other.itsProvider;
		itsType          = other.itsType;
		itsIdsParameterName = other.itsIdsParameterName;
		itsResults       = null;
		itsIterator      = null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public RedisGetListNamedQuery<K,T>
	copy()
	{
	    return new RedisGetListNamedQuery<K,T>( this );
	}

	/************************************************************************
     * {@inheritDoc} 
	 * @return 
     */
    @Override
    public INamedQuery<T> 
    clear()
    {
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

		itsResults  = getResults( getInputs().getNamedInputs() );
		itsIterator = itsResults.iterator();
	}
	
	/************************************************************************
	 *  
	 *
	 * @param inputs
	 * @return
	 */
	protected List<T>
	getResults(Map<String,Object> inputs)
	{
		Set<K> keys = null;
		Collection<T> output = null;

		if (!inputs.containsKey(itsIdsParameterName))
			throw
				new IllegalArgumentException(
					"query input does not contain '" +
						itsIdsParameterName + "'");

		keys = (Set<K>)inputs.get(itsIdsParameterName);
		output = getUnitOfWork().getEntitiesIn(itsType,keys,itsMembershipCheck);

		try
		{
			return (List<T>)output;
		}
		catch (ClassCastException e)
		{
			return new ArrayList<>(output);
		}
	}

	protected IReadOnlyUnitOfWork
	getUnitOfWork() { return itsProvider.getReadOnlyUnitOfWork(); }
}


// ##########################################################################
