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
    public <K extends Serializable,E> E 
    insertNew(
        AbstractUnitOfWork context,
        Class<K>           keyType, 
        Class<E>           entityType, 
        E                  newEntity)
    {
        throw 
            new IllegalStateException(
                "Cannot call insertNew(E) from state: " + getName() + "." );
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
        throw 
            new IllegalStateException(
                "Cannot call updateExisting(E) from state: " + 
                getName() + "." );
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
        throw 
            new IllegalStateException(
                "Cannot call removeExisting(E) from state: " + 
                getName() + "." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> E 
    getExisting(AbstractUnitOfWork context,Class<E> type,K key)
    {
        throw 
            new IllegalStateException(
                "Cannot call getExisting(Class<E>,K) from " + 
                "state: " + getName() + "." );
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
        throw 
            new IllegalStateException(
                "Cannot call getNamedQuery(Class<E>,String) " + 
                "from state: " + getName() + "." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <K extends Serializable,E> boolean 
    hasExisting(AbstractUnitOfWork context,Class<E> type, K key)
    {
        throw 
            new IllegalStateException(
                "Cannot call hasExisting(Class<E>,K) from " + 
                "state: " + getName() + "." );
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
        throw 
            new IllegalStateException(
                "Cannot call hasNamedQuery(Class<E>,String) " + 
                "from state: " + getName() + "." );
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
    public void 
    commit(AbstractUnitOfWork context) 
        throws CommitFailedException
    {
        throw 
            new IllegalStateException(
                "Cannot call commit() from state: " + getName() + "." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    rollback(AbstractUnitOfWork context)
        throws RollbackFailedException
    {
        throw 
            new IllegalStateException(
                "Cannot call rollback() from state: " + getName() + "." );
    }

}

// ##########################################################################
