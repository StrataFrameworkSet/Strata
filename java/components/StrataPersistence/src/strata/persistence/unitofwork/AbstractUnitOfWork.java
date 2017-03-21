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
public abstract 
class AbstractUnitOfWork
    implements IUnitOfWork
{
    private IUnitOfWorkProvider itsProvider;
    private IUnitOfWorkState    itsCurrent;
    
    /************************************************************************
     * Creates a new {@code AbstractUnitOfWork}. 
     *
     */
    protected 
    AbstractUnitOfWork(IUnitOfWorkProvider provider)
    {
        itsProvider = provider;
        itsCurrent  = ActiveUnitOfWorkState.getInstance();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> E 
    insertNew(Class<K> keyType, Class<E> entityType, E newEntity)
    {
        return itsCurrent.insertNew( this,keyType,entityType,newEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> E 
    updateExisting(Class<K> keyType, Class<E> entityType, E existingEntity)
    {
        return 
            itsCurrent
                .updateExisting( this,keyType,entityType,existingEntity );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> void 
    removeExisting(Class<K> keyType, Class<E> entityType, E existingEntity)
    {
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
    public <K extends Serializable,E> E 
    getExisting(Class<E> type,K key)
    {
        return itsCurrent.getExisting( this,type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <E> INamedQuery<E> 
    getNamedQuery(Class<E> type,String queryName)
    {
        return itsCurrent.getNamedQuery( this,type,queryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> boolean 
    hasExisting(Class<E> type,K key)
    {
        return itsCurrent.hasExisting( this,type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <E> boolean 
    hasNamedQuery(Class<E> type,String queryName)
    {
        return itsCurrent.hasNamedQuery( this,type,queryName );
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
    public void 
    commit() 
        throws CommitFailedException
    {
        itsCurrent.commit( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    rollback() throws RollbackFailedException
    {
        itsCurrent.rollback( this );
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
    protected abstract <K extends Serializable,E> E 
    doInsertNew(Class<K> keyType, Class<E> entityType, E newEntity);

    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     * @return
     */
    protected abstract <K extends Serializable,E> E 
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity);

    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     */
    protected abstract <K extends Serializable,E> void 
    doRemoveExisting(Class<K> keyType, Class<E> entityType, E existingEntity);

    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    protected abstract <K extends Serializable,E> E 
    doGetExisting(Class<E> type,K key);

    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    protected abstract <E> INamedQuery<E> 
    doGetNamedQuery(Class<E> type,String queryName);

    /************************************************************************
     *  
     *
     * @param type TODO
     * @param key
     * @return
     */
    protected abstract <K extends Serializable,E> boolean 
    doHasExisting(Class<E> type, K key);

    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    protected abstract <E> boolean 
    doHasNamedQuery(Class<E> type,String queryName);
    
    /************************************************************************
     *  
     *
     * @throws CommitFailedException
     */
    protected abstract void
    doCommit() throws CommitFailedException;
    
    /************************************************************************
     *  
     *
     * @throws RollbackFailedException
     */
    protected abstract void
    doRollback() throws RollbackFailedException;
}

// ##########################################################################
