//  ##########################################################################
//  # File Name: InMemoryRepositoryMethod.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Utility;
using Strata.Persistence.Repository;

namespace Strata.Persistence.InMemoryRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class InMemoryRepositoryMethod:
        AbstractRepositoryMethod
    {
        public InMemoryRepositoryContext Context { get; protected set; }

        protected 
        InMemoryRepositoryMethod(String name,InMemoryRepositoryContext cntxt):
            base( name )
        {
            Context = cntxt;
        }

        protected
        InMemoryRepositoryMethod(InMemoryRepositoryMethod other):
            base( other )
        {
            Context = other.Context;
        }

        public override void 
        Clear() {}
    }
}

//  ##########################################################################
