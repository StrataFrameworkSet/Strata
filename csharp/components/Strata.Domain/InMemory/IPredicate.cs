//  ##########################################################################
//  # File Name: IPredicate.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Domain.InMemory
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IPredicate<in T>
    {
        bool
        Evaluate(T target,IDictionary<string,object> inputs);
    }
}

//  ##########################################################################
