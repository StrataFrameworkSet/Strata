//  ##########################################################################
//  # File Name: DictionaryExtension.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using System.Collections;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public static 
    class DictionaryExtension
    {
        public static IDictionary<K,T>
        Merge<K,T>(this IDictionary<K,T> x,IDictionary<K,T> y)
        {
            IDictionary<K, T> merged = new Dictionary<K,T>(x);

            foreach (KeyValuePair<K,T> entry in y)
                merged[entry.Key] = entry.Value;

            return merged;
        }

        public static IDictionary<K,T> 
        Filter<K,T>(this IDictionary<K,T> x,IDictionary<K,T> y)
        {
            IDictionary<K, T> filtered = new Dictionary<K,T>(x);

            foreach (KeyValuePair<K,T> entry in y)
                if ( filtered.ContainsKey( entry.Key ) )
                    filtered.Remove( entry.Key );

            return filtered;
        }


    }
}

//  ##########################################################################
