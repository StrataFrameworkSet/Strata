using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Persistence.InMemoryRepository;
using Strata.Persistence.Repository;

namespace Strata.FlatFilePersistence.CsvRepository
{
    public class CsvRepositoryProvider<TKey, T, TAdapter> : AbstractRepositoryProvider<TKey, T> 
        where T: class, new()
        where TAdapter : class, T, new()
    {
        private readonly KeyRetriever<TKey, T> keyRetriever;
        private readonly IDictionary<String, IFinder<T>> finders;

        private readonly CsvRepositoryContext<TKey, T, TAdapter> context;
        public override IRepositoryContext Context
        {
            get { return context; }
        }
        
        public CsvRepositoryProvider(CsvRepositoryContext<TKey, T, TAdapter> c, KeyRetriever<TKey, T> retriever)
        {
            context = c;
            keyRetriever = retriever;
            finders = new Dictionary<string, IFinder<T>>();
        }

        /// <summary>
        /// Helper method that inserts an entity into the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="entity">entity to be inserted</param>
        /// <returns>persistent instance of inserted entity</returns>
        protected override T DoInsert(T entity)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            return unitOfWork.DoInsert(
                CsvAdapterPropertyHelper.Copy(entity, new TAdapter()),
                keyRetriever);
        }

        /// <summary>
        /// Helper method that updates an entity in the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="entity">entity to be updated</param>
        /// <returns>persistent instance of updated entity</returns>
        /// 
        protected override T DoUpdate(T entity)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            return unitOfWork.DoUpdate(
                CsvAdapterPropertyHelper.Copy(entity, new TAdapter()),
                keyRetriever);
        }

        /// <summary>
        /// Helper method that removes an entity from the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="entity">entity to be removed</param>
        /// 
        protected override void DoRemove(T entity)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            unitOfWork.DoRemove(
                CsvAdapterPropertyHelper.Copy(entity, new TAdapter()),
                keyRetriever);
        }

        /// <summary>
        /// Helper method that gets an entity using its key from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="key">primary key of entity</param>
        /// <returns>entity associated with key or null</returns>
        /// 
        protected override T DoGetEntityByKey(TKey key)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            return unitOfWork.DoGet(key, keyRetriever);
        }

        /// <summary>
        /// Helper method that gets an entity using a predicate from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="predicate">predicate for matching entities</param>
        /// <returns>entity that satisfies predicate or null</returns>
        /// 
        protected override T DoGetEntityByPredicate(Func<T, bool> predicate)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            return unitOfWork.DoQuery().FirstOrDefault(predicate);
        }

        /// <summary>
        /// Helper method that gets entities using a predicate from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        /// <param name="predicate">predicate for matching entities</param>
        /// <returns>entities that satisfies predicate</returns>
        /// 
        protected override IList<T> DoGetEntitiesByPredicate(Func<T, bool> predicate)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            var query = unitOfWork.DoQuery().ToList();
            var result = query.AsQueryable()
                .Where(predicate)
                .ToList();
            return result;
        }

        /// <summary>
        /// Helper method that gets all entities of type T from the 
        /// repository. Subclasses must implement this method for specific 
        /// object-relational frameworks. 
        /// </summary>
        /// 
        protected override IList<T> DoGetAllEntities()
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            return new List<T>(unitOfWork.DoQuery());
        }

        /// <summary>
        /// Helper method that gets a finder from the repository.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="finderName">identifies specific finder</param>
        /// <returns>finder associated with finderName or null</returns>
        /// 
        protected override IFinder<T> DoGetFinder(string finderName)
        {
            return finders[finderName];
        }

        /// <summary>
        /// Helper method that determines if the repository contains
        /// an entity associated with the specified key.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="key">identifies entity</param>
        /// <returns>
        /// true if key is associated with an entity in the repository,
        /// false otherwise
        /// </returns>
        /// 
        protected override bool DoHasEntityByKey(TKey key)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            return unitOfWork.DoHas(key, keyRetriever);
        }

        /// <summary>
        /// Helper method that determines if the repository contains
        /// an entities satisfying the specified predicate.
        /// Subclasses must implement this method for specific 
        /// object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="predicate">predicate for matching entities</param>
        /// <returns>
        /// true if repository has entities satisfying the predicate,
        /// false otherwise
        /// </returns>
        /// 
        protected override bool DoHasEntitiesByPredicate(Func<T, bool> predicate)
        {
            var unitOfWork = (CsvUnitOfWork<TKey, T, TAdapter>)context.GetUnitOfWork();
            return unitOfWork
                .DoQuery()
                .Any(predicate);
        }

        /// <summary>
        /// Helper method that determines if the repository has a finder
        /// with the specified name. Subclasses must implement this 
        /// method for specific object-relational frameworks.
        /// </summary>
        /// 
        /// <param name="finderName">identifies finder</param>
        /// <returns>true if repository has finder, false otherwise</returns>
        /// 
        protected override bool DoHasFinder(string finderName)
        {
            return finders.ContainsKey(finderName);
        }

        public void CreateFinder(String finderName, IPredicate<T> predicate)
	    {
	        IFinder<T> finder = 
	            new CsvFinder<TKey, T, TAdapter>(
	                context,
	                finderName,
	                predicate);
	    
	        finders.Add( finder.Name,finder ); 
	    }
    }
}
