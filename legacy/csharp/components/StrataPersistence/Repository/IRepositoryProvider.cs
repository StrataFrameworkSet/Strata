//  ##########################################################################
//  # File Name: IRepositoryProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
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
        GetEntityByPredicate(Func<T,bool> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        IList<T> 
        GetEntitiesByPredicate(Func<T,bool> predicate);

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
        IFinder<T> 
        GetFinder(String finderName);
        
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
        HasEntitiesWithPredicate(Func<T,bool> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="finderName">finder's name</param>
        /// <returns>true if finder exists, false otherwise</returns>
        /// 
        bool 
        HasFinder(String finderName);
    }
}

//  ##########################################################################
