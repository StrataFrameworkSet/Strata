//  ##########################################################################
//  # File Name: NhibernateRepositoryProvider.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Persistence.Repository;
using System.Linq;
using NHibernate;
using NHibernate.Cfg;
using NHibernate.Exceptions;
using NHibernate.Linq;


namespace Strata.NhibernatePersistence.NhibernateRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class NhibernateRepositoryProvider<K,T>:
        AbstractRepositoryProvider<K,T>
        where T:class
    {
        private NhibernateRepositoryContext context;

        public override IRepositoryContext Context
        {
            get { return context; }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        NhibernateRepositoryProvider(NhibernateRepositoryContext c)
        {
            context = c;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRepositoryProvider{K,T}.DoInsert(T)"/>
        /// 
        protected override T 
        DoInsert(T entity)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return unitOfWork.DoInsert(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRepositoryProvider{K,T}.DoUpdate(T)"/>
        /// 
        protected override T 
        DoUpdate(T entity)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return unitOfWork.DoUpdate(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRepositoryProvider{K,T}.DoRemove(T)"/>
        /// 
        protected override void 
        DoRemove(T entity)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            unitOfWork.DoRemove(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRepositoryProvider{K,T}.DoGetEntityByKey(K)"/>
        /// 
        protected override T 
        DoGetEntityByKey(K key)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return unitOfWork.DoGet<K,T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRepositoryProvider{K,T}
        ///                .DoGetEntityByPredicate(Func{T,bool})"/>
        /// 
        protected override T 
        DoGetEntityByPredicate(Func<T,bool> predicate)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return
                unitOfWork
                    .DoQuery<T>()
                    .SingleOrDefault(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRepositoryProvider{K,T}
        ///                .DoGetEntitiesByPredicate(Func{T,bool})"/>
        /// 
        protected override IList<T> 
        DoGetEntitiesByPredicate(Func<T, bool> predicate)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return
                unitOfWork
                    .DoQuery<T>()
                    .Where(predicate)
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="AbstractRepositoryProvider{K,T}.DoGetAllEntities()"/>
        /// </summary>
        /// 
        protected override IList<T> 
        DoGetAllEntities()
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return                 
                unitOfWork
                    .DoQuery<T>()
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="AbstractRepositoryProvider{K,T}.DoGetFinder(String)"/>
        /// </summary>
        /// 
        protected override IFinder<T> 
        DoGetFinder(String finderName)
        {
	        if ( DoHasFinder(finderName))
	            return 
	                new NhibernateFinder<T>(finderName,context);
            
            return null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="AbstractRepositoryProvider{K,T}.DoHasEntityByKey(K)"/>
        /// </summary>
        /// 
        protected override bool 
        DoHasEntityByKey(K key)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return unitOfWork.DoHas<K,T>(key);

        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="AbstractRepositoryProvider{K,T}
        ///                .DoHasEntitiesByPredicate(Func{T,bool})"/>
        /// </summary>
        /// 
        protected override bool 
        DoHasEntitiesByPredicate(Func<T,bool> predicate)
        {
            NhibernateUnitOfWork unitOfWork = 
                (NhibernateUnitOfWork)context.GetUnitOfWork();

            return
                unitOfWork
                    .DoQuery<T>()
                    .Where(predicate)
                    .Any();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="AbstractRepositoryProvider{K,T}.DoHasFinder(String)"/>
        /// </summary>
        /// 
        protected override bool 
        DoHasFinder(String finderName)
        {
            ISessionFactory factory = context.GetSessionFactory();
            ISession        session = factory.OpenSession();
        
            return session.GetNamedQuery(finderName) != null;
        }
    }
}

//  ##########################################################################
