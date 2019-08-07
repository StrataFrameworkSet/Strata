//  ##########################################################################
//  # File Name: IUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using Strata.Domain.NamedQuery;

namespace Strata.Domain.UnitOfWork
{
    public delegate void RollbackAction();

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an abstraction for grouping operations into a unit-of-work
    /// (i.e. a transaction).
    /// </summary>
    /// 
    public
    interface IUnitOfWork:
        IDisposable
    {
        IUnitOfWorkProvider Provider { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Inserts an entity into the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        T
        Insert<K,T>(T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Updates an entity in the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="entity"></param>
        /// <returns>the updated entity</returns>
        /// 
        T
        Update<K,T>(T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an entity from the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="entity"></param>
        /// 
        void
        Remove<K,T>(T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity associated with the specified key. 
        /// </summary>
        /// 
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        T
        GetUniqueWithKey<K,T>(K key)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity that matches the specified predicate. 
        /// </summary>
        /// 
        /// <param name="predicate">
        /// a predicate that matches a unique entity
        /// </param>
        /// <returns>the uniquely matching entity or null</returns>
        /// 
        T
        GetUniqueWithPredicate<T>(Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        IList<T>
        GetAllWithPredicate<T>(Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        IList<T>
        GetAll<T>()
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="queryName">query's name</param>
        /// <returns>query or null</returns>
        /// 
        INamedQuery<T> 
        GetNamedQuery<T>(string queryName)
            where T: class;
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="key"></param>
        /// 
        bool
        HasUniqueWithKey<K,T>(K key)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        /// 
        bool
        HasUniqueWithPredicate<T>(Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="queryName">query's name</param>
        /// <returns>true if query exists, false otherwise</returns>
        /// 
        bool 
        HasNamedQuery<T>(string queryName)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Pushes a rollback action onto the unit of works internal stack.
        /// </summary>
        /// 
        /// <returns>true if active, false otherwise</returns>
        /// 
        void
        PushRollbackAction(RollbackAction rollbackAction);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is active.
        /// </summary>
        /// 
        /// <returns>true if active, false otherwise</returns>
        /// 
        bool
        IsActive();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work has failed.
        /// </summary>
        /// 
        /// <returns>true if failed, false otherwise</returns>
        /// 
        bool 
        IsFailed();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is committed.
        /// </summary>
        /// 
        /// <returns>true if committed, false otherwise</returns>
        /// 
        bool 
        IsCommitted();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is rolled back.
        /// </summary>
        /// 
        /// <returns>true if rolled back, false otherwise</returns>
        /// 
        bool 
        IsRolledBack();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Commits the current unit of work to the underlying storage.
        /// </summary>
        /// 
        /// <exception cref="CommitFailedException">
        /// The unit of work failed to commit. 
        /// </exception>
        /// 
        void
        Commit();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Rolls back the current unit of work in the event of an error.
        /// </summary>
        /// 
        /// <exception cref="RollbackFailedException">
        /// The unit of work failed to roll back. 
        /// </exception>
        /// 
        void
        Rollback();
    }
}

//  ##########################################################################
