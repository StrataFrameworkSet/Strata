//  ##########################################################################
//  # File Name: ListExtension.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Collections;

namespace Strata.Common.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public static 
    class ListExtension
    {

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
