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

package strata.persistence.inmemory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.repository.IKeyRetriever;
import strata.persistence.unitofwork.AbstractUnitOfWork;
import strata.persistence.unitofwork.CommitFailedException;
import strata.persistence.unitofwork.RollbackFailedException;

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
        itsInserted  = new HashMap<EntityIdentifier,Object>();
        itsUpdated   = new HashMap<EntityIdentifier,Object>();
        itsRemoved   = new HashMap<EntityIdentifier,Object>();
        itsEntities  = entities;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close() throws Exception
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> E 
    doInsertNew(Class<K> keyType,Class<E> entityType,E newEntity)
    {
        E                insertedEntity = null;
        K                key            = null;
        EntityIdentifier id             = null;
        
        insertedEntity = 
            getReplicator(keyType,entityType).replicate(newEntity);
        key =             
            getRetriever(keyType,entityType).getKey(insertedEntity);
        id = new EntityIdentifier(entityType,key);
        
        if ( itsUpdated.containsKey( id ) )
            throw new IllegalArgumentException("entity is already updated");
        
        if ( itsRemoved.containsKey( id ) )
            throw new IllegalArgumentException("entity is already removed");
        
        if ( itsInserted.containsKey( id ) || doHasExisting( entityType,key ) )
            throw new IllegalArgumentException("entity is already inserted");
        
        itsInserted.put( id,insertedEntity );
        return insertedEntity;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> E 
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        E                updatedEntity = null;
        K                key           = null;
        EntityIdentifier id            = null;
        
        updatedEntity = 
            getReplicator(keyType,entityType).replicate(existingEntity);
        key =             
            getRetriever(keyType,entityType).getKey(updatedEntity);
        id = new EntityIdentifier(entityType,key);
        
        if ( itsRemoved.containsKey( id ) )
            throw new IllegalArgumentException("entity is already removed");

        if ( !itsInserted.containsKey(id) && !doHasExisting(entityType,key))
            throw new IllegalArgumentException("entity does not exist");
        
        itsUpdated.put( new EntityIdentifier(entityType,key),updatedEntity );
        return updatedEntity;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> void 
    doRemoveExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        K                key = null;
        EntityIdentifier id  = null;
        
        key =             
            getRetriever(keyType,entityType).getKey(existingEntity);
        id = new EntityIdentifier(entityType,key);

        if ( !itsInserted.containsKey( id ) && !doHasExisting( entityType,key ) )
            throw new IllegalArgumentException("entity does not exist");
        
        itsRemoved.put( id,existingEntity );        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> E 
    doGetExisting(Class<E> type,K key)
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
     * {@inheritDoc} 
     */
    @Override
    protected <E> INamedQuery<E> 
    doGetNamedQuery(Class<E> type,String queryName)
    {
        return 
            ((InMemoryUnitOfWorkProvider)getProvider())
                .getNamedQuery( type,queryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> boolean 
    doHasExisting(Class<E> type,K key)
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
     * {@inheritDoc} 
     */
    @Override
    protected <E> boolean 
    doHasNamedQuery(Class<E> type,String queryName)
    {
        return doGetNamedQuery( type,queryName ) != null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    doCommit()
        throws CommitFailedException
    {
        
        try
        {
            commitInserted();
            commitUpdated();
            commitRemoved();
        }
        catch (Exception cause)
        {
            throw new CommitFailedException(cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    doRollback()
        throws RollbackFailedException
    {
        itsInserted.clear();
        itsUpdated.clear();
        itsRemoved.clear();
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
