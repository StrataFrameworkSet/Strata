//  ##########################################################################
//  # File Name: IEfRepositoryContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Persistence.Repository;

namespace Strata.EfPersistence.EfRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IEfRepositoryContext:
        IRepositoryContext
    {
        void
        InsertFinder<T>(EfFinder<T> finder)
            where T:class;

        IList<EfFinder<T>>
        GetFinders<T>()
            where T:class;
    }
}

//  ##########################################################################
