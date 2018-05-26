//  ##########################################################################
//  # File Name: MappableDictionary.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Mapper;
using System;
using System.Collections;
using System.Collections.Generic;

namespace Strata.Foundation.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class MappableDictionary<K,T>:
        IDictionary<K,T>
    {
        public int            Count { get { return itsImp.Count; } }
        public bool           IsReadOnly { get { return itsImp.IsReadOnly; } }
        public ICollection<K> Keys { get { return itsImp.Keys; } }
        public ICollection<T> Values { get { return itsImp.Values; } }

        public T
        this[K key]
        {
            get { return itsImp[key]; }
            set { itsImp[key] = value; }
        }

        public string
        Contents
        {
            get { return itsMapper.ToPayload(itsImp); }
            set { itsImp = itsMapper.ToObject<Dictionary<K,T>>(value); }
        }

        private IDictionary<K,T>                       itsImp;
        private IObjectMapper<IDictionary<K,T>,string> itsMapper;

        public
        MappableDictionary()
        {
            itsImp = new Dictionary<K,T>();
            itsMapper = new JsonObjectMapper<IDictionary<K,T>>();
        }

        public bool 
        ContainsKey(K key)
        {
            return itsImp.ContainsKey(key);
        }

        public void 
        Add(K key,T value)
        {
            itsImp.Add(key,value);
        }

        public bool 
        Remove(K key)
        {
            return itsImp.Remove(key);
        }

        public bool 
        TryGetValue(K key,out T value)
        {
            return itsImp.TryGetValue(key,out value);
        }


        public void 
        Add(KeyValuePair<K,T> item)
        {
            itsImp.Add(item);
        }

        public void 
        Clear()
        {
            itsImp.Clear();
        }

        public bool 
        Contains(KeyValuePair<K,T> item)
        {
            return itsImp.Contains(item);
        }

        public void 
        CopyTo(KeyValuePair<K,T>[] array,int arrayIndex)
        {
            itsImp.CopyTo(array,arrayIndex);
        }

        public bool 
        Remove(KeyValuePair<K,T> item)
        {
            return itsImp.Remove(item);
        }

        public IEnumerator<KeyValuePair<K,T>> 
        GetEnumerator()
        {
            return itsImp.GetEnumerator();
        }

        IEnumerator 
        IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
    }
}

//  ##########################################################################
