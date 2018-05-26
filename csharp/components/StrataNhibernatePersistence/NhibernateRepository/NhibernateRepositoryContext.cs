//  ##########################################################################
//  # File Name: NhibernateRepositoryContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Reflection;
using Strata.Persistence.Repository;
using NHibernate;
using NHibernate.Cfg;

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
    class NhibernateRepositoryContext:
        AbstractRepositoryContext
    {
        private ISessionFactory      factory;
        private NhibernateUnitOfWork unitOfWork;

        public 
        NhibernateRepositoryContext(ISessionFactory f,bool useCache=false)
        {
            factory = f;
            
            if ( useCache )
                ConfigureCache();

            unitOfWork = new NhibernateUnitOfWork(factory);
        }

        public 
        NhibernateRepositoryContext(Type[] entityTypes,bool useCache=false)
        {
            Configuration config = new Configuration();

            foreach (Type entityType in entityTypes)
                config.AddAssembly( Assembly.GetAssembly(entityType) );   
                 
            config.Configure();
            factory = config.BuildSessionFactory();
            
            if ( useCache )
                ConfigureCache();

            unitOfWork = new NhibernateUnitOfWork(factory);
        }


        public 
        NhibernateRepositoryContext(String[] assemblyNames,bool useCache=false)
        {
            Configuration config = new Configuration();

            foreach (String assemblyName in assemblyNames)
                config.AddAssembly( assemblyName );   
                 
            config.Configure();
            factory = config.BuildSessionFactory();
            
            if ( useCache )
                ConfigureCache();

            unitOfWork = new NhibernateUnitOfWork(factory);
        }

        public override IUnitOfWork
        GetUnitOfWork()
        {
            if ( !unitOfWork.IsActive() )
                unitOfWork = new NhibernateUnitOfWork(factory);

            return unitOfWork;
        }

        public ISessionFactory
        GetSessionFactory()
        {
            return factory;
        }

        private void
        ConfigureCache()
        {
            SqlDependency.Start( 
                factory.OpenSession().Connection.ConnectionString );
        }
    }
}

//  ##########################################################################
