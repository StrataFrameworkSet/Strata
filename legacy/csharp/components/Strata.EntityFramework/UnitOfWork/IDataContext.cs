//  ##########################################################################
//  # File Name: IDataContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Data.Entity.Infrastructure;
using System.Data.Entity;

namespace Strata.EntityFramework.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IDataContext: 
        IDisposable
    {
        DbChangeTracker ChangeTracker { get; }
        Database        Database { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        int 
        SaveChanges();
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        IDbSet<T> 
        GetDbSet<T>() where T : class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        DbEntityEntry<TEntity> 
        Entry<TEntity>(TEntity entity) where TEntity : class;
    }
}

//  ##########################################################################
