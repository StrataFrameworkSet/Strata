//  ##########################################################################
//  # File Name: AbstractUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Core.NamedQuery;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Foundation.Core.Utility;
using TaskFactory = Strata.Foundation.Core.Utility.TaskFactory;

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
        public Task<E> 
        Insert<K,E>(E entity) 
            where E: class
        {
            return State.Insert<K,E>(this,entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<E> 
        Update<K,E>(E entity) 
            where E: class
        {
            return State.Update<K,E>(this,entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task
        Remove<K,E>(E entity) 
            where E: class
        {
            return State.Remove<K,E>(this,entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<Optional<E>> 
        GetUnique<K,E>(K key) 
            where E: class
        {
            return State.GetUnique<K,E>(this,key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<Optional<E>> 
        GetUniqueMatching<E>(Expression<Func<E,bool>> predicate) 
            where E: class
        {
            return State.GetUniqueMatching(this,predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<IList<E>> 
        GetMatching<E>(Expression<Func<E,bool>> predicate) 
            where E: class
        {
            return State.GetMatching(this,predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<IList<E>> 
        GetAll<E>() 
            where E: class
        {
            return State.GetAll<E>(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<INamedQuery<E>> 
        GetNamedQuery<E>(string queryName) 
            where E: class
        {
            return State.GetNamedQuery<E>(this,queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<bool> 
        HasUnique<K,E>(K key) 
            where E: class
        {
            return State.HasUnique<K,E>(this,key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<bool> 
        HasUniqueMatching<E>(Expression<Func<E,bool>> predicate) 
            where E: class
        {
            return State.HasUniqueMatching(this,predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task<bool> 
        HasNamedQuery<E>(string queryName)
            where E: class
        {
            return State.HasNamedQuery<E>(this,queryName);
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
        public Task 
        Commit()
        {
            return State.Commit(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public Task 
        Rollback()
        {
            return State.Rollback(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// <param name="entity">entity being inserted</param>
        /// <returns>the inserted entity</returns>
        /// 
        public abstract Task<E> 
        DoInsert<K,E>(E entity)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// <param name="entity">entity being updated</param>
        /// <returns>the updated entity</returns>
        /// 
        public abstract Task<E> 
        DoUpdate<K,E>(E entity)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// <param name="entity">entity being removed</param>
        public abstract Task
        DoRemove<K,E>(E entity)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// <param name="key">an entity's key</param>
        /// <returns>the entity if it exists or null</returns>
        /// 
        public abstract Task<Optional<E>> 
        DoGetUnique<K,E>(K key)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Retrieves the entity that matches the specified predicate. 
        /// </summary>
        /// <param name="predicate">
        ///     a predicate that matches a unique entity
        /// </param>
        /// <returns>the uniquely matching entity or null</returns>
        /// 
        public abstract Task<Optional<E>> 
        DoGetUniqueMatching<E>(Expression<Func<E,bool>> predicate)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="predicate"></param>
        /// 
        public abstract Task<IList<E>> 
        DoGetMatching<E>(Expression<Func<E,bool>> predicate)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        public abstract Task<IList<E>> 
        DoGetAll<E>()
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="queryName">query's name</param>
        /// <returns>query or null</returns>
        /// 
        public abstract Task<INamedQuery<E>> 
        DoGetNamedQuery<E>(string queryName)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="key"></param>
        /// 
        public abstract Task<bool> 
        DoHasUnique<K,E>(K key)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="predicate"></param>
        /// 
        public abstract Task<bool> 
        DoHasMatching<E>(Expression<Func<E,bool>> predicate)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// <param name="queryName">query's name</param>
        /// <returns>true if query exists, false otherwise</returns>
        /// 
        public abstract Task<bool> 
        DoHasNamedQuery<E>(string queryName)
            where E: class;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        public abstract Task 
        DoCommit();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  
        /// </summary>
        /// 
        public virtual Task 
        DoRollback()
        {
            Task task = 
                new Task(
                    () =>
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
                    });

            task.Start();
            return task;
        }
    }
}

//  ##########################################################################
