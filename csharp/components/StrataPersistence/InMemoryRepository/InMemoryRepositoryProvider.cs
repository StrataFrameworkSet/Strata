//  ##########################################################################
//  # File Name: InMemoryRepositoryProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using Strata.Common.Utility;
using Strata.Persistence.Repository;

namespace Strata.Persistence.InMemoryRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class InMemoryRepositoryProvider<K,T> :
        AbstractRepositoryProvider<K,T>
        where T:class
    {
        private Type                           itsEntityClass;
	    private InMemoryRepositoryContext      itsContext;
	    private IEntityReplicator<K,T>         itsReplicator;
	    private KeyRetriever<K,T>		       itsRetriever;
	    private IDictionary<String,IFinder<T>> itsFinders;

        public override IRepositoryContext Context
        {
            get { return itsContext; }
        }

        public 
        InMemoryRepositoryProvider(
            InMemoryRepositoryContext c,
            IEntityReplicator<K,T>    r,
            KeyRetriever<K,T>         k)
        {
		    itsEntityClass = typeof(T);
		    itsContext     = c;
		    itsReplicator  = r;
		    itsRetriever   = k;
		    itsFinders     = new Dictionary<String,IFinder<T>>();
		
		    InitializeFinders( itsContext.GetFinders<T>() );
		    CreateFinder( "GetAll"  ,new GetAllPredicate<T>() );
        }

	    public void 
	    CreateFinder(String finderName,IPredicate<T> predicate)
	    {
	        IFinder<T> finder = null;

            if ( !DoHasFinder( finderName ) )
            {
                finder = new InMemoryFinder<T>(
                    itsContext,
                    finderName,
                    predicate );

                itsFinders.Add( finder.Name,finder );
            }
	    }
	
        protected override T 
        DoInsert(T entity) 
        {
            try
            {
                InMemoryUnitOfWork unitOfWork = 
                    (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

                return
                    unitOfWork.DoInsert( entity,itsReplicator,itsRetriever );
            }
            catch (Exception cause)
            {
                throw new InsertFailedException("insert failed",cause);
            }
        }

        protected override T 
        DoUpdate(T entity) 
        {
            try
            {
                InMemoryUnitOfWork unitOfWork = 
                    (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

                return
                    unitOfWork
                        .DoUpdate( 
                            entity,
                            itsReplicator, 
                            itsRetriever );
            }
            catch (Exception cause)
            {
                throw new UpdateFailedException("updated failed",cause);
            }
        }

        protected override void 
        DoRemove(T entity) 
        {
            try
            {
                InMemoryUnitOfWork unitOfWork = 
                    (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

               unitOfWork.DoRemove( entity,itsRetriever );
            }
            catch (Exception cause)
            {
                throw new RemoveFailedException("remove failed",cause);
            }
        }

        protected override T 
        DoGetEntityByKey(K key)
        {
            InMemoryUnitOfWork unitOfWork = 
                (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

            return unitOfWork.DoGet<K,T>( key );
        }

        protected override T
        DoGetEntityByPredicate(Func<T,bool> predicate)
        {
            InMemoryUnitOfWork unitOfWork = 
                (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

            return
                unitOfWork
                    .DoQuery<T>()
                    .SingleOrDefault(predicate);
        }

        protected override IList<T> 
        DoGetEntitiesByPredicate(Func<T,bool> predicate)
        {
            InMemoryUnitOfWork unitOfWork = 
                (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

            return
                unitOfWork
                    .DoQuery<T>()
                    .Where(predicate)
                    .ToList();
        }

        protected override IList<T> 
        DoGetAllEntities()
        {
            InMemoryUnitOfWork unitOfWork = 
                (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

            return
                unitOfWork
                    .DoQuery<T>()
                    .ToList();
        }

        protected override IFinder<T> 
        DoGetFinder(String finderName)
        {
            return itsFinders[finderName].Copy();
        }

        protected override bool 
        DoHasEntityByKey(K key)
        {
            InMemoryUnitOfWork unitOfWork = 
                (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

            return unitOfWork.DoHas<K,T>(key);

        }

        protected override bool 
        DoHasEntitiesByPredicate(Func<T,bool> predicate)
        {
            InMemoryUnitOfWork unitOfWork = 
                (InMemoryUnitOfWork)itsContext.GetUnitOfWork();

            return
                unitOfWork
                    .DoQuery<T>()
                    .Any(predicate);
        }

        protected override bool 
        DoHasFinder(String finderName)
        {
            return itsFinders.ContainsKey( finderName );
        }

        private void
        InitializeFinders(IList<InMemoryFinder<T>> finders)
        {
            foreach (InMemoryFinder<T> finder in finders)
                itsFinders.Add( finder.Name,finder );
        }


    }
}

//  ##########################################################################
