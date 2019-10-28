//  ##########################################################################
//  # File Name: IUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Core.NamedQuery;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.UnitOfWork
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
        /// <param name="context">context for the operation</param>
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        Task<E> 
        Insert<K,E>(AbstractUnitOfWork context,E entity)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Updates an entity in the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="entity"></param>
        /// <returns>the updated entity</returns>
        /// 
        Task<E> 
        Update<K,E>(AbstractUnitOfWork context,E entity)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an entity from the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="entity"></param>
        Task
        Remove<K,E>(AbstractUnitOfWork context,E entity)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity associated with the specified key. 
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        Task<Optional<E>> 
        GetUnique<K,E>(AbstractUnitOfWork context,K key)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity that matches the specified predicate. 
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="predicate">
        ///     a predicate that matches a unique entity
        /// </param>
        /// <returns>the uniquely matching entity or null</returns>
        /// 
        Task<Optional<E>> 
        GetUniqueMatching<E>(
            AbstractUnitOfWork       context,
            Expression<Func<E,bool>> predicate)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="predicate"></param>
        /// 
        Task<IList<E>> 
        GetMatching<E>(
            AbstractUnitOfWork       context,
            Expression<Func<E,bool>> predicate)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// 
        Task<IList<E>> 
        GetAll<E>(AbstractUnitOfWork context)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="queryName">query's name</param>
        /// <returns>query or null</returns>
        /// 
        Task<INamedQuery<E>> 
        GetNamedQuery<E>(AbstractUnitOfWork context,string queryName)
            where E: class;
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="key"></param>
        /// 
        Task<bool> 
        HasUnique<K,E>(AbstractUnitOfWork context,K key)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="predicate"></param>
        /// 
        Task<bool> 
        HasUniqueMatching<E>(
            AbstractUnitOfWork       context,
            Expression<Func<E,bool>> predicate)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <param name="queryName">query's name</param>
        /// <returns>true if query exists, false otherwise</returns>
        /// 
        Task<bool> 
        HasNamedQuery<E>(AbstractUnitOfWork context,string queryName)
            where E: class;

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
        /// <param name="context">context for the operation</param>
        /// <exception cref="CommitFailedException">
        /// The unit of work failed to commit. 
        /// </exception>
        /// 
        Task
        Commit(AbstractUnitOfWork context);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Rolls back the current unit of work in the event of an error.
        /// </summary>
        /// <param name="context">context for the operation</param>
        /// <exception cref="RollbackFailedException">
        /// The unit of work failed to roll back. 
        /// </exception>
        /// 
        Task
        Rollback(AbstractUnitOfWork context);
    }
}

//  ##########################################################################
