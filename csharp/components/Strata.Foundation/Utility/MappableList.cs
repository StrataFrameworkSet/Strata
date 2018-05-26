//  ##########################################################################
//  # File Name: MappableList.cs
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
    class MappableList<T>:
        IList<T>
    {
        public int  Count { get { return itsImp.Count; } }
        public bool IsReadOnly { get { return itsImp.IsReadOnly; } }

        public T    
        this[int index]
        {
            get { return itsImp[index]; }
            set { itsImp[index] = value; }
        }

        public string 
        Contents
        {
            get { return itsMapper.ToPayload(itsImp); }
            set { itsImp = itsMapper.ToObject<List<T>>(value); }
        }

        private IList<T> itsImp;
        private IObjectMapper<IList<T>,string> itsMapper;

        public
        MappableList()
        {
            itsImp = new List<T>();
            itsMapper = new JsonObjectMapper<IList<T>>();
        }

        public int 
        IndexOf(T item)
        {
            return itsImp.IndexOf(item);
        }

        public void 
        Insert(int index,T item)
        {
            itsImp.Insert(index,item);
        }

        public void 
        RemoveAt(int index)
        {
            itsImp.RemoveAt(index);
        }


        public void 
        Add(T item)
        {
            itsImp.Add(item);
        }

        public void 
        Clear()
        {
            itsImp.Clear();
        }

        public bool 
        Contains(T item)
        {
            return itsImp.Contains(item);
        }

        public void 
        CopyTo(T[] array,int arrayIndex)
        {
            itsImp.CopyTo(array,arrayIndex);
        }

        public bool 
        Remove(T item)
        {
            return itsImp.Remove(item);
        }

        public IEnumerator<T> 
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
