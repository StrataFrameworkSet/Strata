// ##########################################################################
// # File Name:	IRepository.java
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
public 
interface IRepository<K extends Serializable,E>
{
    /************************************************************************
     *  
     *
     * @param newEntity
     * @return
     */
    CompletionStage<E>
    insert(E newEntity);
    
    /************************************************************************
     *  
     *
     * @param existingEntity
     * @return
     */
    CompletionStage<E>
    update(E existingEntity);
    
    /************************************************************************
     *  
     *
     * @param existingEntity
     * @return
     */
    CompletionStage<E>
    remove(E existingEntity);
    
    /************************************************************************
     *  
     *
     * @return
     */
    Class<K>
    getKeyType();
    
    /************************************************************************
     *  
     *
     * @return
     */
    Class<E>
    getEntityType();
    
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
     * @param key
     * @return
     */
    CompletionStage<Optional<E>>
    getUnique(K key);
    
    /************************************************************************
     *  
     *
     * @param queryName
     * @return
     */
    CompletionStage<INamedQuery<E>>
    getNamedQuery(String queryName);
    
    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    CompletionStage<Boolean>
    hasUnique(K key);
    
    /************************************************************************
     *  
     *
     * @param queryName
     * @return
     */
    CompletionStage<Boolean>
    hasNamedQuery(String queryName);
}

// ##########################################################################
