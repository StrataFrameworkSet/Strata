// ##########################################################################
// # File Name:	MultiMap.java
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class MultiMap<K,V>
    implements IMultiMap<K,V>
{
    private Map<K,List<V>> itsImp;
    
    /************************************************************************
     * Creates a new {@code MultiMap}. 
     *
     */
    public 
    MultiMap()
    {
        itsImp = new ConcurrentHashMap<K,List<V>>();
    }

    /************************************************************************
     * Creates a new {@code MultiMap}. 
     *
     * @param type
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public
    MultiMap(Class<? extends Map<K,List<V>>> type)
        throws
        InstantiationException,
        IllegalAccessException,
        NoSuchMethodException,
        InvocationTargetException
    {
        itsImp =
            type
                .getDeclaredConstructor()
                .newInstance();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public MultiMap<K,V> 
    put(K key,V value)
    {
        if ( key == null )
            throw new IllegalArgumentException("key is null");
        
        if ( !itsImp.containsKey( key ))
            itsImp.put( key,new ArrayList<V>() );
        
        itsImp.get( key ).add( value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMultiMap<K,V> 
    remove(K key)
    {
        if ( itsImp.containsKey( key ))
            itsImp.remove( key );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMultiMap<K,V> 
    remove(K key,V value)
    {
        if ( itsImp.containsKey( key ))
            itsImp.get( key ).remove( value );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Collection<V>
    get(K key)
    {
        return itsImp.get( key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public V 
    get(K key,int index)
    {
        List<V> values = itsImp.get( key );
        
        return values.get( index );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getCardinality(K key)
    {
        if ( itsImp.containsKey( key ))
            return get( key ).size();
        
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Collection<K>
    getKeys()
    {
        return itsImp.keySet();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Collection<List<V>>
    getValues()
    {
        return itsImp.values();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    containsKey(K key)
    {
        return itsImp.containsKey( key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    containsValue(K key,V value)
    {
        if ( itsImp.containsKey( key ))
            return itsImp.get( key ).contains( value );
        
        return false;
    }

}

// ##########################################################################
