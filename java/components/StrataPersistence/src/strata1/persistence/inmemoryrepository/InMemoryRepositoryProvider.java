// ##########################################################################
// # File Name:	InMemoryRepositoryProvider.java
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

package strata1.persistence.inmemoryrepository;

import strata1.persistence.repository.AbstractRepositoryProvider;
import strata1.persistence.repository.IFinder;
import strata1.persistence.repository.IKeyRetriever;
import strata1.persistence.repository.InsertFailedException;
import strata1.persistence.repository.RemoveFailedException;
import strata1.persistence.repository.UpdateFailedException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****************************************************************************
 * 
 * @param <K> key type
 * @param <T> entity type
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryRepositoryProvider<K extends Serializable,T> 
	extends AbstractRepositoryProvider<K,T>
{
    private Class<T>                  itsEntityClass;
	private InMemoryRepositoryContext itsContext;
	private IEntityReplicator<K,T>    itsReplicator;
	private IKeyRetriever<K,T>		  itsRetriever;
	private Map<String,IFinder<T>>    itsFinders;

	
	/************************************************************************
	 * Creates a new InMemoryRepositoryImp. 
	 *
	 */
	public 
	InMemoryRepositoryProvider(
	    Class<T>                  type,
		InMemoryRepositoryContext context,
		IEntityReplicator<K,T>    replicator,
		IKeyRetriever<K,T>        retriever)
	{
		super();
		itsEntityClass = type;
		itsContext     = context;
		itsReplicator  = replicator;
		itsRetriever   = retriever;
		itsFinders     = new HashMap<String,IFinder<T>>();
		
		initializeFinders( itsContext.getFinders( itsEntityClass ) );
		createFinder( "GetAll",new GetAllPredicate<T>() );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public InMemoryRepositoryContext 
	getContext()
	{
		return itsContext;
	}

	/************************************************************************
	 *  
	 *
	 * @param finderName
	 * @param predicate
	 */
	public void 
	createFinder(String finderName,IPredicate<T> predicate)
	{
	    IFinder<T> finder = 
	        new InMemoryFinder<T>(
	            itsContext,
	            finderName,
	            itsEntityClass,
	            predicate);
	    
	    itsFinders.put( finder.getName(),finder ); 
	}
	
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected T 
    doInsertNew(T entity) 
        throws InsertFailedException
    {
        try
        {
            return
                itsContext
                    .getUnitOfWork()
                    .doInsert(
                        entity,
                        itsEntityClass,
                        itsReplicator,
                        itsRetriever);
        }
        catch (Throwable cause)
        {
            throw new InsertFailedException(cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected T 
    doUpdateExisting(T entity) 
        throws UpdateFailedException
    {
        try
        {
            return
                itsContext
                    .getUnitOfWork()
                    .doUpdate( 
                        entity,
                        itsEntityClass,
                        itsReplicator, 
                        itsRetriever );
        }
        catch (Throwable cause)
        {
            throw new UpdateFailedException(cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    doRemoveExisting(T entity) 
        throws RemoveFailedException
    {
        try
        {
            itsContext
                .getUnitOfWork()
                .doRemove( entity,itsEntityClass,itsRetriever );
        }
        catch (Throwable cause)
        {
            throw new RemoveFailedException(cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected T 
    doGetExisting(K key)
    {
        return
            itsContext
                .getUnitOfWork()
                .doGet( itsEntityClass,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IFinder<T> 
    doGetFinder(String finderName)
    {
        return itsFinders.get( finderName ).copy();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected boolean 
    doHasExisting(K key)
    {
        return
            itsContext
                .getUnitOfWork()
                .doHas( itsEntityClass,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected boolean 
    doHasFinder(String finderName)
    {
        return itsFinders.containsKey( finderName );
    }
    
    /************************************************************************
     *  
     *
     * @param finders
     */
    private void
    initializeFinders(List<InMemoryFinder<T>> finders)
    {
        for (InMemoryFinder<T> finder : finders)
            itsFinders.put( finder.getName(),finder );
    }
}


// ##########################################################################
