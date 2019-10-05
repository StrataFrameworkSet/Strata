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

package strata.domain.core.repository;

import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

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
        Class<K> keyType,
        Class<E> entityType,
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
    public CompletionStage<E>
    insert(E newEntity)
    {
        return
            getUnitOfWork()
                .thenCompose(
                    unitOfWork ->
                        unitOfWork.insertNew(
                            getKeyType(),
                            getEntityType(),
                            newEntity));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<E>
    update(E existingEntity)
    {
        return
            getUnitOfWork()
                .thenCompose(
                    unitOfWork ->
                        unitOfWork.updateExisting(
                            getKeyType(),
                            getEntityType(),
                            existingEntity));
    }

    /************************************************************************
     * {@inheritDoc}
     * @return
     */
    @Override
    public CompletionStage<E>
    remove(E existingEntity)
    {
        return
            getUnitOfWork()
                .thenCompose(
                    unitOfWork ->
                        unitOfWork.removeExisting(
                            getKeyType(),
                            getEntityType(),
                            existingEntity));
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
    public CompletionStage<Optional<E>>
    getUnique(K key)
    {
        return
            getUnitOfWork()
                .thenCompose(
                    unitOfWork ->
                        unitOfWork.getExisting( getEntityType(),key));
    }

    /************************************************************************
     * {@inheritDoc}
     * @return
     */
    @Override
    public CompletionStage<INamedQuery<E>>
    getNamedQuery(String queryName)
    {
        return
            getUnitOfWork()
                .thenCompose(
                    unitOfWork ->
                        unitOfWork.getNamedQuery(getEntityType(),queryName));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasUnique(K key)
    {
        return
            getUnitOfWork()
                .thenCompose(
                    unitOfWork ->
                        unitOfWork.hasExisting(getEntityType(),key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasNamedQuery(String queryName)
    {
        return
            getUnitOfWork()
                .thenCompose(
                    unitOfWork ->
                        unitOfWork.hasNamedQuery(getEntityType(),queryName));
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected CompletionStage<? extends IUnitOfWork>
    getUnitOfWork()
    {
        return getProvider().getUnitOfWork();
    }
}

// ##########################################################################
