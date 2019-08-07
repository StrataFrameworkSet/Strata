//  ##########################################################################
//  # File Name: NhibernateUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Data.SqlClient;
using System.Linq;
using System.Reflection;
using System.Transactions;
using Strata.Domain.NamedQuery;
using System.Linq.Expressions;
using System.Data.Entity.Migrations;

namespace Strata.EntityFramework.UnitOfWork
{
    using IsolationLevel = System.Transactions.IsolationLevel;
    using CommitFailedException = Strata.Domain.UnitOfWork.CommitFailedException;
    using EntityState = System.Data.Entity.EntityState;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class EfUnitOfWork:
        AbstractUnitOfWork,
        IUnitOfWork
    {
        private IDataContext     session;
        private TransactionScope transaction;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>EfUnitOfWork</c> instance.
        /// </summary>
        /// 
        public
        EfUnitOfWork(IEfUnitOfWorkProvider provider,IDataContext data):
            base(provider)
        {
            session = data;
            transaction = new TransactionScope(
                                  TransactionScopeOption.Required,
                                  new TransactionOptions
                                  {
                                      IsolationLevel =
                                          IsolationLevel.ReadUncommitted
                                  });
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IList<T> 
        DoQuery<T>(String query,IDictionary<String,Object> inputs)
            where T:class
        {
            DbSet<T> entities = (DbSet<T>)session.GetDbSet<T>();

            return 
                entities
                    .SqlQuery( query,ToSqlParameters( inputs ) )
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IList<T> 
        DoQuery<T>(String query)
            where T:class
        {
            DbSet<T> entities = (DbSet<T>)session.GetDbSet<T>();

            return 
                entities
                    .SqlQuery( query )
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool 
        DoHas<K,T>(K key)
            where T:class
        {
            return 
                session
                    .GetDbSet<T>()
                    .Find( key ) != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private Object[]
        ToSqlParameters(IDictionary<String,Object> inputs)
        {
            List<Object> output = new List<Object>();

            foreach (KeyValuePair<String,Object> input in inputs)
                output.Add( new SqlParameter(input.Key,input.Value) );

            return output.ToArray();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        Dispose()
        {
            throw new NotImplementedException();
        }


        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoInsert<K,T>(T entity)
        {
            T inserted =
                session
                    .GetDbSet<T>()
                    .Add(entity);

            session.SaveChanges();
            return inserted;
        }
        
        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoUpdate<K,T>(T entity)
        {
            session.GetDbSet<T>().AddOrUpdate(entity);

            return session.Entry(entity).Entity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoRemove<K,T>(T entity)
        {
            session
                .GetDbSet<T>()
                .Remove(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithKey<K,T>(K key)
        {
            return
                session
                    .GetDbSet<T>()
                    .Find(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return
                session
                    .GetDbSet<T>()
                    .AsQueryable()
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
                    .GetDbSet<T>()
                    .AsQueryable()
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
                    .GetDbSet<T>()
                    .AsQueryable()
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override INamedQuery<T> 
        DoGetNamedQuery<T>(string queryName)
        {
            return
                ((IEfUnitOfWorkProvider)Provider)
                    .GetNamedQuery<T>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasUniqueWithKey<K,T>(K key)
        {
            return
                session
                    .GetDbSet<T>()
                    .Find(key) != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasAnyWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return
                session
                    .GetDbSet<T>()
                    .AsQueryable()
                    .Where(predicate)
                    .Any();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasNamedQuery<T>(string queryName)
        {
            return 
                ((IEfUnitOfWorkProvider)Provider).HasNamedQuery<T>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoCommit()
        {
            try
            {
                session.SaveChanges();

                if (transaction != null)
                {
                    transaction.Complete();
                    transaction.Dispose();
                }

                session.Dispose();
            }
            catch (DbUpdateConcurrencyException e)
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
                if (transaction != null)
                {
                    transaction.Dispose();
                }
                session.Dispose();
                transaction = null;
                session = null;
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
        }
    }
}

//  ##########################################################################
