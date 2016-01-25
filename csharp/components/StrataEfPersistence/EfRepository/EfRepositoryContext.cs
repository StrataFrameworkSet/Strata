//  ##########################################################################
//  # File Name: EntityFrameworkRepositoryContext.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using Strata.EfPersistence.DbContextAdapter;
using Strata.Persistence.Repository;

namespace Strata.EfPersistence.EfRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class EfRepositoryContext<D>:
        AbstractRepositoryContext,
        IEfRepositoryContext
        where D: IDataContext,new()
    {
        private EfUnitOfWork                    unitOfWork;
        private IDictionary<Type,IList<Object>> finders;
        private bool useTransactions = true;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        EfRepositoryContext() : this(true)
        {
        }

        public EfRepositoryContext(bool useTransaction)
        {
            useTransactions = useTransaction;
            unitOfWork = new EfUnitOfWork(new D(), useTransactions);
            finders = new Dictionary<Type, IList<Object>>();
        } 

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override IUnitOfWork
        GetUnitOfWork()
        {
            if ( !unitOfWork.IsActive() )
                unitOfWork = new EfUnitOfWork( new D(), useTransactions );

            return unitOfWork;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void 
        InsertFinder<T>(EfFinder<T> finder) 
            where T: class
        {
            Type type = typeof(T);

            if ( !finders.ContainsKey( type ) )
                finders.Add( type,new List<Object>() );
        
            finders[type].Add( finder );            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IList<EfFinder<T>> 
        GetFinders<T>() 
            where T: class
        {
            Type                     type = typeof(T);
            IList<EfFinder<T>> output = 
                new List<EfFinder<T>>();
        
            if ( finders.ContainsKey( type ) )
                foreach (Object finder in finders[type])
                    output.Add( (EfFinder<T>)finder );
        
            return output;
        }
    }
}

//  ##########################################################################
