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

package strata.domain.inmemory;

import strata.domain.core.repository.IKeyRetriever;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.foundation.core.utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Contains the shared lock and in-memory transactions used by related
 * <code>PersonManager</code>s and <code>OrganizationManager</code>s.
 *  
 * @author 		
 *     Java Persistence Strategy Workgroup 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryUnitOfWorkProvider 
	implements IUnitOfWorkProvider
{
    private Map<EntityIdentifier,Object>         itsEntities;
	private INamedQueryMap                       itsNamedQueries;
    private Map<Class<?>,IEntityReplicator<?,?>> itsReplicators;
	private Map<Class<?>,IKeyRetriever<?,?>>     itsRetrievers;
	private ExecutorService                      itsExecutor;
    private InMemoryUnitOfWork                   itsUnitOfWork;

	/************************************************************************
	 * Creates a new InMemoryUnitOfWorkProvider. 
	 *
	 */
	public 
	InMemoryUnitOfWorkProvider()
	{
		super();
        itsEntities     = new HashMap<>();
		itsNamedQueries = new NamedQueryMap();
		itsReplicators  = new HashMap<>();
		itsRetrievers   = new HashMap<>();
		itsExecutor     = Executors.newSingleThreadExecutor();
        itsUnitOfWork   = new InMemoryUnitOfWork(this,itsEntities);
	}

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<? extends IUnitOfWork>
    getUnitOfWork()
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    if ( itsUnitOfWork == null || !itsUnitOfWork.isActive() )
                        itsUnitOfWork = new InMemoryUnitOfWork(this,itsEntities);

                    return itsUnitOfWork;
                },
                itsExecutor);

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletableFuture<Void>
    clearUnitOfWork()
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    itsUnitOfWork = null;
                    return null;
                },
                itsExecutor);
    }

    /************************************************************************
     *  
     *
     * @param type
     * @param query
     */
    public <T> InMemoryUnitOfWorkProvider
    insertNamedQuery(Class<T> type,InMemoryNamedQuery<T> query)
    {
        Pair<Class<?>,String> key =
            new Pair<Class<?>,String>(type,query.getName());
        
        itsNamedQueries.put(key,query);
        return this;
    }

    /************************************************************************
     *  
     *
     * @param type
     * @param replicator
     * @return
     */
    public <K,T> InMemoryUnitOfWorkProvider
    insertReplicator(Class<T> type,IEntityReplicator<K,T> replicator)
    {
        itsReplicators.put( type,replicator );
        return this;
    }

    /************************************************************************
     *  
     *
     * @param type
     * @param retriever
     * @return
     */
    public <K,T> InMemoryUnitOfWorkProvider
    insertRetriever(Class<T> type,IKeyRetriever<K,T> retriever)
    {
        itsRetrievers.put( type,retriever );
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public <T> List<T>
    getEntitiesByType(Class<T> type)
    {
        List<T> entities = new ArrayList<T>();
        
        for (
            Map.Entry<EntityIdentifier,Object> entry:
                itsEntities.entrySet())
        {
            if ( type.isAssignableFrom( entry.getKey().getType() ) )
                entities.add( type.cast( entry.getValue() ) );
        }
        
        return entities;
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> InMemoryNamedQuery<T>
    getNamedQuery(Class<T> type,String queryName)
    {
        Pair<Class<?>,String> key =
            new Pair<Class<?>,String>( type,type.getName() + "." + queryName );
        
        return (InMemoryNamedQuery<T>)itsNamedQueries.get( key );
    }
    
    /************************************************************************
     *  
     *
     * @param keyType
     * @param entityType
     * @return
     */
    @SuppressWarnings("unchecked")
    public <K,T> IEntityReplicator<K,T>
    getReplicator(Class<K> keyType,Class<T> entityType)
    {
        return (IEntityReplicator<K,T>)itsReplicators.get( entityType );
    }
    
    /************************************************************************
     *  
     *
     * @param keyType
     * @param entityType
     * @return
     */
    @SuppressWarnings("unchecked")
    public <K,T> IKeyRetriever<K,T>
    getRetriever(Class<K> keyType,Class<T> entityType)
    {
        return (IKeyRetriever<K,T>)itsRetrievers.get( entityType );
    }

    /************************************************************************
     *
     * @return
     */
    public ExecutorService
    getExecutor()
    {
        if (itsExecutor == null)
            throw new IllegalStateException("executor must be non-null");

        return itsExecutor;
    }
}


// ##########################################################################
