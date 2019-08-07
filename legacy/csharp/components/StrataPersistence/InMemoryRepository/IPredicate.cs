//  ##########################################################################
//  # File Name: IPredicate.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.InMemoryRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IPredicate<T>
    {
        bool
        Evaluate(T target,IDictionary<String,Object> inputs);
    }
}

//  ##########################################################################
