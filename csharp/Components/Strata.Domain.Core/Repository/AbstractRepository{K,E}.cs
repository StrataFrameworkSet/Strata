//  ##########################################################################
//  # File Name: AbstractRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.Repository
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
        public virtual Task<E> 
        Insert(E entity)
        {
            return GetUnitOfWork().Insert<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<E> 
        Update(E entity)
        {
            return GetUnitOfWork().Update<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task
        Remove(E entity)
        {
            return GetUnitOfWork().Remove<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<Optional<E>> 
        GetUnique(K key)
        {
            return GetUnitOfWork().GetUnique<K,E>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<Optional<E>> 
        GetUniqueMatching(Expression<Func<E,bool>> predicate)
        {
            return GetUnitOfWork().GetUniqueMatching<E>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<IList<E>> 
        GetMatching(Expression<Func<E,bool>> predicate)
        {
            return GetUnitOfWork().GetMatching<E>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<IList<E>> 
        GetAll()
        {
            return GetUnitOfWork().GetAll<E>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<INamedQuery<E>> 
        GetNamedQuery(string queryName)
        {
            return GetUnitOfWork().GetNamedQuery<E>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> 
        HasUnique(K key)
        {
            return GetUnitOfWork().HasUnique<K,E>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> 
        HasMatching(Expression<Func<E,bool>> predicate)
        {
            return GetUnitOfWork().HasUniqueMatching<E>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> 
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
