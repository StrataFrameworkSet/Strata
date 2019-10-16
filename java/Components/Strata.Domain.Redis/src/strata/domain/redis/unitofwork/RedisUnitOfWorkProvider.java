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

package strata.domain.redis.unitofwork;

import io.lettuce.core.RedisClient;
import strata.domain.core.repository.IKeyRetriever;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.redis.namedquery.IRedisNamedQuery;
import strata.foundation.core.utility.IObjectMapper;
import strata.foundation.core.utility.JsonObjectMapper;
import strata.foundation.core.utility.Pair;

import java.util.Map;
import java.util.concurrent.*;

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
class RedisUnitOfWorkProvider
	implements IUnitOfWorkProvider
{
    private RedisClient                          itsClient;
	private INamedQueryMap                       itsNamedQueries;
    private Map<Class<?>,IEntityReplicator<?,?>> itsReplicators;
	private Map<Class<?>,IKeyRetriever<?,?>>     itsRetrievers;
	private ExecutorService                      itsExecutor;
	private IObjectMapper<Object,String>         itsMapper;
    private RedisUnitOfWork                      itsUnitOfWork;

    /************************************************************************
     * Creates a new RedisUnitOfWorkProvider.
     *
     */
    public
    RedisUnitOfWorkProvider(RedisClient client)
    {
        this(client,new JsonObjectMapper<>());
    }

    /************************************************************************
     * Creates a new RedisUnitOfWorkProvider.
     *
     */
    public
    RedisUnitOfWorkProvider(
        RedisClient                  client,
        IObjectMapper<Object,String> mapper)
    {
        super();
        itsClient       = client;
        itsNamedQueries = new NamedQueryMap();
        itsReplicators  = new ConcurrentHashMap<>();
        itsRetrievers   = new ConcurrentHashMap<>();
        itsExecutor     = Executors.newSingleThreadExecutor();
        itsMapper       = mapper;
        itsUnitOfWork   = new RedisUnitOfWork(this);
    }

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<IUnitOfWork>
    getUnitOfWork()
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    if ( itsUnitOfWork == null || !itsUnitOfWork.isActive() )
                        itsUnitOfWork = new RedisUnitOfWork(this);

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
     * {@inheritDoc}
     */
    @Override
    public ExecutorService
    getExecutor()
    {
        return itsExecutor;
    }

    /************************************************************************
     *
     */
    public RedisClient
    getClient() { return itsClient; }

    /************************************************************************
     *
     */
    public IObjectMapper<Object,String>
    getMapper() { return itsMapper; }

    /************************************************************************
     *
     */
    public IReadOnlyUnitOfWork
    getReadOnlyUnitOfWork()
    {
        if ( itsUnitOfWork == null || !itsUnitOfWork.isActive() )
            itsUnitOfWork = new RedisUnitOfWork(this);

        return itsUnitOfWork;
    }

    /************************************************************************
     *  
     *
     * @param type
     * @param query
     */
    public <T> RedisUnitOfWorkProvider
    insertNamedQuery(Class<T> type,IRedisNamedQuery<T> query)
    {
        Pair<Class<?>,String> key = Pair.create(type,query.getName());
        
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
    public <K,T> RedisUnitOfWorkProvider
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
    public <K,T> RedisUnitOfWorkProvider
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
    @SuppressWarnings("unchecked")
    public <T> IRedisNamedQuery<T>
    getNamedQuery(Class<T> type,String queryName)
    {
        Pair<Class<?>,String> key =
            Pair.create(type,type.getName() + "." + queryName);
        
        return (IRedisNamedQuery<T>)itsNamedQueries.get( key );
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
}


// ##########################################################################
