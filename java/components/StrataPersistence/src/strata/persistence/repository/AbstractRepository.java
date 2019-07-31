// ##########################################################################
// # File Name:	AbstractRepository.java
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

package strata.persistence.repository;

import java.io.Serializable;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.unitofwork.IUnitOfWork;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractRepository<K extends Serializable,E>
    implements IRepository<K,E>
{
    private final Class<K>            itsKeyType;
    private final Class<E>            itsEntityType;
    private final IUnitOfWorkProvider itsProvider;
    
    /************************************************************************
     * Creates a new {@code AbstractRepository}. 
     *
     */
    protected
    AbstractRepository(
        Class<K>            keyType,
        Class<E>            entityType,
        IUnitOfWorkProvider provider)
    {
        itsKeyType    = keyType;
        itsEntityType = entityType;
        itsProvider   = provider;   
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public E 
    insertNew(E newEntity) 
        throws InsertFailedException
    {
        try
        {
            return 
                getUnitOfWork()
                    .insertNew( getKeyType(),getEntityType(),newEntity );
        }
        catch (Exception e)
        {
            throw new InsertFailedException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public E 
    updateExisting(E existingEntity) 
        throws UpdateFailedException
    {
        try
        {
            return 
                getUnitOfWork()
                    .updateExisting( 
                        getKeyType(),
                        getEntityType(),
                        existingEntity );
        }
        catch (Exception e)
        {
            throw new UpdateFailedException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    removeExisting(E existingEntity) 
        throws RemoveFailedException
    {
        try
        {
            getUnitOfWork()
                .removeExisting( 
                    getKeyType(),
                    getEntityType(),
                    existingEntity );
        }
        catch (Exception e)
        {
            throw new RemoveFailedException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<K> 
    getKeyType()
    {
        return itsKeyType;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<E> 
    getEntityType()
    {
        return itsEntityType;
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
    public E 
    getExisting(K key)
    {
        return getUnitOfWork().getExisting( getEntityType(),key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public INamedQuery<E> 
    getNamedQuery(String queryName)
    {
        return getUnitOfWork().getNamedQuery( getEntityType(),queryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasExisting(K key)
    {
        return getUnitOfWork().hasExisting( getEntityType(),key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasNamedQuery(String queryName)
    {
        return getUnitOfWork().hasNamedQuery( getEntityType(),queryName );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected IUnitOfWork
    getUnitOfWork()
    {
        return getProvider().getUnitOfWork();
    }
}

// ##########################################################################
