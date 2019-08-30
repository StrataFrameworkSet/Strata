// ##########################################################################
// # File Name:	IUnitOfWorkState.java
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
interface IUnitOfWorkState
{
    /************************************************************************
     *  
     *
     * @return
     */
    String
    getName();
    
    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param newEntity
     * @return
     */
    <K extends Serializable,E> CompletionStage<E>
    insertNew(
        AbstractUnitOfWork context,
        Class<K> keyType,
        Class<E> entityType,
        E newEntity);
    
    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     * @return
     */
    <K extends Serializable,E> CompletionStage<E>
    updateExisting(
        AbstractUnitOfWork context,
        Class<K> keyType,
        Class<E> entityType,
        E existingEntity);
    
    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     */
    <K extends Serializable,E> CompletionStage<Void>
    removeExisting(
        AbstractUnitOfWork context,
        Class<K> keyType,
        Class<E> entityType,
        E existingEntity);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    <K extends Serializable,E> CompletionStage<Optional<E>>
    getExisting(AbstractUnitOfWork context,Class<E> type,K key);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    <E> CompletionStage<Optional<INamedQuery<E>>>
    getNamedQuery(
        AbstractUnitOfWork context,
        Class<E> type,
        String queryName);
    
    /************************************************************************
     *  
     *
     * @param type 
     * @param key
     * @return
     */
    <K extends Serializable,E> CompletionStage<Boolean>
    hasExisting(AbstractUnitOfWork context,Class<E> type,K key);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    <E> CompletionStage<Boolean>
    hasNamedQuery(
        AbstractUnitOfWork context,
        Class<E> type,
        String queryName);

    /************************************************************************
     *  
     *
     * @param context
     * @return
     */
    public boolean
    isActive(AbstractUnitOfWork context);
    
    /************************************************************************
     *  
     *
     * @param context
     * @return
     */
    public boolean
    isFailed(AbstractUnitOfWork context);
    
    /************************************************************************
     *  
     *
     * @param context
     * @return
     */
    public boolean
    isCommitted(AbstractUnitOfWork context);
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    isRolledBack(AbstractUnitOfWork context);

    /************************************************************************
     *  
     *
     * @param context
     */
    public CompletionStage<Void>
    commit(AbstractUnitOfWork context);

    /************************************************************************
     *  
     *
     * @param context
     */
    public CompletionStage<Void>
    rollback(AbstractUnitOfWork context);

}

// ##########################################################################
