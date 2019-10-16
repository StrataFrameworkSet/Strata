//  ##########################################################################
//  # File Name: MultiMap.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Windows.Input;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class MultiMap<K,V>:
        IMultiMap<K,V>
    {
        private IDictionary<K,IList<V>> imp;

        public
        MultiMap()
        {
            imp = new ConcurrentDictionary<K,IList<V>>();
        }

        public IMultiMap<K,V> 
        Insert(K key,V value)
        {        
            if ( !imp.ContainsKey( key ))
                imp.Add( key,new List<V>() );
        
            imp[key].Add( value );
            return this;
        }

        public IMultiMap<K,V> 
        Remove(K key)
        {
            if ( imp.ContainsKey( key ))
                imp.Remove( key );
        
            return this;
        }

        public IMultiMap<K,V> 
        Remove(K key,V value)
        {
            if ( imp.ContainsKey( key ))
                imp[key].Remove( value );
        
            return this;
        }

        public IList<V> 
        Get(K key)
        {
            return imp[key];
        }

        public V 
        Get(K key,int index)
        {
            return imp[key][index];
        }

        public int 
        GetCardinality(K key)
        {
            return imp[key].Count;
        }

        public ICollection<K> 
        GetKeys()
        {
            return imp.Keys;
        }

        public ICollection<IList<V>> 
        GetValues()
        {
            return imp.Values;
        }

        public bool 
        ContainsKey(K key)
        {
            return imp.ContainsKey( key );
        }

        public bool 
        ContainsValue(K key,V value)
        {
            return 
                imp.ContainsKey( key ) &&
                imp[key].Contains( value );
        }
    }
}

//  ##########################################################################
