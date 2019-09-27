// ##########################################################################
// # File Name:	AbstractUnitOfWorkState.java
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractUnitOfWorkState
    implements IUnitOfWorkState
{
    private final String itsName;
    
    /************************************************************************
     * Creates a new {@code AbstractUnitOfWorkState}. 
     *
     */
    protected 
    AbstractUnitOfWorkState(String name)
    {
        itsName = name;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String
    getName()
    {
        return itsName;
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
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call insert(E) from state: " + getName() + "." ));
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
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call update(E) from state: " +
                    getName() + "." ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Void>
    removeExisting(
        AbstractUnitOfWork context,
        Class<K> keyType,
        Class<E> entityType,
        E                  existingEntity)
    {
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call remove(E) from state: " +
                    getName() + "." ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Optional<E>>
    getExisting(AbstractUnitOfWork context,Class<E> type,K key)
    {
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call getUnique(Class<E>,K) from " +
                    "state: " + getName() + "." ));
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
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call getNamedQuery(Class<E>,String) " +
                    "from state: " + getName() + "." ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Boolean>
    hasExisting(AbstractUnitOfWork context,Class<E> type,K key)
    {
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call hasExisting(Class<E>,K) from " +
                    "state: " + getName() + "." ));
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
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call hasNamedQuery(Class<E>,String) " +
                    "from state: " + getName() + "." ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isActive(AbstractUnitOfWork context)
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isFailed(AbstractUnitOfWork context)
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isCommitted(AbstractUnitOfWork context)
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRolledBack(AbstractUnitOfWork context)
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    commit(AbstractUnitOfWork context) 
    {
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call commit() from state: " + getName() + "." ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    rollback(AbstractUnitOfWork context)
    {
        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Cannot call rollback() from state: " + getName() + "." ));
    }

}

// ##########################################################################
