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

package strata.persistence.repository;

import java.io.Serializable;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

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
     * @throws InsertFailedException
     */
    E
    insertNew(E newEntity)
        throws InsertFailedException;
    
    /************************************************************************
     *  
     *
     * @param existingEntity
     * @return
     * @throws UpdateFailedException
     */
    E
    updateExisting(E existingEntity)
        throws UpdateFailedException;
    
    /************************************************************************
     *  
     *
     * @param existingEntity
     * @throws RemoveFailedException
     */
    void
    removeExisting(E existingEntity)
        throws RemoveFailedException;
    
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
    E
    getExisting(K key);
    
    /************************************************************************
     *  
     *
     * @param queryName
     * @return
     */
    INamedQuery<E>
    getNamedQuery(String queryName);
    
    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    boolean
    hasExisting(K key);
    
    /************************************************************************
     *  
     *
     * @param queryName
     * @return
     */
    boolean
    hasNamedQuery(String queryName);
}

// ##########################################################################
