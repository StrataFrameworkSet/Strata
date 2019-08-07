//  ##########################################################################
//  # File Name: NhibernateUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Linq;
using System.Collections.Generic;
using Strata.Persistence.Repository;
using NHibernate;
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
    class NhibernateUnitOfWork:
        IUnitOfWork
    {
        private ISession     session;
        private ITransaction transaction;

        public IDictionary<String,String> ErrorList { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateUnitOfWork</c> instance.
        /// </summary>
        /// 
        public 
        NhibernateUnitOfWork(ISessionFactory factory)
        {
            ErrorList = new Dictionary<string, string>();
            session     = factory.OpenSession();
            transaction = session.Transaction;
            transaction.Begin();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.GetRepositoryMethod"/>
        /// 
        public IRepositoryMethod 
        GetRepositoryMethod(string name)
        {
            return new NhibernateRepositoryMethod( name,this );
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.Commit()"/>
        /// 
        public void 
        Commit()
        {
            try
            {
                session.Flush();
                transaction.Commit();

                if ( session.IsOpen )
                    session.Close();
            }
            catch (Exception e)
            {
                throw new CommitFailedException("commit failed",e);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.Rollback()"/>
        /// 
        public void 
        Rollback()
        {
            try
            {
                transaction.Rollback();
            }
            catch (Exception e)
            {
                throw new RollbackFailedException("rollback failed",e);
            } 
            finally
            {
                if ( session.IsOpen )
                    session.Close();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.IsActive()"/>
        /// 
        public bool 
        IsActive()
        {
            return transaction.IsActive;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.IsCommitted()"/>
        /// 
        public bool 
        IsCommitted()
        {
            return transaction.WasCommitted;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IUnitOfWork.IsRolledBack()"/>
        /// 
        public bool 
        IsRolledBack()
        {
            return transaction.WasRolledBack;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoInsert<T>(T entity)
            where T:class
        {
            return session.Get<T>(session.Save(entity));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoUpdate<T>(T entity)
            where T:class
        {
            if ( session.Contains( entity ) )
                session.Evict( entity );

            session.Update( entity );
            return session.Merge(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void 
        DoRemove<T>(T entity)
            where T:class
        {
            session.Delete(session.Merge(entity));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoGet<K,T>(K key)
            where T:class
        {
            return session.Get<T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IQueryable<T> 
        DoQuery<T>()
            where T:class
        {
            return session.Query<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IQuery 
        DoGetNamedQuery(String name)
        {
            return session.GetNamedQuery( name );
        }
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool 
        DoHas<K,T>(K key)
            where T:class
        {
            return session.Get<T>(key) != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public ISession
        GetSession()
        {
            return session;
        }
    }
}

//  ##########################################################################
