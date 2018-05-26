//  ##########################################################################
//  # File Name: IEfUnitOfWorkProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using Strata.Domain.NamedQuery;
using Strata.Domain.UnitOfWork;

namespace Strata.EntityFramework.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IEfUnitOfWorkProvider:
        IUnitOfWorkProvider
    {
        IEfUnitOfWorkProvider
        InsertNamedQuery<T>(string queryName,string sql)
            where T:class;

        INamedQuery<T>
        GetNamedQuery<T>(string queryName)
            where T:class;

        bool
        HasNamedQuery<T>(string queryName)
            where T: class;
    }
}

//  ##########################################################################
