//  ##########################################################################
//  # File Name: ListExtension.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
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
    class ListExtension
    {
        public static void
        AddAll<T>(this IList<T> list,ICollection<T> other)
        {
            foreach (T item in other)
                list.Add(item);
        }

        public static void 
        Sort<T>(this IList<T> list)
        {
            ArrayList.Adapter((IList)list).Sort();
        }

        public static void
        Sort<T>(this IList<T> list, Comparison<T> comparison)
        {
            ArrayList.Adapter((IList)list).Sort(new Comparer<T>(comparison));
        }

        class Comparer<T> : IComparer<T>, IComparer
        {
            private readonly Comparison<T> comparison;

            public Comparer(Comparison<T> comparison)
            {
                this.comparison = comparison;
            }

            public int Compare(T x, T y)
            {
                return comparison(x, y);
            }

            public int Compare(object x, object y)
            {
                return comparison((T)x, (T)y);
            }
        }
    }
}

//  ##########################################################################
