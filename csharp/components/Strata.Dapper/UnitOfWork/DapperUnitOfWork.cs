//  ##########################################################################
//  # File Name: DapperUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Linq.Expressions;
using DapperExtensions;
using Strata.Domain.NamedQuery;
using Strata.Domain.UnitOfWork;

namespace Strata.Dapper.UnitOfWork
{

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class DapperUnitOfWork:
        AbstractUnitOfWork
    {
        public bool           IsDisposed { get; set; }
        public IDbConnection  Connection { get; set; }
        public IDbTransaction Transaction { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DapperUnitOfWork</c> instance.
        /// </summary>
        /// 
        /// <param name="provider">in memory entities</param>
        /// 
        public 
        DapperUnitOfWork(DapperUnitOfWorkProvider provider):
            base(provider)
        {
            IsDisposed = false;
            Connection = new SqlConnection(provider.ConnectionString);
            Connection.Open();
            Transaction = Connection.BeginTransaction();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void
        Dispose()
        {
            Transaction.Dispose();
            Connection.Dispose();
            IsDisposed = true;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoInsert<K,T>(T entity)
        {
            KeyAssigner<K,T> assigner =
                ((DapperUnitOfWorkProvider)Provider).GetAssigner<K,T>();

            assigner(entity,Connection.Insert(entity,Transaction));
            return entity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoUpdate<K,T>(T entity)
        {
            if ( !Connection.Update(entity,Transaction) )
                throw new DataException("update failed");

            return entity;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoRemove<K,T>(T entity)
        {
            if ( !Connection.Delete(entity,Transaction) )
                throw new DataException("remove failed");
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithKey<K,T>(K key)
        {
            return Connection.Get<T>(key,Transaction);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T 
        DoGetUniqueWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return
                Connection
                    .GetList<T>(predicate,null,Transaction)
                    .Single();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T> 
        DoGetAllWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return 
                Connection
                    .GetList<T>(predicate,null,Transaction)
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T> 
        DoGetAll<T>()
        {
            return 
                Connection
                    .GetList<T>(null,null,Transaction)
                    .ToList();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override INamedQuery<T> 
        DoGetNamedQuery<T>(string queryName)
        {
            return
                ((DapperUnitOfWorkProvider) Provider)
                    .GetNamedQuery<T>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasUniqueWithKey<K,T>(K key)
        {
            return Connection.Get<T>(key,Transaction) != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasAnyWithPredicate<T>(Expression<Func<T,bool>> predicate)
        {
            return Connection.Count<T>(predicate,Transaction) > 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool 
        DoHasNamedQuery<T>(string queryName)
        {
            return 
                ((DapperUnitOfWorkProvider)Provider)
                    .HasNamedQuery<T>(queryName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        DoCommit()
        {
            try
            {
                Transaction.Commit();
            }
            catch (Exception cause)
            {
                throw new CommitFailedException("commit failed",cause);
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
                Transaction.Rollback();
                base.DoRollback();
            }
            catch (RollbackFailedException)
            {
                throw;
            }
            catch (Exception cause)
            {
                throw new RollbackFailedException("rollback failed",cause);
            }
        }

    }
}

//  ##########################################################################
