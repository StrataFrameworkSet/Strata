//  ##########################################################################
//  # File Name: DapperUnitOfWorkProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Dapper.NamedQuery;
using Strata.Domain.NamedQuery;
using Strata.Domain.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Configuration;

namespace Strata.Dapper.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class DapperUnitOfWorkProvider:
        AbstractUnitOfWorkProvider
    {
        public string ConnectionString { get; protected set; }

	    private DapperUnitOfWork                                unitOfWork;
	    private readonly IDictionary<Tuple<Type,string>,string> namedQueries;
        private readonly IDictionary<Type,object>               retrievers;
        private readonly IDictionary<Type,object>               assigners;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        DapperUnitOfWorkProvider():
            this(ConfigurationManager.ConnectionStrings["Default"].ToString())
        {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        DapperUnitOfWorkProvider(string connectionString)
        {
            ConnectionString = connectionString;
            unitOfWork   = null;
            namedQueries = new Dictionary<Tuple<Type,string>,string>();
            retrievers   = new Dictionary<Type,object>();
            assigners    = new Dictionary<Type,object>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IUnitOfWork 
        GetUnitOfWork()
        {
            if ( MustCreateNewUnitOfWork() )
                unitOfWork = new DapperUnitOfWork(this);
        
            return unitOfWork;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        ClearUnitOfWork()
        {
            if ( unitOfWork != null ) 
                unitOfWork.Dispose();

            unitOfWork = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public DapperUnitOfWorkProvider
        InsertNamedQuery<T>(string queryName,string sql)
            where T:class
        {
            namedQueries.Add(new Tuple<Type,string>(typeof(T),queryName),sql);
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public DapperUnitOfWorkProvider
        InsertRetriever<K,T>(KeyRetriever<K,T> retriever)
            where T:class
        {
            retrievers.Add(typeof(T),retriever);
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public DapperUnitOfWorkProvider
        InsertAssigner<K,T>(KeyAssigner<K,T> assigner)
            where T:class
        {
            assigners.Add(typeof(T),assigner);
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public INamedQuery<T>
        GetNamedQuery<T>(string queryName) 
            where T: class
        {
            if (!HasNamedQuery<T>(queryName))
                return null;

            return 
                new DapperNamedQuery<T>( 
                    (DapperUnitOfWork)GetUnitOfWork(),
                    queryName,
                    namedQueries[new Tuple<Type,string>(typeof(T),queryName)]);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public KeyRetriever<K,T>
        GetRetriever<K,T>() 
            where T: class
        {
            return (KeyRetriever<K,T>)retrievers[typeof(T)];
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public KeyAssigner<K,T>
        GetAssigner<K,T>() 
            where T: class
        {
            return (KeyAssigner<K,T>)assigners[typeof(T)];
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        HasNamedQuery<T>(string queryName) 
            where T: class
        {
            return
                namedQueries.ContainsKey(
                    new Tuple<Type,string>(typeof(T),queryName));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private bool
        MustCreateNewUnitOfWork()
        {
            return 
                unitOfWork == null ||
                !unitOfWork.IsActive();    
        }

    }
}

//  ##########################################################################
