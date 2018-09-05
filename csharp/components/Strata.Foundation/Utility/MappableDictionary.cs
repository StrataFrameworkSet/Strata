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
        public virtual int Count { get { return itsImp.Count; } }
        public virtual bool IsReadOnly { get { return itsImp.IsReadOnly; } }
        public virtual ICollection<K> Keys { get { return itsImp.Keys; } }
        public virtual ICollection<T> Values { get { return itsImp.Values; } }

        public virtual T
        this[K key]
        {
            get { return itsImp[key]; }
            set { itsImp[key] = value; }
        }

        public virtual string
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

        public virtual bool
        ContainsKey(K key)
        {
            return itsImp.ContainsKey(key);
        }

        public virtual void
        Add(K key,T value)
        {
            itsImp.Add(key,value);
        }

        public virtual bool
        Remove(K key)
        {
            return itsImp.Remove(key);
        }

        public virtual bool
        TryGetValue(K key,out T value)
        {
            return itsImp.TryGetValue(key,out value);
        }


        public virtual void
        Add(KeyValuePair<K,T> item)
        {
            itsImp.Add(item);
        }

        public virtual void
        Clear()
        {
            itsImp.Clear();
        }

        public virtual bool
        Contains(KeyValuePair<K,T> item)
        {
            return itsImp.Contains(item);
        }

        public virtual void
        CopyTo(KeyValuePair<K,T>[] array,int arrayIndex)
        {
            itsImp.CopyTo(array,arrayIndex);
        }

        public virtual bool
        Remove(KeyValuePair<K,T> item)
        {
            return itsImp.Remove(item);
        }

        public virtual IEnumerator<KeyValuePair<K,T>> 
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
