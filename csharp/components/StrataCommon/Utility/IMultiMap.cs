//  ##########################################################################
//  # File Name: IMultiMap.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Windows.Input;

namespace Strata.Common.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A map or dictionary abstract data type in which more than one value 
    /// may be associated with and returned for a given key.
    /// </summary>
    ///  
    public 
    interface IMultiMap<K,V>
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Inserts a new key -> value mapping into the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <param name="key">key part of the mapping</param>
        /// <param name="value">value part of the mapping</param>
        /// <returns>
        /// this <c>IMultiMap</c> to enable method chaining
        /// </returns>
        /// 
        IMultiMap<K,V>
        Insert(K key,V value);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes all key -> value mappings for the
        /// specified key from the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <param name="key">key part of the mapping</param>
        /// <returns>
        /// this <c>IMultiMap</c> to enable method chaining
        /// </returns>
        /// 
        IMultiMap<K,V>
        Remove(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes a key -> value mapping from the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <param name="key">key part of the mapping</param>
        /// <param name="value">value part of the mapping</param>
        /// <returns>
        /// this <c>IMultiMap</c> to enable method chaining
        /// </returns>
        /// 
        IMultiMap<K,V>
        Remove(K key,V value);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns all values for the specified key 
        /// from the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <param name="key">key part of the mapping</param>
        /// <returns>all values mapped to key</returns>
        /// 
        IList<V>
        Get(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the value for the specified key 
        /// and itsIndex from the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <param name="key">key part of the mapping</param>
        /// <param name="index">itsIndex of specific value</param>
        /// <returns>value mapped to key and itsIndex</returns>
        /// 
        V
        Get(K key,int index);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the cardinality (count) of the values for  
        /// the specified key from the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <param name="key">key part of the mapping</param>
        /// <returns>count of values mapped to key</returns>
        /// 
        int
        GetCardinality(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the collection of keys from the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <returns>collection of keys</returns>
        /// 
        ICollection<K>
        GetKeys();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the collection of values from the <c>IMultiMap</c>.
        /// </summary>
        /// 
        /// <returns>collection of values</returns>
        /// 
        ICollection<IList<V>>
        GetValues();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the <c>IMultiMap</c> contains the specified key.
        /// </summary>
        /// 
        /// <param name="key">a key</param>
        /// <returns>true if <c>IMultiMap</c> contains key</returns>
        /// 
        bool
        ContainsKey(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the <c>IMultiMap</c> contains the 
        /// specified key and value.
        /// </summary>
        /// 
        /// <param name="key">key part of mapping</param>
        /// <param name="value">value part of mapping</param>
        /// <returns>
        /// true if <c>IMultiMap</c> contains key and value
        /// </returns>
        /// 
        bool
        ContainsValue(K key,V value);
    }
}

//  ##########################################################################
