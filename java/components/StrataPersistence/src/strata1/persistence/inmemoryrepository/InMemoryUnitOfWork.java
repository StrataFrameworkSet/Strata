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

package strata1.persistence.inmemoryrepository;

import strata1.persistence.repository.CommitFailedException;
import strata1.persistence.repository.IKeyRetriever;
import strata1.persistence.repository.IUnitOfWork;
import strata1.persistence.repository.RollbackFailedException;
import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryUnitOfWork
    implements IUnitOfWork
{
    private Map<EntityIdentifier,Object> itsInserted;
    private Map<EntityIdentifier,Object> itsUpdated;
    private Map<EntityIdentifier,Object> itsRemoved;
    private Map<EntityIdentifier,Object> itsEntities;
    private UnitOfWorkState              itsState;
    
    /************************************************************************
     * Creates a new {@code InMemoryUnitOfWork}. 
     *
     */
    public 
    InMemoryUnitOfWork(Map<EntityIdentifier,Object> entities)
    {
        itsInserted  = new HashMap<EntityIdentifier,Object>();
        itsUpdated   = new HashMap<EntityIdentifier,Object>();
        itsRemoved   = new HashMap<EntityIdentifier,Object>();
        itsEntities  = entities;
        itsState     = UnitOfWorkState.ACTIVE;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    commit() 
        throws CommitFailedException
    {
        try
        {
            if ( !isActive() )
                throw new IllegalStateException("inactive transaction");
            
            commitInserted();
            commitUpdated();
            commitRemoved();
            
            itsState = UnitOfWorkState.COMMITTED;
        }
        catch (Throwable cause)
        {
            throw new CommitFailedException(cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    rollback() 
        throws RollbackFailedException
    {
        
        itsState = UnitOfWorkState.ROLLED_BACK;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isActive()
    {
        return itsState == UnitOfWorkState.ACTIVE;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isCommitted()
    {
        return itsState == UnitOfWorkState.COMMITTED;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRolledback()
    {
        itsInserted.clear();
        itsUpdated.clear();
        itsRemoved.clear();
        return itsState == UnitOfWorkState.ROLLED_BACK;
    }

    /************************************************************************
     * {@inheritDoc} 
     * @param type TODO
     */
    public <K,T> T 
    doInsert(
        T                      entity,
        Class<T>               type,
        IEntityReplicator<K,T> replicator, 
        IKeyRetriever<K,T>     retriever)
    {
        T                insertedEntity = replicator.replicate( entity );
        K                key            = retriever.getKey( insertedEntity );
        EntityIdentifier id             = new EntityIdentifier(type,key);
        
        if ( itsUpdated.containsKey( id ) )
            throw new IllegalArgumentException("entity is already updated");
        
        if ( itsRemoved.containsKey( id ) )
            throw new IllegalArgumentException("entity is already removed");
        
        if ( itsInserted.containsKey( id ) || doHas( type,key ) )
            throw new IllegalArgumentException("entity is already inserted");
        
        itsInserted.put( id,insertedEntity );
        return insertedEntity;
    }

    /************************************************************************
     * {@inheritDoc} 
     * @param type TODO
     */
    public <K,T> T 
    doUpdate(
        T                      entity,
        Class<T>               type,
        IEntityReplicator<K,T> replicator, 
        IKeyRetriever<K,T>     retriever)
    {
        T                updatedEntity = replicator.replicate( entity );
        K                key           = retriever.getKey( updatedEntity );
        EntityIdentifier id            = new EntityIdentifier(type,key);
        
        if ( itsRemoved.containsKey( id ) )
            throw new IllegalArgumentException("entity is already removed");

        if ( !itsInserted.containsKey( id ) && !doHas( type,key ) )
            throw new IllegalArgumentException("entity does not exist");
        
        itsUpdated.put( new EntityIdentifier(type,key),updatedEntity );
        return updatedEntity;
    }

    /************************************************************************
     * {@inheritDoc} 
     * @param type TODO
     */
    public <K,T> void 
    doRemove(T entity,Class<T> type,IKeyRetriever<K,T> retriever)
    {
        K                key = retriever.getKey( entity );
        EntityIdentifier id  = new EntityIdentifier(type,key);
        
        if ( !itsInserted.containsKey( id ) && !doHas( type,key ) )
            throw new IllegalArgumentException("entity does not exist");
        
        itsRemoved.put( id,entity );
    }

    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    public <K,T> T
    doGet(Class<T> type,K key)
    {
        EntityIdentifier id = new EntityIdentifier(type,key);
        
        if ( itsRemoved.containsKey( id ) )
            return null;
        
        if ( itsUpdated.containsKey( id ) )
            return type.cast( itsUpdated.get( id ) );
        
        if ( itsInserted.containsKey( id ) )
            return type.cast( itsInserted.get( id ) );
        
        if ( itsEntities.containsKey( id ) )
            return type.cast( itsEntities.get( id ) );
        
        return getCandidates( key.getClass(),type ).get( key );
    }
    
    /************************************************************************
     *  
     *
     * @param type TODO
     * @param key
     * @return
     */
    public <K,T> boolean
    doHas(Class<T> type,K key)
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
        
        return getCandidates( key.getClass(),type ).containsKey( key );
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
