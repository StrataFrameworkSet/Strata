//  ##########################################################################
//  # File Name: AbstractRepositoryProvider.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>IRepositoryProvider{K,T}</c> types.
    /// </summary>
    /// 
    /// <typeparam name="K">Key Type</typeparam>
    /// <typeparam name="T">Entity Type</typeparam>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractRepositoryProvider<K,T>:
        IRepositoryProvider<K,T>
        where T:class
    {
        public abstract IRepositoryContext Context { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractRepositoryContext</c> instance.
        /// </summary>
        /// 
        public 
        AbstractRepositoryProvider() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.InsertEntity(T)"/>
        /// </summary>
        /// 
        public T 
        InsertEntity(T entity)
        {
            Context.LockForWriting();

            try
            {
                return DoInsert(entity);
            }
            finally
            {
                Context.UnlockFromWriting();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.UpdateEntity(T)"/>
        /// </summary>
        /// 
        public T 
        UpdateEntity(T entity)
        {
            Context.LockForWriting();

            try
            {
                return DoUpdate(entity);
            }
            finally
            {
                Context.UnlockFromWriting();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.RemoveEntity(T)"/>
        /// </summary>
        /// 
        public void 
        RemoveEntity(T entity)
        {
            Context.LockForWriting();

            try
            {
                DoRemove(entity);
            }
            finally
            {
                Context.UnlockFromWriting();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.GetEntityByKey(K)"/>
        /// </summary>
        /// 
        public T 
        GetEntityByKey(K key)
        {
            Context.LockForReading();

            try
            {
                return DoGetEntityByKey(key);
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.InsertEntity(T)"/>
        /// </summary>
        /// 
        public T 
        GetEntityByPredicate(Func<T,bool> predicate)
        {
            Context.LockForReading();

            try
            {
                return DoGetEntityByPredicate(predicate);
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.InsertEntity(T)"/>
        /// </summary>
        /// 
        public IList<T> 
        GetEntitiesByPredicate(Func<T, bool> predicate)
        {
            Context.LockForReading();

            try
            {
                return DoGetEntitiesByPredicate(predicate);
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.InsertEntity(T)"/>
        /// </summary>
        /// 
        public IList<T> 
        GetAllEntities()
        {
            Context.LockForReading();

            try
            {
                return DoGetAllEntities();
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.GetFinder(String)"/>
        /// </summary>
        /// 
        public IFinder<T> 
        GetFinder(String finderName)
        {
            Context.LockForReading();

            try
            {
                return DoGetFinder(finderName);
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.GetEntityByKey(K)"/>
        /// </summary>
        /// 
        public bool 
        HasEntityWithKey(K key)
        {
            Context.LockForReading();

            try
            {
                return DoHasEntityByKey(key);
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.InsertEntity(T)"/>
        /// </summary>
        /// 
        public bool 
        HasEntitiesWithPredicate(Func<T, bool> predicate)
        {
            Context.LockForReading();

            try
            {
                return DoHasEntitiesByPredicate(predicate);
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryProvider{K,T}.HasFinder(String)"/>
        /// </summary>
        /// 
        public bool 
        HasFinder(String finderName)
        {
            Context.LockForReading();

            try
            {
                return DoHasFinder(finderName);
            }
            finally
            {
                Context.UnlockFromReading();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that inserts an entity into the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="entity">entity to be inserted</param>
        /// <returns>persistent instance of inserted entity</returns>
        /// 
        protected abstract T
        DoInsert(T entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that updates an entity in the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="entity">entity to be updated</param>
        /// <returns>persistent instance of updated entity</returns>
        /// 
        protected abstract T
        DoUpdate(T entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that removes an entity from the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="entity">entity to be removed</param>
        /// 
        protected abstract void
        DoRemove(T entity);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that gets an entity using its key from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="key">primary key of entity</param>
        /// <returns>entity associated with key or null</returns>
        /// 
        protected abstract T
        DoGetEntityByKey(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that gets an entity using a predicate from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="predicate">predicate for matching entities</param>
        /// <returns>entity that satisfies predicate or null</returns>
        /// 
        protected abstract T
        DoGetEntityByPredicate(Func<T,bool> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that gets entities using a predicate from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="predicate">predicate for matching entities</param>
        /// <returns>entities that satisfies predicate</returns>
        /// 
        protected abstract IList<T> 
        DoGetEntitiesByPredicate(Func<T,bool> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that gets all entities of type T from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        protected abstract IList<T> 
        DoGetAllEntities();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that gets a finder from the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="finderName">identifies specific finder</param>
        /// <returns>finder associated with finderName or null</returns>
        /// 
        protected abstract IFinder<T> 
        DoGetFinder(String finderName);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that determines if the repository contains
        /// an entity associated with the specified key.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="key">identifies entity</param>
        /// <returns>
        /// true if key is associated with an entity in the repository,
        /// false otherwise
        /// </returns>
        /// 
        protected abstract bool
        DoHasEntityByKey(K key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that determines if the repository contains
        /// an entities satisfying the specified predicate.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="predicate">predicate for matching entities</param>
        /// <returns>
        /// true if repository has entities satisfying the predicate,
        /// false otherwise
        /// </returns>
        /// 
        protected abstract bool
        DoHasEntitiesByPredicate(Func<T,bool> predicate);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Helper method that determines if the repository has a finder
        /// with the specified name. Subclasses must implement this 
        /// method for specific object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="finderName">identifies finder</param>
        /// <returns>true if repository has finder, false otherwise</returns>
        /// 
        protected abstract bool
        DoHasFinder(String finderName);

    }
}

//  ##########################################################################
