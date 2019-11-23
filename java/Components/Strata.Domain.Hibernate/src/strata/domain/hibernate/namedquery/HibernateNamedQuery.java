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

package strata.domain.hibernate.namedquery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ShortType;
import strata.domain.core.namedquery.AbstractNamedQuery;
import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.namedquery.InputKeeper;
import strata.domain.core.namedquery.NotUniqueException;
import strata.domain.hibernate.unitofwork.HibernateUnitOfWorkProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

/****************************************************************************
 * 
 * @param <T> entity type
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateNamedQuery<T>
	extends    AbstractNamedQuery<T>
	implements INamedQuery<T>
{
	private Class<T> 					itsEntityClass;
	private HibernateUnitOfWorkProvider itsContext;
	private List<T> 					itsResult;
	private Iterator<T> 				itsCurrent;
	private ExecutorService 			itsExecutor;
	
	/************************************************************************
	 * Creates a new HibernateNamedQuery. 
	 *
	 */
	public 
	HibernateNamedQuery(
		String queryName,
		Class<T> entityClass,
		HibernateUnitOfWorkProvider context)
	{
		super( entityClass.getName() + "." + queryName );
		itsEntityClass = entityClass;
		itsContext     = context;
		itsResult      = null;
		itsCurrent     = null;
		itsExecutor    = context.getExecutor();
	}

	/************************************************************************
	 * Creates a new HibernateNamedQuery. 
	 *
	 * @param other
	 */
	public
	HibernateNamedQuery(HibernateNamedQuery<T> other)
	{
		super( other );
		itsEntityClass = other.itsEntityClass;
		itsContext     = other.itsContext;
		itsResult      = null;
		itsCurrent     = null;
		itsExecutor    = other.itsExecutor;
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public HibernateNamedQuery<T> 
	copy()
	{
		return new HibernateNamedQuery<T>( this );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public HibernateNamedQuery<T> 
	clear()
	{
		clearInputs();
		itsResult  = null;
		itsCurrent = null;
		return this;
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
					evaluateGet(ResultCardinality.ZERO_TO_MANY);
					return itsResult;
				},
				itsExecutor);
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
					evaluateGet(ResultCardinality.ZERO_TO_ONE);

					if ( itsResult.size() > 1 )
						throw
							new CompletionException(
								new NotUniqueException("result not unique"));

					return Optional.ofNullable(itsResult.get(0));

				},
				itsExecutor);
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
					evaluateGet(ResultCardinality.ZERO_TO_MANY);
					return Optional.ofNullable(itsCurrent.next());
				},
				itsExecutor);
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
					long numResults = evaluateHas();

					if ( numResults > 1 )
						throw
							new CompletionException(
								new NotUniqueException("results are not unique"));

					return numResults == 1;
				},
				itsExecutor);
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
				() -> evaluateHas() > 0,
				itsExecutor);
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
					evaluateGet(ResultCardinality.ZERO_TO_MANY);
					return itsCurrent.hasNext();
				},
				itsExecutor);
	}
	
	/************************************************************************
	 *  
	 *
	 * @param cardinality
	 */
	protected void
	evaluateGet(ResultCardinality cardinality)
	{
		if ( itsResult == null )
		{
			SessionFactory factory = itsContext.getSessionFactory();
			Session        session = factory.openSession();
			InputKeeper    keeper  = getInputs();

			try
			{
				switch (keeper.getMode())
				{
					case NAMED:
						itsResult =
							evaluateGetWithNamedInputs(
								session,keeper,cardinality);
						break;

					case POSITIONAL:
						itsResult =
							evaluateGetWithPositionalInputs(
								session,keeper,cardinality);
						break;

					default:
						itsResult =
							evaluateGetWithNoInputs(session,cardinality);
						break;
				}

				itsCurrent = itsResult.iterator();
			}
			finally
			{
				if (session != null & session.isOpen())
					session.close();
			}
		}
	}

	/************************************************************************
     *  
     *
     * @param session
     * @param keeper
     * @param cardinality
     * @return
     */
    private List<T>
    evaluateGetWithNamedInputs(
    	Session           session,
    	InputKeeper       keeper,
    	ResultCardinality cardinality)
    {
        Query query  = session.getNamedQuery( getName() );
    	Map<String,Object> inputs = keeper.getNamedInputs();
    	
    	for (Map.Entry<String,Object> input : inputs.entrySet())
    		if (input.getValue() instanceof Collection<?>)
    			query.setParameterList(
    				input.getKey(),
					(Collection<?>)input.getValue());
    		else
    			query.setParameter( input.getKey(),input.getValue() );
    	
    	return 
            cardinality == ResultCardinality.ZERO_TO_MANY
                ? convertUntypedListToTypedList(query.list())
                : convertObjectToTypedList(query.uniqueResult());
    }

    /************************************************************************
	 *  
	 *
	 * @param session
	 * @param keeper
	 * @param cardinality
	 * @return
	 */
	private List<T>
	evaluateGetWithPositionalInputs(
		Session           session,
		InputKeeper       keeper, 
		ResultCardinality cardinality)
	{
	    Query query    = session.getNamedQuery( getName() );
		Collection<Object> inputs   = keeper.getPositionalInputs();
		int                position = 0;
		
		for (Object input : inputs)
			if (input instanceof Collection<?>)
				query.setParameterList(position++,(Collection<?>)input);
			else
		    	query.setParameter( position++,input );
		
		return 
		    cardinality == ResultCardinality.ZERO_TO_MANY
                ? convertUntypedListToTypedList(query.list())
                : convertObjectToTypedList(query.uniqueResult());	        
	}

	/************************************************************************
	 *  
	 *
	 * @param session
	 * @param cardinality
	 * @return
	 */
	private List<T>
	evaluateGetWithNoInputs(
	    Session session,
	    ResultCardinality cardinality)
	{
	    Query query = session.getNamedQuery( getName() );
	    
		return
            cardinality == ResultCardinality.ZERO_TO_MANY
                ? convertUntypedListToTypedList(query.list())
                : convertObjectToTypedList(query.uniqueResult());
	}

    /************************************************************************
     *  
     *
     * @return
     */
    protected long
    evaluateHas()
    {
        SessionFactory factory = itsContext.getSessionFactory();
        Session 	   session = factory.openSession();
        InputKeeper    keeper  = getInputs();

        try
		{
			switch (keeper.getMode())
			{
				case NAMED:
					return
						evaluateHasWithNamedInputs(session,keeper);

				case POSITIONAL:
					return
						evaluateHasWithPositionalInputs(session,keeper);

				default:
					return
						evaluateHasWithNoInputs(session);
			}
		}
        finally
		{
			if (session != null && session.isOpen())
				session.close();
		}
    }

    /************************************************************************
     *  
     *
     * @param session
     * @param keeper
     * @return
     */
    private long 
    evaluateHasWithNamedInputs(Session session,InputKeeper keeper)
    {
        Query query  = session.getNamedQuery( getName() );
        Map<String,Object> inputs = keeper.getNamedInputs();
        
        query.setMaxResults( 2 );
        query.setFetchSize( 2 );
        
        for (Map.Entry<String,Object> input : inputs.entrySet())
        	if (input.getValue() instanceof Collection<?>)
        		query.setParameterList(
        			input.getKey(),
					(Collection<?>)input.getValue());
        	else
            	query.setParameter( input.getKey(),input.getValue() );
                
        return
            hasIntegerReturnType(query)
                ? (long)query.uniqueResult() 
                : query.list().size();           
    }

    /************************************************************************
     *  
     *
     * @param session
     * @param keeper
     * @return
     */
    private long 
    evaluateHasWithPositionalInputs(
        Session session,
        InputKeeper       keeper)
    {
        Query query    = session.getNamedQuery( getName() );
        Collection<Object> inputs   = keeper.getPositionalInputs();
        int                position = 0;
        
        query.setMaxResults( 2 );
        query.setFetchSize( 2 );
        
        for (Object input : inputs)
        	if (input instanceof Collection<?>)
        		query.setParameterList(position++,(Collection<?>)input);
        	else
            	query.setParameter( position++,input );
        
        return
            hasIntegerReturnType(query)
                ? (long)query.uniqueResult() 
                : query.list().size();           
    }

    /************************************************************************
     *  
     *
     * @param session
     * @return
     */
    private long 
    evaluateHasWithNoInputs(Session session)
    {
        Query query = session.getNamedQuery( getName() );
        
        query.setMaxResults( 2 );
        query.setFetchSize( 2 );
        
        return
            hasIntegerReturnType(query)
                ? (long)query.uniqueResult() 
                : query.list().size();           
    }

    /************************************************************************
     *  
     *
     * @param untyped
     * @return
     */
    private List<T>
    convertUntypedListToTypedList(List<?> untyped)
    {
        List<T> typed = new ArrayList<T>();
        
        for (Object entity : untyped)
            typed.add( itsEntityClass.cast( entity ) );
        
        return typed;
    }

	/************************************************************************
	 *  
	 *
	 * @param untyped
	 * @return
	 */
	private List<T>
	convertObjectToTypedList(Object untyped)
	{
		List<T> typed = new ArrayList<T>();
		
		typed.add( itsEntityClass.cast( untyped ) );
		return typed;
	}

	/************************************************************************
	 *  
	 *
	 * @param query
	 * @return
	 */
	private boolean
	hasIntegerReturnType(Query query)
	{
	    return
	        query.getReturnTypes().length == 1 && (
	        query.getReturnTypes()[0].getClass().equals(LongType.class) ||
	        query.getReturnTypes()[0].getClass().equals(IntegerType.class) ||
	        query.getReturnTypes()[0].getClass().equals(ShortType.class) );
	}
}


// ##########################################################################
