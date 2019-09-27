// ##########################################################################
// # File Name:	IUnitOfWork.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The Strata Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The Strata Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.domain.core.unitofwork;

import strata.domain.core.namedquery.INamedQuery;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/****************************************************************************
 * Represents a unit of work for processing data in relation to persistent
 * storage mechanisms.
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IUnitOfWork
    extends AutoCloseable
{
    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param newEntity
     * @return
     */
    <K extends Serializable,E> CompletionStage<E>
    insertNew(Class<K> keyType,Class<E> entityType,E newEntity);
    
    /************************************************************************
     *  
     *
     * @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     * @return
     */
    <K extends Serializable,E> CompletionStage<E>
    updateExisting(Class<K> keyType,Class<E> entityType,E existingEntity);
    
    /************************************************************************
     *  
     *  @param keyType TODO
     * @param entityType TODO
     * @param existingEntity
     * @return
     */
    <K extends Serializable,E> CompletionStage<E>
    removeExisting(Class<K> keyType,Class<E> entityType,E existingEntity);
    
    /************************************************************************
     *  
     *
     * @return
     */
    IUnitOfWorkProvider
    getProvider();
    
    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    <K extends Serializable,E> CompletionStage<Optional<E>>
    getExisting(Class<E> type,K key);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    <E> CompletionStage<INamedQuery<E>>
    getNamedQuery(Class<E> type,String queryName);
    
    /************************************************************************
     *  
     *
     * @param type TODO
     * @param key
     * @return
     */
    <K extends Serializable,E> CompletionStage<Boolean>
    hasExisting(Class<E> type,K key);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    <E> CompletionStage<Boolean>
    hasNamedQuery(Class<E> type,String queryName);

    /************************************************************************
     *
     *
     * @return
     */
    void
    pushRollbackAction(Runnable action);

    /************************************************************************
     *
     *
     * @return
     */
    boolean
    isActive();

    /************************************************************************
     *
     *
     * @return
     */
    boolean
    isFailed();

    /************************************************************************
     *  
     *
     * @return
     */
    boolean
    isCommitted();
    
    /************************************************************************
     *  
     *
     * @return
     */
    boolean
    isRolledback();

    /************************************************************************
     *  
     *
     * @throws CommitFailedException
     */
    CompletionStage<Void>
    commit();

    /************************************************************************
     *  
     *
     * @throws RollbackFailedException
     */
    CompletionStage<Void>
    rollback();
}

// ##########################################################################
