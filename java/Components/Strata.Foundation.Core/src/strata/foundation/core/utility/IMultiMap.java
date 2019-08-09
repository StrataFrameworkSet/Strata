// ##########################################################################
// # File Name:	IMultiMap.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.core.utility;

import java.util.Collection;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IMultiMap<K,V>
{
    IMultiMap<K,V>
    put(K key,V value);
    
    IMultiMap<K,V>
    remove(K key);
    
    IMultiMap<K,V>
    remove(K key,V value);
    
    Collection<V>
    get(K key);
    
    V
    get(K key,int index);
    
    int
    getCardinality(K key);
    
    Collection<K>
    getKeys();
    
    Collection<List<V>>
    getValues();
    
    boolean
    containsKey(K key);
    
    boolean
    containsValue(K key,V value);
}

// ##########################################################################
