//  ##########################################################################
//  # File Name: IRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.Shared;
using Strata.Domain.Core.UnitOfWork;

namespace Strata.Domain.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an abstraction of repository implementations and includes
    /// operations for inserting, updating, removing, and getting entities.
    /// </summary>
    /// 
    /// <typeparam name="K">Key Type</typeparam>
    /// <typeparam name="S">Entity/Domain Event Source Type</typeparam>
    /// <typeparam name="E">Domain Event Type</typeparam>
    /// <typeparam name="O">Domain Event Observer Type</typeparam>
    ///  
    public
    interface IRepository<in K,S,E,O>
        where S: class,IDomainEventSource<S,E,O>
        where E: IDomainEvent<S>
        where O: IDomainEventObserver<E>

    {
        IUnitOfWorkProvider Provider { get; }
        ISet<O>             Observers { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Inserts an entity into the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        S
        Insert(S entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Updates an entity in the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="entity"></param>
        /// <returns>the updated entity</returns>
        /// 
        S
        Update(S entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an entity from the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="entity"></param>
        /// 
        void
        Remove(S entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity associated with the specified key. 
        /// </summary>
        /// 
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        S
        GetUnique(K key);

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
        S
        GetUnique(Expression<Func<S,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        IList<S>
        GetAll(Expression<Func<S,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        IList<S> 
        GetAll();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="queryName">query's name</param>
        /// <returns>finder or null</returns>
        /// 
        INamedQuery<S> 
        GetNamedQuery(string queryName);
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="key"></param>
        /// 
        bool
        HasUnique(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        /// 
        bool
        HasAny(Expression<Func<S,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="queryName">query's name</param>
        /// <returns>true if query exists, false otherwise</returns>
        /// 
        bool 
        HasNamedQuery(string queryName);
    }
}

//  ##########################################################################
