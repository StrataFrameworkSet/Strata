// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.persistence.repository;


/**
 * The generic interface of all repositories. Participates in the 
 * <a href="{@docRoot}/designpatterns/DomainObjectManager.pdf">IRepository 
 * (aka Domain Object Manager)</a> design pattern.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 * @param <K>
 * @param <T>
 */
public 
interface IRepositoryProvider<K,T> 
{
	/************************************************************************
	 * Returns the <code>IRepositoryContext</code> associated with this
	 * <code>RepositoryImp</code>. 
	 *
	 * @return	repository context
	 */
	public IRepositoryContext
	getContext();
	
	/************************************************************************
	 *  
	 *
	 * @param object
	 * @return TODO
	 */
	public T
	insertNew(T object)
	    throws InsertFailedException;
	
	/************************************************************************
	 *  
	 *
	 * @param object
	 * @return TODO
	 */
	public T
	updateExisting(T object)
	    throws UpdateFailedException;
	
	/************************************************************************
	 *  
	 *
	 * @param object
	 */
	public void
	removeExisting(T object)
	    throws RemoveFailedException;
	
	/************************************************************************
	 *  
	 *
	 * @param key
	 * @return
	 */
	public T
	getExisting(K key);
	
	/************************************************************************
	 *  
	 *
	 * @param finderName
	 * @return
	 */
	public IFinder<T>
	getFinder(String finderName);
	
	/************************************************************************
	 *  
	 *
	 * @param key
	 * @return
	 */
	public boolean
	hasExisting(K key);

	/************************************************************************
	 *  
	 *
	 * @param finderName
	 * @return
	 */
	public boolean
	hasFinder(String finderName);
}


// ##########################################################################
