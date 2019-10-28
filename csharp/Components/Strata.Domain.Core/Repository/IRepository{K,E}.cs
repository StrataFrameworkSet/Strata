//  ##########################################################################
//  # File Name: IRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
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
    /// <typeparam name="E">Entity Type</typeparam>
    ///  
    public
    interface IRepository<in K,E>
        where E: class
    {
        IUnitOfWorkProvider Provider { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Inserts an entity into the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        Task<E> 
        Insert(E entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Updates an entity in the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// <param name="entity"></param>
        /// <returns>the updated entity</returns>
        /// 
        Task<E> 
        Update(E entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an entity from the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// <param name="entity"></param>
        Task
        Remove(E entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity associated with the specified key. 
        /// </summary>
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        Task<Optional<E>> 
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
        Task<Optional<E>> 
        GetUniqueMatching(Expression<Func<E,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="predicate"></param>
        /// 
        Task<IList<E>> 
        GetMatching(Expression<Func<E,bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        Task<IList<E>> 
        GetAll();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="queryName">query's name</param>
        /// <returns>finder or null</returns>
        /// 
        Task<INamedQuery<E>> 
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
        HasMatching(Expression<Func<E,bool>> predicate);

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
