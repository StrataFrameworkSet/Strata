//  ##########################################################################
//  # File Name: AbstractUnitOfWorkState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Domain.Core.NamedQuery;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractUnitOfWorkState:
        IUnitOfWorkState
    {
        public string Name { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractUnitOfWorkState</c> instance.
        /// </summary>
        /// 
        protected
        AbstractUnitOfWorkState(string name)
        {
            Name = name;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract void
        Dispose();

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<E> 
        Insert<K,E>(AbstractUnitOfWork context,E entity) 
            where E: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call Insert(T) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<E> 
        Update<K,E>(AbstractUnitOfWork context,E entity) 
            where E: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call Update(T) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task
        Remove<K,E>(AbstractUnitOfWork context,E entity) 
            where E: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call Remove(T) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<Optional<T>> 
        GetUnique<K,T>(AbstractUnitOfWork context,K key) 
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call GetUniqueWithKey(K) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<Optional<T>> 
        GetUniqueMatching<T>(AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate) 
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call GetUniqueWithPredicate" + 
                    "(Expression<Func<T,bool>>) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<IList<T>> GetMatching<T>(AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate) 
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call GetAllWithPredicate" + 
                    "(Expression<Func<T,bool>>) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<IList<T>> GetAll<T>(AbstractUnitOfWork context) 
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call GetMatching<T>() from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<INamedQuery<T>> GetNamedQuery<T>(AbstractUnitOfWork context,string queryName) 
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call GetNamedQuery<T>(string) from state: " + 
                    Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> HasUnique<K,T>(AbstractUnitOfWork context,K key) 
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call HasUniqueWithKey(K) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> HasUniqueMatching<T>(AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate) 
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call HasUniqueWithPredicate" + 
                    "(Expression<Func<T,bool>>) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> HasNamedQuery<T>(AbstractUnitOfWork context,string queryName)
            where T: class
        {
            throw 
                new InvalidOperationException(
                    "Cannot call HasNamedQuery(string) from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool 
        IsActive(AbstractUnitOfWork context)
        {
            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool 
        IsFailed(AbstractUnitOfWork context)
        {
            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool 
        IsCommitted(AbstractUnitOfWork context)
        {
            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool 
        IsRolledBack(AbstractUnitOfWork context)
        {
            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task
            Commit(AbstractUnitOfWork context)
        {
            throw 
                new InvalidOperationException(
                    "Cannot call Commit() from state: " + Name );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task
            Rollback(AbstractUnitOfWork context)
        {
            throw 
                new InvalidOperationException(
                    "Cannot call Rollback() from state: " + Name );
        }
    }
}

//  ##########################################################################
