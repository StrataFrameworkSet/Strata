// ##########################################################################
// # File Name:	InMemoryUnitOfWork.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.repository.IKeyRetriever;
import strata.domain.core.repository.InsertFailedException;
import strata.domain.core.repository.RemoveFailedException;
import strata.domain.core.repository.UpdateFailedException;
import strata.domain.core.unitofwork.AbstractUnitOfWork;
import strata.domain.core.unitofwork.CommitFailedException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryUnitOfWork
    extends AbstractUnitOfWork
{
    private Map<EntityIdentifier,Object> itsInserted;
    private Map<EntityIdentifier,Object> itsUpdated;
    private Map<EntityIdentifier,Object> itsRemoved;
    private Map<EntityIdentifier,Object> itsEntities;
    private final ExecutorService        itsExecutor;
    
    /************************************************************************
     * Creates a new {@code InMemoryUnitOfWork}. 
     *
     */
    public 
    InMemoryUnitOfWork(
        InMemoryUnitOfWorkProvider   provider,
        Map<EntityIdentifier,Object> entities)
    {
        super( provider );
        itsInserted = new HashMap<>();
        itsUpdated  = new HashMap<>();
        itsRemoved  = new HashMap<>();
        itsEntities = entities;
        itsExecutor = provider.getExecutor();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doInsertNew(Class<K> keyType,Class<E> entityType,E newEntity)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    System.out.println("InMemoryUnitOfWork.doInsertNew: " + Thread.currentThread().getName());
                    E                insertedEntity = null;
                    K                key            = null;
                    EntityIdentifier id             = null;

                    try
                    {
                        insertedEntity =
                            getReplicator(keyType,entityType).replicate(newEntity);
                        key =
                            getRetriever(keyType,entityType).getKey(insertedEntity);
                        id = new EntityIdentifier(entityType,key);

                        if (itsUpdated.containsKey(id))
                            throw new IllegalArgumentException("entity is already updated");

                        if (itsRemoved.containsKey(id))
                            throw new IllegalArgumentException("entity is already removed");

                        if (
                            itsInserted.containsKey(id) ||
                                doHasExistingInternal(entityType,key))
                            throw new IllegalArgumentException("entity is already inserted");

                        itsInserted.put(id,insertedEntity);
                        return insertedEntity;
                    }
                    catch (Exception e)
                    {
                        throw
                            new CompletionException(
                                new InsertFailedException(e));
                    }

                },
                itsExecutor);

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    System.out.println("InMemoryUnitOfWork.doUpdateExisting: " + Thread.currentThread().getName());
                    E                updatedEntity = null;
                    K                key           = null;
                    EntityIdentifier id            = null;

                    try
                    {
                        updatedEntity =
                            getReplicator(keyType,entityType).replicate(existingEntity);
                        key =
                            getRetriever(keyType,entityType).getKey(updatedEntity);
                        id = new EntityIdentifier(entityType,key);

                        if (itsRemoved.containsKey(id))
                            throw new IllegalArgumentException("entity is already removed");

                        if (
                            !itsInserted.containsKey(id) &&
                                !doHasExistingInternal(entityType,key))
                            throw new IllegalArgumentException("entity does not exist");

                        itsUpdated.put(new EntityIdentifier(entityType,key),updatedEntity);
                        return updatedEntity;
                    }
                    catch (Exception e)
                    {
                        throw
                            new CompletionException(
                                new UpdateFailedException(e));
                    }
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc}
     * @return
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doRemoveExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    K                key = null;
                    EntityIdentifier id  = null;

                    try
                    {
                        key =
                            getRetriever(keyType,entityType).getKey(existingEntity);
                        id = new EntityIdentifier(entityType,key);

                        if (
                            !itsInserted.containsKey(id) &&
                                !doHasExistingInternal(entityType,key))
                            throw new IllegalArgumentException("entity does not exist");

                        itsRemoved.put(id,existingEntity);
                        return existingEntity;
                    }
                    catch (Exception e)
                    {
                        throw
                            new CompletionException(
                                new RemoveFailedException(e));
                    }

                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Optional<E>>
    doGetExisting(Class<E> type,K key)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    System.out.println("InMemoryUnitOfWork.doGetExisting: " + Thread.currentThread().getName());
                    EntityIdentifier id = new EntityIdentifier(type,key);

                    if ( itsRemoved.containsKey( id ) )
                        return Optional.empty();

                    if ( itsUpdated.containsKey( id ) )
                        return
                            Optional.ofNullable(
                                type.cast(itsUpdated.get(id)));

                    if ( itsInserted.containsKey( id ) )
                        return
                            Optional.ofNullable(
                                type.cast(itsInserted.get(id)));

                    if ( itsEntities.containsKey( id ) )
                        return
                            Optional.ofNullable(
                                type.cast(itsEntities.get(id)));

                    return
                        Optional.ofNullable(
                            getCandidates(key.getClass(),type).get(key));

                },
                itsExecutor);

    }

    /************************************************************************
     * {@inheritDoc}
     * @return
     */
    @Override
    protected <E> CompletionStage<INamedQuery<E>>
    doGetNamedQuery(Class<E> type,String queryName)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    INamedQuery<E> query =
                        ((InMemoryUnitOfWorkProvider)getProvider())
                            .getNamedQuery(type,queryName);

                    if ( query == null )
                        throw new NullPointerException(queryName + " not found");

                    return query;
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Boolean>
    doHasExisting(Class<E> type,K key)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    System.out.println("InMemoryUnitOfWork.doHasExisting: " + Thread.currentThread().getName());
                    EntityIdentifier id = new EntityIdentifier(type,key);

                    if ( itsRemoved.containsKey( id ) )
                        return false;

                    if ( itsUpdated.containsKey( id ) )
                        return true;

                    if ( itsInserted.containsKey( id ) )
                        return true;

                    if ( itsEntities.containsKey( id ) )
                        return true;

                    return
                        getCandidates(key.getClass(),type)
                            .containsKey(key);

                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <E> CompletionStage<Boolean>
    doHasNamedQuery(Class<E> type,String queryName)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    System.out.println("InMemoryUnitOfWork.doHasNamedQuery: " + Thread.currentThread().getName());
                    return doGetNamedQuery(type,queryName) != null;
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected CompletionStage<Void>
    doCommit()
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    synchronized (itsEntities)
                    {
                        try
                        {
                            commitInserted();
                            commitUpdated();
                            commitRemoved();
                            return null;
                        }
                        catch (Exception cause)
                        {
                            throw
                                new CompletionException(
                                    new CommitFailedException(cause));
                        }
                    }
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected CompletionStage<Void>
    doRollback()
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    itsInserted.clear();
                    itsUpdated.clear();
                    itsRemoved.clear();
                    return null;
                },
                itsExecutor);
    }


    /************************************************************************
     *  
     *
     */
    protected void
    commitInserted()
    {
        for (
            Map.Entry<EntityIdentifier,Object> entry :
                itsInserted.entrySet())
            itsEntities.put( entry.getKey(),entry.getValue() );
    }
    
    /************************************************************************
     *  
     *
     */
    protected void
    commitUpdated()
    {
        for (
            Map.Entry<EntityIdentifier,Object> entry :
                itsUpdated.entrySet())
            itsEntities.put( entry.getKey(),entry.getValue() );
    }
   
    /************************************************************************
     *  
     *
     */
    protected void
    commitRemoved()
    {
        for (
            Map.Entry<EntityIdentifier,Object> entry :
                itsRemoved.entrySet())
        {
            if ( itsEntities.remove( entry.getKey() ) == null )
                throw new IllegalStateException("entity does not exist");
        }
    }

    /************************************************************************
     *  
     *
     * @param keyType
     * @param entityType
     * @return
     */
    private <K extends Serializable,E> IEntityReplicator<K,E>
    getReplicator(Class<K> keyType,Class<E> entityType)
    {
        return 
            ((InMemoryUnitOfWorkProvider)getProvider())
                .getReplicator( keyType,entityType );
    }

    /************************************************************************
     *  
     *
     * @param keyType
     * @param entityType
     * @return
     */
    private <K extends Serializable,E> IKeyRetriever<K,E>
    getRetriever(Class<K> keyType,Class<E> entityType)
    {
        return 
            ((InMemoryUnitOfWorkProvider)getProvider())
                .getRetriever( keyType,entityType );
    }

    /************************************************************************
     *  
     *
     * @param keyType
     * @param entityType
     * @return
     */
    private <K,T> Map<K,T>
    getCandidates(Class<K> keyType,Class<T> entityType)
    {
        Map<K,T> candidates = new HashMap<K,T>();
        
        for (
            Map.Entry<EntityIdentifier,Object> entry:
                itsEntities.entrySet())
        {
            if ( entityType.isAssignableFrom( entry.getKey().getType() ) )
            {
                try
                {
                    candidates.put( 
                        keyType.cast( entry.getKey().getKey() ),
                        entityType.cast( entry.getValue() ) );
                }
                catch (ClassCastException e) {}
            }
        }
        
        return candidates;
    }


    /************************************************************************
     * {@inheritDoc}
     */
    protected <E,K> boolean
    doHasExistingInternal(Class<E> type,K key)
    {
        EntityIdentifier id = new EntityIdentifier(type,key);

        if ( itsRemoved.containsKey( id ) )
            return false;

        if ( itsUpdated.containsKey( id ) )
            return true;

        if ( itsInserted.containsKey( id ) )
            return true;

        if ( itsEntities.containsKey( id ) )
            return true;

        return
            getCandidates(key.getClass(),type)
                .containsKey(key);
    }

}

// ##########################################################################
