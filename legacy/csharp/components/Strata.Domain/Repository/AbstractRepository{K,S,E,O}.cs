//  ##########################################################################
//  # File Name: AbstractRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.NamedQuery;
using Strata.Domain.Shared;
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
        public virtual S 
        Insert(S entity)
        {
            S result = GetUnitOfWork().Insert<K,S>(entity);

            if (result != null)
                result.Attach(Observers);

            return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual S 
        Update(S entity)
        {
            S result = GetUnitOfWork().Update<K,S>(entity);

            if (result != null)
                result.Attach(Observers);

            return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual void 
        Remove(S entity)
        {
            GetUnitOfWork().Remove<K,S>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual S
        GetUnique(K key)
        {
            S result = GetUnitOfWork().GetUniqueWithKey<K,S>(key);

            if (result != null)
                result.Attach(Observers);

            return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual S
        GetUnique(Expression<Func<S,bool>> predicate)
        {
            S result = GetUnitOfWork().GetUniqueWithPredicate(predicate);

            if (result != null)
                result.Attach(Observers);

            return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IList<S> 
        GetAll(Expression<Func<S,bool>> predicate)
        {
            IList<S> results = 
                GetUnitOfWork().GetAllWithPredicate(predicate);

            foreach (S result in results)
                if (result != null)
                    result.Attach(Observers);

            return results;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IList<S> 
        GetAll()
        {
            IList<S> results = GetUnitOfWork().GetAll<S>();

            foreach (S result in results)
                if (result != null)
                    result.Attach(Observers);

            return results;
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
            return GetUnitOfWork().HasUniqueWithKey<K,S>(key);
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
