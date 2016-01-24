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

package strata1.persistence.hibernaterepository;

import strata1.persistence.repository.AbstractFinder;
import strata1.persistence.repository.IFinder;
import strata1.persistence.repository.InputKeeper;
import strata1.persistence.repository.NotUniqueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ShortType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/****************************************************************************
 * 
 * @param <T> entity type
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateFinder<T>
	extends 	AbstractFinder<T>
	implements	IFinder<T>
{
	private Class<T>                   itsEntityClass;
	private HibernateRepositoryContext itsContext;
	private List<T>	                   itsResult;
	private Iterator<T>				   itsCurrent;
	
	/************************************************************************
	 * Creates a new HibernateFinder. 
	 *
	 */
	public 
	HibernateFinder(
		String 					   queryName,
		Class<T> 				   entityClass,
		HibernateRepositoryContext context)
	{
		super( queryName );
		itsEntityClass = entityClass;
		itsContext     = context;
		itsResult      = null;
		itsCurrent     = null;
	}

	/************************************************************************
	 * Creates a new HibernateFinder. 
	 *
	 * @param other
	 */
	public
	HibernateFinder(HibernateFinder<T> other)
	{
		super( other );
		itsEntityClass = other.itsEntityClass;
		itsContext     = other.itsContext;
		itsResult      = null;
		itsCurrent     = null;
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public HibernateFinder<T> 
	copy()
	{
		return new HibernateFinder<T>( this );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	clear()
	{
		clearInputs();
		itsResult  = null;
		itsCurrent = null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public Collection<T> 
	getAll()
	{
		evaluateGet(ResultCardinality.ZERO_TO_MANY);
		return itsResult;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public T 
	getUnique() 
	    throws NotUniqueException
	{
		evaluateGet(ResultCardinality.ZERO_TO_ONE);
		
		if ( itsResult.size() > 1 )
		    throw new NotUniqueException("result not unique");
		
		return itsResult.get( 0 );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public T 
	getNext()
	{
		evaluateGet(ResultCardinality.ZERO_TO_MANY);
		return itsCurrent.next();
	}

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasUnique() 
        throws NotUniqueException
    {
        long numResults = evaluateHas();
        
        if ( numResults > 1 )
            throw new NotUniqueException("results are not unique");
        
        return numResults == 1;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasAny()
    {
        return evaluateHas() > 0;
    }

    /************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public boolean 
	hasNext()
	{
		evaluateGet(ResultCardinality.ZERO_TO_MANY);
		return itsCurrent.hasNext();
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
			
			switch ( keeper.getMode() )
			{
			case NAMED:
				itsResult = 
				    evaluateGetWithNamedInputs(
				        session,keeper,cardinality );
				break;
				
			case POSITIONAL:
				itsResult = 
				    evaluateGetWithPositionalInputs(
				        session,keeper,cardinality );
				break;
				
			default:
				itsResult = 
				    evaluateGetWithNoInputs( session,cardinality );
				break;
			}
			
			itsCurrent = itsResult.iterator();
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
        Query              query  = session.getNamedQuery( getName() );
    	Map<String,Object> inputs = keeper.getNamedInputs();
    	
    	for (Map.Entry<String,Object> input : inputs.entrySet())
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
	    Query              query    = session.getNamedQuery( getName() );
		Collection<Object> inputs   = keeper.getPositionalInputs();
		int                position = 0;
		
		for (Object input : inputs)
		    query.setParameter( position++,input );
		
		return 
		    cardinality == ResultCardinality.ZERO_TO_MANY
                ? convertUntypedListToTypedList(query.list())
                : convertObjectToTypedList(query.uniqueResult());	        
	}

	/************************************************************************
	 *  
	 *
	 * @param template
	 * @return
	 */
	private List<T> 
	evaluateGetWithNoInputs(
	    Session           session,
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
        Session        session = factory.openSession();
        InputKeeper    keeper  = getInputs();
        
        switch ( keeper.getMode() )
        {
        case NAMED:
            return 
                evaluateHasWithNamedInputs( session,keeper );
            
        case POSITIONAL:
            return 
                evaluateHasWithPositionalInputs( session,keeper );
            
        default:
            return 
                evaluateHasWithNoInputs( session );
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
        Query              query  = session.getNamedQuery( getName() );
        Map<String,Object> inputs = keeper.getNamedInputs();
        
        query.setMaxResults( 2 );
        query.setFetchSize( 2 );
        
        for (Map.Entry<String,Object> input : inputs.entrySet())
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
        Session           session,
        InputKeeper       keeper)
    {
        Query              query    = session.getNamedQuery( getName() );
        Collection<Object> inputs   = keeper.getPositionalInputs();
        int                position = 0;
        
        query.setMaxResults( 2 );
        query.setFetchSize( 2 );
        
        for (Object input : inputs)
            query.setParameter( position++,input );
        
        return
            hasIntegerReturnType(query)
                ? (long)query.uniqueResult() 
                : query.list().size();           
    }

    /************************************************************************
     *  
     *
     * @param template
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
