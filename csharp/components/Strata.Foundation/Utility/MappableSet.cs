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
        public virtual int Count { get { return itsImp.Count; } }
        public virtual bool IsReadOnly { get { return itsImp.IsReadOnly; } }

        public virtual string Contents
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

        public virtual bool
        Add(T item)
        {
            return itsImp.Add(item);
        }

        public virtual void
        UnionWith(IEnumerable<T> other)
        {
            itsImp.UnionWith(other);
        }

        public virtual void
        IntersectWith(IEnumerable<T> other)
        {
            itsImp.IntersectWith(other);
        }

        public virtual void
        ExceptWith(IEnumerable<T> other)
        {
            itsImp.ExceptWith(other);
        }

        public virtual void
        SymmetricExceptWith(IEnumerable<T> other)
        {
            itsImp.SymmetricExceptWith(other);
        }

        public virtual bool
        IsSubsetOf(IEnumerable<T> other)
        {
            return itsImp.IsSubsetOf(other);
        }

        public virtual bool
        IsSupersetOf(IEnumerable<T> other)
        {
            return itsImp.IsSupersetOf(other);
        }

        public virtual bool
        IsProperSupersetOf(IEnumerable<T> other)
        {
            return itsImp.IsProperSupersetOf(other);
        }

        public virtual bool
        IsProperSubsetOf(IEnumerable<T> other)
        {
            return itsImp.IsProperSubsetOf(other);
        }

        public virtual bool
        Overlaps(IEnumerable<T> other)
        {
            return itsImp.Overlaps(other);
        }

        public virtual bool
        SetEquals(IEnumerable<T> other)
        {
            return itsImp.SetEquals(other);
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
