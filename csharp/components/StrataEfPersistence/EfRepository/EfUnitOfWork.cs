//  ##########################################################################
//  # File Name: NhibernateUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Data.SqlClient;
using System.Linq;
using System.Reflection;
using System.Transactions;
using Strata.EfPersistence.DbContextAdapter;
using Strata.Persistence.Repository;

namespace Strata.EfPersistence.EfRepository
{
    using IsolationLevel = System.Transactions.IsolationLevel;
    using CommitFailedException = Strata.Persistence.Repository.CommitFailedException;
    using EntityState = System.Data.Entity.EntityState;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
        class EfUnitOfWork:
            IUnitOfWork
    {
        private IDataContext session;
        private TransactionScope transaction;
        private UnitOfWorkState state;

        public IDictionary<string,string> ErrorList { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>EfUnitOfWork</c> instance.
        /// </summary>
        /// 
        public
            EfUnitOfWork(IDataContext data): this( data,true ) {}

        public
            EfUnitOfWork(IDataContext data,bool useTransactions)
        {
            ErrorList = new Dictionary<string,string>();
            session = data;
            transaction = useTransactions
                              ? new TransactionScope(
                                    TransactionScopeOption.Required,
                                    new TransactionOptions
                                        {
                                            IsolationLevel =
                                                IsolationLevel.ReadUncommitted
                                        } )
                              : null;
            state = UnitOfWorkState.ACTIVE;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public IRepositoryMethod 
        GetRepositoryMethod(String name)
        {
            return null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public void 
        Commit()
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
                state = UnitOfWorkState.COMMITTED;
            }
            catch (Exception e)
            {
                throw new CommitFailedException("commit failed",e);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public void 
        Rollback()
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
                state = UnitOfWorkState.ROLLED_BACK;
            }
            catch (Exception e)
            {
                throw new RollbackFailedException("rollback failed",e);
            } 
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public bool 
        IsActive()
        {
            return state == UnitOfWorkState.ACTIVE;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public bool 
        IsCommitted()
        {
            return state == UnitOfWorkState.COMMITTED;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public bool 
        IsRolledBack()
        {
            return state == UnitOfWorkState.ROLLED_BACK;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoInsert<T>(T entity)
            where T:class
        {
            T inserted =  
                session
                    .GetDbSet<T>()
                    .Add( entity );

            session.SaveChanges();
            return inserted;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoUpdate<T>(T entity)
            where T:class
        {
            DbEntityEntry<T> entry = session.Entry(entity);

            if ( entry.State == EntityState.Detached )
            {
                session.GetDbSet<T>().Attach( entity );

                foreach (
                    PropertyInfo property in typeof(T).GetProperties())
                    entry.Property( property.Name ).IsModified = true;
            }
            else
                entry.CurrentValues.SetValues(entity);

            return entry.Entity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public void 
        DoRemove<T>(T entity)
            where T:class
        {
            session
                .GetDbSet<T>()
                .Remove( entity );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public T 
        DoGet<K,T>(K key)
            where T:class
        {
            return 
                session
                    .GetDbSet<T>()
                    .Find(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IQueryable<T> 
        DoQuery<T>()
            where T:class
        {
            return 
                session
                    .GetDbSet<T>()
                    .AsQueryable();
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
    }
}

//  ##########################################################################
