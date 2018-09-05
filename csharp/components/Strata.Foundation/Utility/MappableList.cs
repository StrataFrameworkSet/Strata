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
        public virtual int Count { get { return itsImp.Count; } }
        public virtual bool IsReadOnly { get { return itsImp.IsReadOnly; } }

        public virtual T
        this[int index]
        {
            get { return itsImp[index]; }
            set { itsImp[index] = value; }
        }

        public virtual string
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

        public virtual int 
        IndexOf(T item)
        {
            return itsImp.IndexOf(item);
        }

        public virtual void
        Insert(int index,T item)
        {
            itsImp.Insert(index,item);
        }

        public virtual void
        RemoveAt(int index)
        {
            itsImp.RemoveAt(index);
        }


        public virtual void
        Add(T item)
        {
            itsImp.Add(item);
        }

        public virtual void
        Clear()
        {
            itsImp.Clear();
        }

        public virtual bool
        Contains(T item)
        {
            return itsImp.Contains(item);
        }

        public virtual void
        CopyTo(T[] array,int arrayIndex)
        {
            itsImp.CopyTo(array,arrayIndex);
        }

        public virtual bool
        Remove(T item)
        {
            return itsImp.Remove(item);
        }

        public virtual IEnumerator<T> 
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
