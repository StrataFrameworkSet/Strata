//  ##########################################################################
//  # File Name: ManagerIdEqualsPredicate.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Persistence.TradeDomain;

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
    class ManagerIdEqualsPredicate:
        IPredicate<Trade>
    {
        public bool 
        Evaluate(Trade target,IDictionary<String,Object> inputs)
        {
            int managerId = (int)inputs["id"];    

            return 
                target
                    .AccountAllocations
                    .Any(
                        a => a.ManagerAllocations.Any(
                            m => m.ManagerId == managerId));
        }
    }
}

//  ##########################################################################
