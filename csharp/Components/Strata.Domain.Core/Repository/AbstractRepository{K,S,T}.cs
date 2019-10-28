//  ##########################################################################
//  # File Name: AbstractRepository{K,S,T}.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.UnitOfWork;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractRepository<K,S,T>:
        IRepository<K,S>
        where S : class, T
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
        public virtual async Task<S>
        Insert(S entity)
        {
            T inserted =
                await
                    GetUnitOfWork()
                        .Insert<K,T>((T)entity)
                        .ConfigureAwait(false);

            return (S)inserted;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<S>
        Update(S entity)
        {
            T updated =
                await
                    GetUnitOfWork()
                        .Update<K,T>((T)entity)
                        .ConfigureAwait(false);

            return (S)updated;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task
        Remove(S entity)
        {
            return 
                GetUnitOfWork()
                    .Remove<K,T>((T)entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<Optional<S>> 
        GetUnique(K key)
        {
            Optional<T> requested = 
                await 
                    GetUnitOfWork()
                        .GetUnique<K,T>(key)
                        .ConfigureAwait(false);

            return
                requested.IfPresentOrElse(
                    e => Optional<S>.OfNullable((S)e),
                    Optional<S>.Empty);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<Optional<S>> 
        GetUniqueMatching(Expression<Func<S,bool>> predicate)
        {
            return GetUnitOfWork().GetUniqueMatching<S>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<IList<S>> 
        GetMatching(Expression<Func<S,bool>> predicate)
        {

            return GetUnitOfWork().GetMatching<S>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<IList<S>> 
        GetAll()
        {
            IList<T> requested =
                await 
                    GetUnitOfWork()
                        .GetAll<T>()
                        .ConfigureAwait(false);

            return 
                requested
                    .Cast<S>()
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<INamedQuery<S>> 
        GetNamedQuery(string queryName)
        {
            return GetUnitOfWork().GetNamedQuery<S>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> 
        HasUnique(K key)
        {
            return GetUnitOfWork().HasUnique<K,T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> 
        HasMatching(Expression<Func<S,bool>> predicate)
        {
            return GetUnitOfWork().HasUniqueMatching<S>(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task<bool> 
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
