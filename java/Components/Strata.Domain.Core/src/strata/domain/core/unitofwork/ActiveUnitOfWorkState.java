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

package strata.domain.core.unitofwork;

import strata.domain.core.namedquery.INamedQuery;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

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
    public <K extends Serializable,E> CompletionStage<E>
    insertNew(
        AbstractUnitOfWork context,
        Class<K> keyType,
        Class<E> entityType,
        E                  newEntity)
    {
        return context.doInsertNew( keyType,entityType,newEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<E>
    updateExisting(
        AbstractUnitOfWork context,
        Class<K> keyType,
        Class<E> entityType,
        E                  existingEntity)
    {
        return context.doUpdateExisting( keyType,entityType,existingEntity );
    }

    /************************************************************************
     * {@inheritDoc}
     * @return
     */
    @Override
    public <K extends Serializable,E> CompletionStage<E>
    removeExisting(
        AbstractUnitOfWork context,
        Class<K> keyType,
        Class<E> entityType,
        E                  existingEntity)
    {
        return context.doRemoveExisting( keyType,entityType,existingEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Optional<E>>
    getExisting(AbstractUnitOfWork context,Class<E> type,K key)
    {
        return context.doGetExisting( type,key );
    }

    /************************************************************************
     * {@inheritDoc}
     * @return
     */
    @Override
    public <E> CompletionStage<INamedQuery<E>>
    getNamedQuery(
        AbstractUnitOfWork context,
        Class<E> type,
        String queryName)
    {
        return context.doGetNamedQuery( type,queryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Boolean>
    hasExisting(AbstractUnitOfWork context,Class<E> type,K key)
    {
        return context.doHasExisting( type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <E> CompletionStage<Boolean>
    hasNamedQuery(
        AbstractUnitOfWork context,
        Class<E> type,
        String queryName)
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
    public CompletionStage<Void>
    commit(AbstractUnitOfWork context) 
    {
        return
            context
                .doCommit()
                .thenApply(
                    v ->
                    {
                        context.changeState(CommittedUnitOfWorkState.getInstance());
                        return (Void)null;
                    })
                .exceptionally(
                    e ->
                    {
                        context.changeState( FailedUnitOfWorkState.getInstance() );
                        return null;
                    });
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    rollback(AbstractUnitOfWork context)
    {
        return
            context
                .doRollback()
                .thenApply(
                    v ->
                    {
                        context.changeState(
                            RolledBackUnitOfWorkState.getInstance());
                        return (Void)null;
                    })
                .exceptionally(
                    e ->
                    {
                        context.changeState(FailedUnitOfWorkState.getInstance());
                        return null;
                    });
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
