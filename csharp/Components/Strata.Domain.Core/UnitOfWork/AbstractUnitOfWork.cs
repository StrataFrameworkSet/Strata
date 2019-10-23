//  ##########################################################################
//  # File Name: AbstractUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Core.NamedQuery;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;

namespace Strata.Domain.Core.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractUnitOfWork:
        IUnitOfWork
    {
        public IUnitOfWorkProvider     Provider { get; protected set; }
        internal IUnitOfWorkState      State { get; set; }
        internal Stack<RollbackAction> RollbackActions { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractUnitOfWork</c> instance.
        /// </summary>
        /// 
        protected
        AbstractUnitOfWork(IUnitOfWorkProvider provider)
        {
            Provider        = provider;
            State           = ActiveUnitOfWorkState.GetInstance();
            RollbackActions = new Stack<RollbackAction>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract void 
        Dispose();

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T 
        Insert<K,T>(T entity) 
            where T: class
        {
            return State.Insert<K,T>(this,entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T 
        Update<K,T>(T entity) 
            where T: class
        {
            return State.Update<K,T>(this,entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Remove<K,T>(T entity) 
            where T: class
        {
            State.Remove<K,T>(this,entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T 
        GetUniqueWithKey<K,T>(K key) 
            where T: class
        {
            return State.GetUniqueWithKey<K,T>(this,key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T 
        GetUniqueWithPredicate<T>(Expression<Func<T, bool>> predicate) 
            where T: class
        {
            return State.GetUniqueWithPredicate(this,predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IList<T> 
        GetAllWithPredicate<T>(Expression<Func<T, bool>> predicate) 
            where T: class
        {
            return State.GetAllWithPredicate(this,predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IList<T> 
        GetAll<T>() 
            where T: class
        {
            return State.GetAll<T>(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public INamedQuery<T> 
        GetNamedQuery<T>(string queryName) 
            where T: class
        {
            return State.GetNamedQuery<T>(this,queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        HasUniqueWithKey<K,T>(K key) 
            where T: class
        {
            return State.HasUniqueWithKey<K,T>(this,key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        HasUniqueWithPredicate<T>(Expression<Func<T, bool>> predicate) 
            where T: class
        {
            return State.HasUniqueWithPredicate(this,predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        HasNamedQuery<T>(string queryName)
            where T: class
        {
            return State.HasNamedQuery<T>(this,queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        PushRollbackAction(RollbackAction rollbackAction)
        {
            RollbackActions.Push(rollbackAction);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        IsActive()
        {
            return State.IsActive(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        IsFailed()
        {
            return State.IsFailed(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        IsCommitted()
        {
            return State.IsCommitted(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        IsRolledBack()
        {
            return State.IsRolledBack(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Commit()
        {
            State.Commit(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Rollback()
        {
            State.Rollback(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        public abstract T
        DoInsert<K,T>(T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        /// <param name="entity">entity being updated</param>
        /// <returns>the updated entity</returns>
        /// 
        public abstract T
        DoUpdate<K,T>(T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        /// <param name="entity">entity being removed</param>
        /// 
        public abstract void
        DoRemove<K,T>(T entity)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        public abstract T
        DoGetUniqueWithKey<K,T>(K key)
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
        public abstract T
        DoGetUniqueWithPredicate<T>(Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        public abstract IList<T>
        DoGetAllWithPredicate<T>(Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        public abstract IList<T>
        DoGetAll<T>()
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="queryName">query's name</param>
        /// <returns>query or null</returns>
        /// 
        public abstract INamedQuery<T> 
        DoGetNamedQuery<T>(string queryName)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="key"></param>
        /// 
        public abstract bool
        DoHasUniqueWithKey<K,T>(K key)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="predicate"></param>
        /// 
        public abstract bool
        DoHasAnyWithPredicate<T>(Expression<Func<T,bool>> predicate)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        /// <param name="queryName">query's name</param>
        /// <returns>true if query exists, false otherwise</returns>
        /// 
        public abstract bool 
        DoHasNamedQuery<T>(string queryName)
            where T: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        public abstract void 
        DoCommit();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        public virtual void 
        DoRollback()
        {
            RollbackFailedException failed = null;

            while (RollbackActions.Count != 0)
            {
                try
                {
                    RollbackAction action = RollbackActions.Pop();

                    action.Invoke();
                }
                catch (Exception e)
                {
                    if (failed == null)
                        failed = 
                            new RollbackFailedException(
                                "Rollback failed. See causes.");

                    failed.Causes.Add(e);
                }
            }

            if (failed != null)
                throw failed;
        }
    }
}

//  ##########################################################################
