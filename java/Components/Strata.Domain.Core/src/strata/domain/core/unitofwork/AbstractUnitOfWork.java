// ##########################################################################
// # File Name:	AbstractUnitOfWork.java
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
import java.util.Stack;
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
public abstract 
class AbstractUnitOfWork
    implements IUnitOfWork
{
    private IUnitOfWorkProvider itsProvider;
    private IUnitOfWorkState    itsCurrent;
    private Stack<Runnable> itsRollbackActions;
    
    /************************************************************************
     * Creates a new {@code AbstractUnitOfWork}. 
     *
     */
    protected 
    AbstractUnitOfWork(IUnitOfWorkProvider provider)
    {
        itsProvider = provider;
        itsCurrent  = ActiveUnitOfWorkState.getInstance();
        itsRollbackActions = new Stack<>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<E>
    insertNew(Class<K> keyType,Class<E> entityType,E newEntity)
    {
        return itsCurrent.insertNew( this,keyType,entityType,newEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<E>
    updateExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return
            itsCurrent
                .updateExisting( this,keyType,entityType,existingEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Void>
    removeExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        System.out.println("AbstractUnitOfWOrk.removeExisting: " + Thread.currentThread().getName());
        return
            itsCurrent.removeExisting( this,keyType,entityType,existingEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IUnitOfWorkProvider 
    getProvider()
    {
        return itsProvider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Optional<E>>
    getExisting(Class<E> type,K key)
    {
        System.out.println("AbstractUnitOfWOrk.getExisting: " + Thread.currentThread().getName());
        return itsCurrent.getExisting( this,type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <E> CompletionStage<Optional<INamedQuery<E>>>
    getNamedQuery(Class<E> type,String queryName)
    {
        System.out.println("AbstractUnitOfWOrk.getNamedQuery: " + Thread.currentThread().getName());
        return itsCurrent.getNamedQuery( this,type,queryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> CompletionStage<Boolean>
    hasExisting(Class<E> type,K key)
    {
        System.out.println("AbstractUnitOfWOrk.hasExisting: " + Thread.currentThread().getName());
        return itsCurrent.hasExisting( this,type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <E> CompletionStage<Boolean>
    hasNamedQuery(Class<E> type,String queryName)
    {
        System.out.println("AbstractUnitOfWOrk.hasNamedQuery: " + Thread.currentThread().getName());
        return itsCurrent.hasNamedQuery( this,type,queryName );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public void
    pushRollbackAction(Runnable action)
    {
        itsRollbackActions.push(action);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isActive()
    {
        return itsCurrent.isActive( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isFailed()
    {
        return itsCurrent.isFailed( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isCommitted()
    {
        return itsCurrent.isCommitted( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRolledback()
    {
        return itsCurrent.isRolledBack( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    commit() 
    {
        System.out.println("AbstractUnitOfWOrk.commit: " + Thread.currentThread().getName());
        return itsCurrent.commit( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    rollback()
    {
        System.out.println("AbstractUnitOfWOrk.rollback: " + Thread.currentThread().getName());
        return itsCurrent.rollback( this );
    }
    
    /************************************************************************
     *  
     *
     * @param state
     */
    protected void
    changeState(IUnitOfWorkState state)
    {
        itsCurrent = state;
    }

    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param newEntity
     * @return
     */
    protected abstract <K extends Serializable,E> CompletionStage<E>
    doInsertNew(Class<K> keyType,Class<E> entityType,E newEntity);

    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     * @return
     */
    protected abstract <K extends Serializable,E> CompletionStage<E>
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity);

    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     */
    protected abstract <K extends Serializable,E> CompletionStage<Void>
    doRemoveExisting(Class<K> keyType,Class<E> entityType,E existingEntity);

    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    protected abstract <K extends Serializable,E> CompletionStage<Optional<E>>
    doGetExisting(Class<E> type,K key);

    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    protected abstract <E> CompletionStage<Optional<INamedQuery<E>>>
    doGetNamedQuery(Class<E> type,String queryName);

    /************************************************************************
     *  
     *
     * @param type TODO
     * @param key
     * @return
     */
    protected abstract <K extends Serializable,E> CompletionStage<Boolean>
    doHasExisting(Class<E> type,K key);

    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    protected abstract <E> CompletionStage<Boolean>
    doHasNamedQuery(Class<E> type,String queryName);
    
    /************************************************************************
     *  
     *
     */
    protected abstract CompletionStage<Void>
    doCommit();
    
    /************************************************************************
     *  
     *
     */
    protected CompletionStage<Void>
    doRollback()
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    System.out.println("AbstractUnitOfWork.doRollback");
                    RollbackFailedException failed = null;

                    while (!itsRollbackActions.isEmpty())
                    {
                        try
                        {
                            Runnable action = itsRollbackActions.pop();

                            action.run();
                        }
                        catch (Exception e)
                        {
                            if (failed == null)
                                failed =
                                    new RollbackFailedException(
                                        "Rollback failed. See causes.");

                            failed.addCause(e);
                        }
                    }

                    if (failed != null)
                        throw new CompletionException(failed);

                    return null;
                });
    }
}

// ##########################################################################
