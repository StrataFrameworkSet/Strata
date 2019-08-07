//  ##########################################################################
//  # File Name: IRepositoryProvider.cs
//  # Copyright: 2012, Capital Group Companies, Inc.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using Strata.Persistence.NamedQuery;

namespace Strata.Persistence.UnitOfWork
{
    public delegate K KeyRetriever<K,T>(T entity);

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an abstraction of repository implementations and includes
    /// operations for inserting, updating, removing, and getting entities.
    /// </summary>
    /// 
    /// <typeparam name="K">Key Type</typeparam>
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IRepositoryProvider<K,T>
        where T:class
    {
        IRepositoryContext Context { get; }

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
        InsertEntity(T entity);

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
        UpdateEntity(T entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an entity from the repository's underlying storage
        /// using the current unit-of-work. 
        /// </summary>
        /// 
        /// <param name="entity"></param>
        /// 
        void
        RemoveEntity(T entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity associated with the specified key. 
        /// </summary>
        /// 
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        T
        GetEntityByKey(K key);

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
        GetEntityByPredicate(Expression<Func<T, bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        IList<T>
        GetEntitiesByPredicate(Expression<Func<T, bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        IList<T> 
        GetAllEntities();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="finderName">finder's name</param>
        /// <returns>finder or null</returns>
        /// 
        INamedQuery<T> 
        GetFinder(string finderName);
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="key"></param>
        /// 
        bool
        HasEntityWithKey(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        /// 
        bool
        HasEntitiesWithPredicate(Expression<Func<T, bool>> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="finderName">finder's name</param>
        /// <returns>true if finder exists, false otherwise</returns>
        /// 
        bool 
        HasFinder(string finderName);
    }
}

//  ##########################################################################
