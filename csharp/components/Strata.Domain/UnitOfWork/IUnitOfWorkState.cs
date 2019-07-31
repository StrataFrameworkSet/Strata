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

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an abstraction for grouping operations into a unit-of-work
    /// (i.e. a transaction).
    /// </summary>
    /// 
    public
    interface IUnitOfWorkState:
        IDisposable
    {
        string Name { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Inserts an entity into the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        T
        Insert<K,T>(AbstractUnitOfWork context,T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Updates an entity in the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="entity"></param>
        /// <returns>the updated entity</returns>
        /// 
        T
        Update<K,T>(AbstractUnitOfWork context,T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an entity from the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="entity"></param>
        /// 
        void
        Remove<K,T>(AbstractUnitOfWork context,T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity associated with the specified key. 
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        T
        GetUniqueWithKey<K,T>(AbstractUnitOfWork context,K key)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity that matches the specified predicate. 
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="predicate">
        /// a predicate that matches a unique entity
        /// </param>
        /// <returns>the uniquely matching entity or null</returns>
        /// 
        T
        GetUniqueWithPredicate<T>(
            AbstractUnitOfWork       context,
            Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="predicate"></param>
        /// 
        IList<T>
        GetAllWithPredicate<T>(
            AbstractUnitOfWork       context,
            Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// 
        IList<T>
        GetAll<T>(AbstractUnitOfWork context)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="queryName">query's name</param>
        /// <returns>query or null</returns>
        /// 
        INamedQuery<T> 
        GetNamedQuery<T>(AbstractUnitOfWork context,string queryName)
            where T: class;
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="key"></param>
        /// 
        bool
        HasUniqueWithKey<K,T>(AbstractUnitOfWork context,K key)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="predicate"></param>
        /// 
        bool
        HasUniqueWithPredicate<T>(
            AbstractUnitOfWork       context,
            Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <param name="queryName">query's name</param>
        /// <returns>true if query exists, false otherwise</returns>
        /// 
        bool 
        HasNamedQuery<T>(AbstractUnitOfWork context,string queryName)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is active.
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <returns>true if active, false otherwise</returns>
        /// 
        bool 
        IsActive(AbstractUnitOfWork context);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is failed.
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <returns>true if failed, false otherwise</returns>
        /// 
        bool 
        IsFailed(AbstractUnitOfWork context);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is committed.
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <returns>true if committed, false otherwise</returns>
        /// 
        bool 
        IsCommitted(AbstractUnitOfWork context);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is rolled back.
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// <returns>true if rolled back, false otherwise</returns>
        /// 
        bool 
        IsRolledBack(AbstractUnitOfWork context);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Commits the current unit of work to the underlying storage.
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// 
        /// <exception cref="CommitFailedException">
        /// The unit of work failed to commit. 
        /// </exception>
        /// 
        void
        Commit(AbstractUnitOfWork context);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Rolls back the current unit of work in the event of an error.
        /// </summary>
        /// 
        /// <param name="context">context for the operation</param>
        /// 
        /// <exception cref="RollbackFailedException">
        /// The unit of work failed to roll back. 
        /// </exception>
        /// 
        void
        Rollback(AbstractUnitOfWork context);
    }
}

//  ##########################################################################
