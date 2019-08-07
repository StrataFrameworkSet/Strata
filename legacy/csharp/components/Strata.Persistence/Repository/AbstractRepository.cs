//  ##########################################################################
//  # File Name: AbstractRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using Strata.Foundation.Utility;
using Strata.Persistence.NamedQuery;
using Strata.Persistence.UnitOfWork;

namespace Strata.Persistence.Repository
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
    class AbstractRepository<K,E,T>:
        IRepository<K,E>
        where E: class, T
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
        public virtual E
        Insert(E entity)
        {
            return (E)GetUnitOfWork().Insert<K,T>((T)entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual E
        Update(E entity)
        {
            return (E)GetUnitOfWork().Update<K,T>((T)entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual void
        Remove(E entity)
        {
            GetUnitOfWork().Remove<K,T>((T)entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual E
        GetUnique(K key)
        {
            return (E)GetUnitOfWork().GetUniqueWithKey<K,T>(key);
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
            return 
                GetUnitOfWork()
                    .GetAll<T>()
                    .Cast<E>()
                    .ToList();
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
            return GetUnitOfWork().HasUniqueWithKey<K,T>(key);
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

}

//  ##########################################################################
