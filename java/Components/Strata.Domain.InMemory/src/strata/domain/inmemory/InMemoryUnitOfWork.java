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
        itsInserted  = new HashMap<>();
        itsUpdated   = new HashMap<>();
        itsRemoved   = new HashMap<>();
        itsEntities  = entities;
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
                    doHasExisting(entityType,key).toCompletableFuture().join())
                throw new IllegalArgumentException("entity is already inserted");

            itsInserted.put(id,insertedEntity);
            return CompletableFuture.completedFuture(insertedEntity);
        }
        catch (Exception e)
        {
            return
                CompletableFuture.failedFuture(
                    new InsertFailedException(e));
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
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
                    !doHasExisting(entityType,key).toCompletableFuture().join())
                throw new IllegalArgumentException("entity does not exist");

            itsUpdated.put(new EntityIdentifier(entityType,key),updatedEntity);
            return CompletableFuture.completedFuture(updatedEntity);
        }
        catch (Exception e)
        {
            return
                CompletableFuture.failedFuture(
                    new UpdateFailedException(e));
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Void>
    doRemoveExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
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
                    !doHasExisting(entityType,key).toCompletableFuture().join())
                throw new IllegalArgumentException("entity does not exist");

            itsRemoved.put(id,existingEntity);
            return CompletableFuture.completedFuture(null);
        }
        catch (Exception e)
        {
            return
                CompletableFuture.failedFuture(
                    new RemoveFailedException(e));
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Optional<E>>
    doGetExisting(Class<E> type,K key)
    {
        EntityIdentifier id = new EntityIdentifier(type,key);
        
        if ( itsRemoved.containsKey( id ) )
            return
                CompletableFuture.completedFuture(Optional.empty());
        
        if ( itsUpdated.containsKey( id ) )
            return
                CompletableFuture.completedFuture(
                    Optional.ofNullable(type.cast(itsUpdated.get(id))));
        
        if ( itsInserted.containsKey( id ) )
            return
                CompletableFuture.completedFuture(
                    Optional.ofNullable(type.cast(itsInserted.get(id))));
        
        if ( itsEntities.containsKey( id ) )
            return
                CompletableFuture.completedFuture(
                    Optional.ofNullable(type.cast(itsEntities.get(id))));
        
        return
            CompletableFuture.completedFuture(
                Optional.ofNullable(
                    getCandidates(key.getClass(),type).get(key)));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <E> CompletionStage<Optional<INamedQuery<E>>>
    doGetNamedQuery(Class<E> type,String queryName)
    {
        return
            CompletableFuture.completedFuture(
                Optional.ofNullable(
                    ((InMemoryUnitOfWorkProvider)getProvider())
                        .getNamedQuery(type,queryName)));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Boolean>
    doHasExisting(Class<E> type,K key)
    {
        EntityIdentifier id = new EntityIdentifier(type,key);
        
        if ( itsRemoved.containsKey( id ) )
            return CompletableFuture.completedFuture(false);
        
        if ( itsUpdated.containsKey( id ) )
            return CompletableFuture.completedFuture(true);
        
        if ( itsInserted.containsKey( id ) )
            return CompletableFuture.completedFuture(true);
        
        if ( itsEntities.containsKey( id ) )
            return CompletableFuture.completedFuture(true);
        
        return
            CompletableFuture.supplyAsync(
                () -> getCandidates(key.getClass(),type).containsKey(key) );
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
                () -> doGetNamedQuery(type,queryName) != null);
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
                });
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
                });
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
}

// ##########################################################################
