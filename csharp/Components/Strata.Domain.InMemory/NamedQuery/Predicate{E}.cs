//  ##########################################################################
//  # File Name: EntityPredicate{E}.cs
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Domain.InMemory.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class EntityPredicate<E>:
        IPredicate<E>
    {
        private readonly Func<E,IDictionary<string,object>,bool> itsImp;

        public 
        EntityPredicate(Func<E,IDictionary<string,object>,bool> imp)
        {
            itsImp = imp;
        }

        public bool 
        Evaluate(E target,IDictionary<string,object> inputs)
        {
            return itsImp.Invoke(target,inputs);
        }
    }
}

//  ##########################################################################
