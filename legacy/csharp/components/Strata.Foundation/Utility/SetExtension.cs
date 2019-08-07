//  ##########################################################################
//  # File Name: SetExtension.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Foundation.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public static
    class SetExtension
    {
        public static void
        ForEach<T>(this ISet<T> extendee,Action<T> action)
        {
            foreach (T element in extendee)
                action.Invoke(element);
        }
    }
}

//  ##########################################################################
