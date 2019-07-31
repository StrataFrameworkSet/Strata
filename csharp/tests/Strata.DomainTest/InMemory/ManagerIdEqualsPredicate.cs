//  ##########################################################################
//  # File Name: ManagerIdEqualsPredicate.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Domain.TradeDomain;

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
