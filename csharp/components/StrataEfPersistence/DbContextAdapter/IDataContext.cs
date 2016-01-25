//  ##########################################################################
//  # File Name: IDataContext.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Data.Entity.Infrastructure;
using System.Data.Entity;

namespace Strata.EfPersistence.DbContextAdapter
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>MCDM</author>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
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
