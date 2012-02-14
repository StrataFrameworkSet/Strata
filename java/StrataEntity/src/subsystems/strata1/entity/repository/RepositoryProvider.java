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

package strata1.entity.repository;

import strata1.common.annotation.*;

/**
 * The generic interface of all repositories. Participates in the 
 * <a href="{@docRoot}/designpatterns/DomainObjectManager.pdf">Repository 
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
interface RepositoryProvider<K,T> 
{
	/************************************************************************
	 * Returns the <code>RepositoryContext</code> associated with this
	 * <code>RepositoryImp</code>. 
	 *
	 * @return	repository context
	 */
	public RepositoryContext
	getContext();
	
	/************************************************************************
	 *  
	 *
	 * @param object
	 */
	public void
	insertNew(T object);
	
	/************************************************************************
	 *  
	 *
	 * @param object
	 */
	public void
	updateExisting(T object);
	
	/************************************************************************
	 *  
	 *
	 * @param object
	 */
	public void
	removeExisting(T object);
	
	/************************************************************************
	 *  
	 *
	 * @param finder
	 */
	@Precondition("this.hasFinder(finder.getName()) == false")
	@Postcondition({
	    "this.hasFinder(finder.getName()) == true",
	    "this.getFinder(finder.getName()) == finder"})
	public void
	insertFinder(Finder<T> finder);
	
	/************************************************************************
	 *  
	 *
	 * @param finderName
	 */
    @Precondition("this.hasFinder(finder.getName()) == true")
    @Postcondition("this.hasFinder(finder.getName()) == false")
	public void
	removeFinder(String finderName);
	
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
	public Finder<T>
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
