// ##########################################################################
// # File Name:	ActiveUnitOfWorkState.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataPersistence Framework.
// #
// #   			The StrataPersistence Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataPersistence Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataPersistence
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.persistence.unitofwork;

import java.io.Serializable;
import strata.persistence.namedquery.INamedQuery;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ActiveUnitOfWorkState
    extends AbstractUnitOfWorkState
{
    private static final ActiveUnitOfWorkState theirInstance = 
        new ActiveUnitOfWorkState();
    
    /************************************************************************
     * Creates a new {@code ActiveUnitOfWorkState}. 
     *
     * @param name
     */
    private 
    ActiveUnitOfWorkState()
    {
        super( "Active" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> E 
    insertNew(
        AbstractUnitOfWork context,
        Class<K>           keyType, 
        Class<E>           entityType, 
        E                  newEntity)
    {
        return context.doInsertNew( keyType,entityType,newEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> E 
    updateExisting(
        AbstractUnitOfWork context,
        Class<K>           keyType, 
        Class<E>           entityType, 
        E                  existingEntity)
    {
        return context.doUpdateExisting( keyType,entityType,existingEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> void 
    removeExisting(
        AbstractUnitOfWork context,
        Class<K>           keyType, 
        Class<E>           entityType, 
        E                  existingEntity)
    {
        context.doRemoveExisting( keyType,entityType,existingEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> E 
    getExisting(AbstractUnitOfWork context,Class<E> type,K key)
    {
        return context.doGetExisting( type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <E> INamedQuery<E> 
    getNamedQuery(
        AbstractUnitOfWork context,
        Class<E>           type,
        String             queryName)
    {
        return context.doGetNamedQuery( type,queryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> boolean 
    hasExisting(AbstractUnitOfWork context,Class<E> type, K key)
    {
        return context.doHasExisting( type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <E> boolean 
    hasNamedQuery(
        AbstractUnitOfWork context,
        Class<E>           type,
        String             queryName)
    {
        return context.doHasNamedQuery( type,queryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isActive(AbstractUnitOfWork context)
    {
        return true;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    commit(AbstractUnitOfWork context) 
        throws CommitFailedException
    {
        try
        {
            context.doCommit();
            context.changeState( CommittedUnitOfWorkState.getInstance() );
        }
        catch (Exception e)
        {
            context.changeState( FailedUnitOfWorkState.getInstance() );
            throw new CommitFailedException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    rollback(AbstractUnitOfWork context)
        throws RollbackFailedException
    {
        try
        {
            context.doRollback();
            context.changeState( RolledBackUnitOfWorkState.getInstance() );
        }
        catch (Exception e)
        {
            context.changeState( FailedUnitOfWorkState.getInstance() );
            throw new RollbackFailedException( e );
        }
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public static ActiveUnitOfWorkState
    getInstance()
    {
        return theirInstance;
    }
}

// ##########################################################################
