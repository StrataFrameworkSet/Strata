//  ##########################################################################
//  # File Name: NhibernateUnitOfWorkProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Reflection;
using NHibernate;
using NHibernate.Cfg;
using Strata.Domain.UnitOfWork;

namespace Strata.Nhibernate.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class NhibernateUnitOfWorkProvider:
        AbstractUnitOfWorkProvider
    {
        private ISessionFactory      factory;
        private NhibernateUnitOfWork unitOfWork;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateUnitOfWorkProvider</c> instance.
        /// </summary>
        /// 
        public
        NhibernateUnitOfWorkProvider(ISessionFactory f,bool useCache=false)
        {
            factory = f;
            
            if ( useCache )
                ConfigureCache();

            unitOfWork = new NhibernateUnitOfWork(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateUnitOfWorkProvider</c> instance.
        /// </summary>
        /// 
        public
        NhibernateUnitOfWorkProvider(Type[] entityTypes,bool useCache=false)
        {
            Configuration config = new Configuration();

            foreach (Type entityType in entityTypes)
                config.AddAssembly( Assembly.GetAssembly(entityType) );   
                 
            config.Configure();
            factory = config.BuildSessionFactory();
            
            if ( useCache )
                ConfigureCache();

            unitOfWork = new NhibernateUnitOfWork(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateUnitOfWorkProvider</c> instance.
        /// </summary>
        /// 
        public
         NhibernateUnitOfWorkProvider(String[] assemblyNames,bool useCache=false)
        {
            Configuration config = new Configuration();

            foreach (String assemblyName in assemblyNames)
                config.AddAssembly( assemblyName );   
                 
            config.Configure();
            factory = config.BuildSessionFactory();
            
            if ( useCache )
                ConfigureCache();

            unitOfWork = new NhibernateUnitOfWork(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IUnitOfWork
        GetUnitOfWork()
        {
            if ( !unitOfWork.IsActive() )
                unitOfWork = new NhibernateUnitOfWork(this);

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
        /// <summary></summary>
        /// 
        public ISessionFactory
        GetSessionFactory()
        {
            return factory;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary></summary>
        /// 
        private void
        ConfigureCache()
        {
            SqlDependency.Start( 
                factory.OpenSession().Connection.ConnectionString );
        }
    }
}

//  ##########################################################################
