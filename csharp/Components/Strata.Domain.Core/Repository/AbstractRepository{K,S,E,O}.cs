//  ##########################################################################
//  # File Name: AbstractRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Domain.Core.DomainEvent;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractRepository<K,S,E,O>:
        IRepository<K,S,E,O>
        where S: class,IDomainEventSource<S,E,O>
        where E: IDomainEvent<S>
        where O: IDomainEventObserver<E>
    {
        public IUnitOfWorkProvider Provider { get; protected set; }
        public ISet<O>             Observers { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractRepository</c> instance.
        /// </summary>
        /// 
        protected
        AbstractRepository(IUnitOfWorkProvider provider)
        {
            Provider  = provider;
            Observers = new HashSet<O>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<S> 
        Insert(S entity)
        {
            S result = 
                await 
                    GetUnitOfWork()
                        .Insert<K,S>(entity)
                        .ConfigureAwait(false);

            if (result != null)
                result.Attach(Observers);

            return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<S> 
        Update(S entity)
        {
            S result = 
                await 
                    GetUnitOfWork()
                        .Update<K,S>(entity)
                        .ConfigureAwait(false);

            if (result != null)
                result.Attach(Observers);

            return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual Task
        Remove(S entity)
        {
            if (entity != null)
                entity
                    .Attach(Observers);

            return GetUnitOfWork().Remove<K,S>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<Optional<S>> 
        GetUnique(K key)
        {
            Optional<S> result =
                await 
                    GetUnitOfWork()
                        .GetUnique<K,S>(key)
                        .ConfigureAwait(false);
            
            return
                result.IfPresent(entity => entity.Attach(Observers));

        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<Optional<S>> 
        GetUniqueMatching(Expression<Func<S,bool>> predicate)
        {
            Optional<S> result = 
                await 
                    GetUnitOfWork()
                        .GetUniqueMatching(predicate)
                        .ConfigureAwait(false);

            return
                result.IfPresent(entity => entity.Attach(Observers));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<IList<S>> 
        GetMatching(Expression<Func<S,bool>> predicate)
        {
            IList<S> results = 
                await 
                    GetUnitOfWork()
                        .GetMatching(predicate)
                        .ConfigureAwait(false);

            foreach (S result in results)
                if (result != null)
                    result.Attach(Observers);

            return results;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual async Task<IList<S>> 
        GetAll()
        {
            IList<S> results = 
                await
                    GetUnitOfWork()
                        .GetAll<S>()
                        .ConfigureAwait(false);

            foreach (S result in results)
                if (result != null)
                    result.Attach(Observers);

            return results;
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
            return GetUnitOfWork().HasUnique<K,S>(key);
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
