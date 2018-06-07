//  ##########################################################################
//  # File Name: AbstractRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.NamedQuery;
using Strata.Domain.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;

namespace Strata.Domain.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractRepository<K,E>:
        IRepository<K,E>
        where E: class
    {
        public IUnitOfWorkProvider Provider { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractRepository</c> instance.
        /// </summary>
        /// 
        protected
        AbstractRepository(IUnitOfWorkProvider provider)
        {
            Provider = provider;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual E 
        Insert(E entity)
        {
            return GetUnitOfWork().Insert<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual E 
        Update(E entity)
        {
            return GetUnitOfWork().Update<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual void 
        Remove(E entity)
        {
            GetUnitOfWork().Remove<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual E
        GetUnique(K key)
        {
            return GetUnitOfWork().GetUniqueWithKey<K,E>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual E
        GetUnique(Expression<Func<E,bool>> predicate)
        {
            return GetUnitOfWork().GetUniqueWithPredicate<E>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IList<E> 
        GetAll(Expression<Func<E,bool>> predicate)
        {
            return GetUnitOfWork().GetAllWithPredicate<E>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IList<E> 
        GetAll()
        {
            return GetUnitOfWork().GetAll<E>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual INamedQuery<E> 
        GetNamedQuery(string queryName)
        {
            return GetUnitOfWork().GetNamedQuery<E>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool
        HasUnique(K key)
        {
            return GetUnitOfWork().HasUniqueWithKey<K,E>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool
        HasAny(Expression<Func<E,bool>> predicate)
        {
            return GetUnitOfWork().HasUniqueWithPredicate<E>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool
        HasNamedQuery(string queryName)
        {
            return GetUnitOfWork().HasNamedQuery<E>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>>
        /// 
        protected virtual IUnitOfWork
        GetUnitOfWork()
        {
            return Provider.GetUnitOfWork();
        }
    }

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractRepository<K,S,T>:
        IRepository<K,S>
        where S: class, T
        where T : class
    {
        public IUnitOfWorkProvider Provider { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractRepository</c> instance.
        /// </summary>
        /// 
        protected
        AbstractRepository(IUnitOfWorkProvider provider)
        {
            Provider = provider;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual S
        Insert(S entity)
        {
            return (S)GetUnitOfWork().Insert<K,T>((T)entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual S
        Update(S entity)
        {
            return (S)GetUnitOfWork().Update<K,T>((T)entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual void
        Remove(S entity)
        {
            GetUnitOfWork().Remove<K,T>((T)entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual S
        GetUnique(K key)
        {
            return (S)GetUnitOfWork().GetUniqueWithKey<K,T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual S
        GetUnique(Expression<Func<S,bool>> predicate)
        {
            return GetUnitOfWork().GetUniqueWithPredicate<S>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IList<S>
        GetAll(Expression<Func<S,bool>> predicate)
        {

            return GetUnitOfWork().GetAllWithPredicate<S>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IList<S>
        GetAll()
        {
            return 
                GetUnitOfWork()
                    .GetAll<T>()
                    .Cast<S>()
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual INamedQuery<S>
        GetNamedQuery(string queryName)
        {
            return GetUnitOfWork().GetNamedQuery<S>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool
        HasUnique(K key)
        {
            return GetUnitOfWork().HasUniqueWithKey<K,T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool
        HasAny(Expression<Func<S,bool>> predicate)
        {
            return GetUnitOfWork().HasUniqueWithPredicate<S>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual bool
        HasNamedQuery(string queryName)
        {
            return GetUnitOfWork().HasNamedQuery<S>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>>
        /// 
        protected virtual IUnitOfWork
        GetUnitOfWork()
        {
            return Provider.GetUnitOfWork();
        }
    }

}

//  ##########################################################################
