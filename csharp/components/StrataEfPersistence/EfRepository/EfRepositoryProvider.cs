//  ##########################################################################
//  # File Name: EntityFrameworkRepositoryProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity.Infrastructure;
using System.Reflection;
using System.Linq;
using Strata.Persistence.Repository;

namespace Strata.EfPersistence.EfRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>MCDM</author>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class EfRepositoryProvider<K,T>:
        AbstractRepositoryProvider<K,T>
        where T:class
    {
        private IEfRepositoryContext context;
        private IDictionary<String,IFinder<T>> finders;

        public override IRepositoryContext Context
        {
            get {return context;}
        }

        public 
        EfRepositoryProvider(IEfRepositoryContext c)
        {
            context = c;
            finders = new Dictionary<String,IFinder<T>>();
		    InitializeFinders( context.GetFinders<T>() );
        }

        protected override T 
        DoInsert(T entity)
        {
            EfUnitOfWork unitOfWork;

            if (entity == null)
                throw new ArgumentNullException("entity");

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return unitOfWork.DoInsert( entity );
        }

        protected override T 
        DoUpdate(T entity)
        {
            EfUnitOfWork unitOfWork;

            if (entity == null)
                throw new ArgumentNullException("entity");

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return unitOfWork.DoUpdate( entity );
         }

        protected override void 
        DoRemove(T entity)
        {
            EfUnitOfWork unitOfWork;

            if (entity == null)
                throw new ArgumentNullException("entity");

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            unitOfWork.DoRemove( entity );
        }

        protected override T 
        DoGetEntityByKey(K key)
        {
            EfUnitOfWork unitOfWork;

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return unitOfWork.DoGet<K,T>( key );
        }

        protected override IFinder<T> 
        DoGetFinder(string finderName)
        {            
            return finders[finderName];
        }

        protected override bool 
        DoHasFinder(string finderName)
        {
            return finders.ContainsKey( finderName );
        }

        protected override T 
        DoGetEntityByPredicate(Func<T,bool> predicate)
        {
            EfUnitOfWork unitOfWork;

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return 
                unitOfWork
                    .DoQuery<T>()
                    .Where<T>(predicate)
                    .SingleOrDefault();
        }

        protected override IList<T> 
        DoGetEntitiesByPredicate(Func<T, bool> predicate)
        {
            EfUnitOfWork unitOfWork;

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return 
                unitOfWork
                    .DoQuery<T>()
                    .Where<T>(predicate)
                    .ToList();
        }

        protected override IList<T> 
        DoGetAllEntities()
        {
            EfUnitOfWork unitOfWork;

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return 
                unitOfWork
                    .DoQuery<T>()
                    .ToList();
        }

        protected override bool 
        DoHasEntityByKey(K key)
        {
            EfUnitOfWork unitOfWork;

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return unitOfWork.DoHas<K,T>( key );
        }

        protected override bool 
        DoHasEntitiesByPredicate(Func<T,bool> predicate)
        {
            EfUnitOfWork unitOfWork;

            unitOfWork = (EfUnitOfWork)context.GetUnitOfWork();
            return 
                unitOfWork
                    .DoQuery<T>()
                    .Any(predicate);
        }

        private void
        InitializeFinders(IList<EfFinder<T>> f)
        {
            foreach (EfFinder<T> finder in f)
                finders.Add( finder.Name,finder );
        }

    }
}

//  ##########################################################################
