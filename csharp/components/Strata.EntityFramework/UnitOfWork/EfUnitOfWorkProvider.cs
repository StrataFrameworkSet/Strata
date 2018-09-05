//  ##########################################################################
//  # File Name: EntityFrameworkRepositoryContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.EntityFramework.NamedQuery;
using Strata.Domain.NamedQuery;
using Strata.Domain.UnitOfWork;
using System;
using System.Collections.Generic;


namespace Strata.EntityFramework.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class EfUnitOfWorkProvider<D>:
        AbstractUnitOfWorkProvider,
        IEfUnitOfWorkProvider
        where D: IDataContext,new()
    {
        private EfUnitOfWork                                    unitOfWork;
        private readonly IDictionary<Tuple<Type,string>,string> namedQueries;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public EfUnitOfWorkProvider()
        {
            unitOfWork = new EfUnitOfWork(this,new D());
            namedQueries = new Dictionary<Tuple<Type,string>,string>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IUnitOfWork
        GetUnitOfWork()
        {
            if ( unitOfWork == null || !unitOfWork.IsActive() )
                unitOfWork = new EfUnitOfWork(this,new D());

            return unitOfWork;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        ClearUnitOfWork()
        {
            if (unitOfWork != null)
                unitOfWork.Dispose();

            unitOfWork = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IEfUnitOfWorkProvider
        InsertNamedQuery<T>(string queryName,string sql)
            where T : class
        {
            namedQueries.Add(new Tuple<Type,string>(typeof(T),queryName),sql);
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public INamedQuery<T>
        GetNamedQuery<T>(string queryName)
            where T : class
        {
            if (!HasNamedQuery<T>(queryName))
                return null;

            return
                new EfNamedQuery<T>(
                    (EfUnitOfWork)GetUnitOfWork(),
                    queryName,
                    namedQueries[new Tuple<Type,string>(typeof(T),queryName)]);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        HasNamedQuery<T>(string queryName)
            where T : class
        {
            return
                namedQueries.ContainsKey(
                    new Tuple<Type,string>(typeof(T),queryName));
        }

    }
}

//  ##########################################################################
