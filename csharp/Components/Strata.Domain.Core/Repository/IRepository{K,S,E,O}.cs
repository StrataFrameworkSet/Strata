//  ##########################################################################
//  # File Name: IRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Domain.Core.DomainEvent;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.UnitOfWork;
using Strata.Foundation.Core.Utility;

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
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        Task<S> 
        Insert(S entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Updates an entity in the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// <param name="entity"></param>
        /// <returns>the updated entity</returns>
        /// 
        Task<S> 
        Update(S entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an entity from the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// <param name="entity"></param>
        Task
        Remove(S entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity associated with the specified key. 
        /// </summary>
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        Task<Optional<S>> 
        GetUnique(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity that matches the specified predicate. 
        /// </summary>
        /// <param name="predicate">
        ///     a predicate that matches a unique entity
        /// </param>
        /// <returns>the uniquely matching entity or null</returns>
        /// 
        Task<Optional<S>> 
        GetUniqueMatching(Expression<Func<S,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="predicate"></param>
        /// 
        Task<IList<S>> 
        GetMatching(Expression<Func<S,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        Task<IList<S>> 
        GetAll();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="queryName">query's name</param>
        /// <returns>finder or null</returns>
        /// 
        Task<INamedQuery<S>> 
        GetNamedQuery(string queryName);
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="key"></param>
        /// 
        Task<bool> 
        HasUnique(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="predicate"></param>
        /// 
        Task<bool> 
        HasMatching(Expression<Func<S,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="queryName">query's name</param>
        /// <returns>true if query exists, false otherwise</returns>
        /// 
        Task<bool>
        HasNamedQuery(string queryName);
    }
}

//  ##########################################################################
