// ##########################################################################
// # File Name:	IChangeSet.java
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

package strata.domain.inmemory;

import java.util.Map;

/****************************************************************************
 * 
 * @param <K> key type
 * @param <T> entity type
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IChangeSet<K,T>
{
	/************************************************************************
	 *  
	 *
	 * @param key
	 * @param object
	 */
	public void
	addToInserted(K key,T object);
	
	/************************************************************************
	 *  
	 *
	 * @param key
	 * @param object
	 */
	public void
	addToUpdated(K key,T object);
	
	/************************************************************************
	 *  
	 *
	 * @param key
	 * @param object
	 */
	public void
	addToRemoved(K key,T object);
	
	/************************************************************************
	 *  
	 *
	 * @param storage
	 */
	public void
	applyChanges(Map<K,T> storage);
	
	/************************************************************************
	 *  
	 *
	 */
	public void
	discardChanges();
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public boolean
	hasChanges();
}


// ##########################################################################
