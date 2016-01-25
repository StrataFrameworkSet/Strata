//  ##########################################################################
//  # File Name: GetAllPredicate.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
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
