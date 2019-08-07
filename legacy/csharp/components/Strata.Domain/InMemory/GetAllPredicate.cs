//  ##########################################################################
//  # File Name: GetAllPredicate.cs
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
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public  
    class GetAllPredicate<T>:
        IPredicate<T>
    {
        public 
        GetAllPredicate() {}

        public bool  
        Evaluate(T target, IDictionary<String,Object> inputs)
        {
 	        return true;
        }
    }
}

//  ##########################################################################
