//  ##########################################################################
//  # File Name: NhibernateUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using NHibernate;
using Strata.Nhibernate.NamedQuery;
using Strata.Domain.NamedQuery;
using Strata.Domain.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;

namespace Strata.Nhibernate.UnitOfWork
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
        AbstractUnitOfWork,
        IUnitOfWork
    {
        private ISession     session;
        private ITransaction transaction;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NhibernateUnitOfWork</c> instance.
        /// </summary>
        /// 
        public 
        NhibernateUnitOfWork(NhibernateUnitOfWorkProvider provider):
            base(provider)
        {
            session     = provider.GetSessionFactory().OpenSession();
            transaction = session.Transaction;
            transaction.Begin();
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
        public ISession
        GetSession()
        {
            return session;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        Dispose()
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoInsert<K,T>(T entity)
        {
            return session.Get<T>(session.Save(entity));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoUpdate<K,T>(T entity)
        {
            /*
            if (session.Contains(entity))
                session.Evict(entity);
                */
            return session.Merge(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoRemove<K,T>(T entity)
        {
            session.Delete(session.Merge(entity));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithKey<K,T>(K key)
        {
            return session.Get<T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return 
                session
                    .Query<T>()
                    .Where(predicate)
                    .SingleOrDefault();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T> 
        DoGetAllWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return
                session
                    .Query<T>()
                    .Where(predicate)
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T> 
        DoGetAll<T>()
        {
            return
                session
                    .Query<T>()
                    .Where( entity => true)
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override INamedQuery<T> 
        DoGetNamedQuery<T>(string queryName)
        {
            return new NhibernateNamedQuery<T>(queryName,this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasUniqueWithKey<K,T>(K key)
        {
            return session.Get<T>(key) != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasAnyWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return
                session
                    .Query<T>()
                    .Where(predicate)
                    .Any();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasNamedQuery<T>(string queryName)
        {
            return session.GetNamedQuery(queryName) != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoCommit()
        {
            try
            {
                session.Flush();
                transaction.Commit();

                if (session.IsOpen)
                    session.Close();
            }
            catch (StaleObjectStateException e)
            {
                throw 
                    new OptimisticLockException(
                        "optimistic lock contention",
                        e);
            }
            catch (Exception e)
            {
                throw new CommitFailedException("commit failed",e);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoRollback()
        {
            try
            {
                transaction.Rollback();
                base.DoRollback();
            }
            catch (RollbackFailedException)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new RollbackFailedException("rollback failed",e);
            }
            finally
            {
                if (session.IsOpen)
                    session.Close();
            }
        }
    }
}

//  ##########################################################################
