//  ##########################################################################
//  # File Name: MappableSet.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Mapper;
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
    class MappableSet<T>:
        ISet<T>
    {
        public int    Count { get { return itsImp.Count; } }
        public bool   IsReadOnly { get { return itsImp.IsReadOnly; } }

        public string Contents
        {
            get
            {
                return itsMapper.ToPayload(itsImp);
            }

            set
            {
                itsImp = itsMapper.ToObject<SortedSet<T>>(value);
            }
        }

        private ISet<T>                       itsImp;
        private IObjectMapper<ISet<T>,string> itsMapper;

        public
        MappableSet()
        {
            itsImp = new SortedSet<T>();
            itsMapper = new JsonObjectMapper<ISet<T>>();
        }

        public bool 
        Add(T item)
        {
            return itsImp.Add(item);
        }

        public void 
        UnionWith(IEnumerable<T> other)
        {
            itsImp.UnionWith(other);
        }

        public void 
        IntersectWith(IEnumerable<T> other)
        {
            itsImp.IntersectWith(other);
        }

        public void 
        ExceptWith(IEnumerable<T> other)
        {
            itsImp.ExceptWith(other);
        }

        public void 
        SymmetricExceptWith(IEnumerable<T> other)
        {
            itsImp.SymmetricExceptWith(other);
        }

        public bool 
        IsSubsetOf(IEnumerable<T> other)
        {
            return itsImp.IsSubsetOf(other);
        }

        public bool 
        IsSupersetOf(IEnumerable<T> other)
        {
            return itsImp.IsSupersetOf(other);
        }

        public bool 
        IsProperSupersetOf(IEnumerable<T> other)
        {
            return itsImp.IsProperSupersetOf(other);
        }

        public bool 
        IsProperSubsetOf(IEnumerable<T> other)
        {
            return itsImp.IsProperSubsetOf(other);
        }

        public bool 
        Overlaps(IEnumerable<T> other)
        {
            return itsImp.Overlaps(other);
        }

        public bool 
        SetEquals(IEnumerable<T> other)
        {
            return itsImp.SetEquals(other);
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

        void
        ICollection<T>.Add(T item)
        {
            Add(item);
        }

        IEnumerator 
        IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
    }
}

//  ##########################################################################
