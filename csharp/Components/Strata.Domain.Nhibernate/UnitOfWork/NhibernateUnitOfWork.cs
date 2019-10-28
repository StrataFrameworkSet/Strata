//  ##########################################################################
//  # File Name: NhibernateUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using NHibernate;
using NHibernate.Linq;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.UnitOfWork;
using Strata.Domain.Nhibernate.NamedQuery;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Nhibernate.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
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
        public override async Task<E> 
        DoInsert<K,E>(E entity)
        {
            E inserted = 
                await 
                    session
                        .GetAsync<E>(session.Save(entity))
                        .ConfigureAwait(false);

            return inserted;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<E> 
        DoUpdate<K,E>(E entity)
        {
            E updated = 
                await 
                    session
                        .MergeAsync(entity)
                        .ConfigureAwait(false);

            return updated;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task
        DoRemove<K,E>(E entity)
        {
            E merged =
                await 
                    session
                        .MergeAsync(entity)
                        .ConfigureAwait(false);
            
            await
                session
                    .DeleteAsync(merged)
                    .ConfigureAwait(false);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<Optional<E>> 
        DoGetUnique<K,E>(K key)
        {
            E requested =  
                await 
                    session
                        .GetAsync<E>(key)
                        .ConfigureAwait(false);

            return Optional<E>.OfNullable(requested);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<Optional<E>> 
        DoGetUniqueMatching<E>(Expression<Func<E,bool>> predicate)
        {
            E requested = 
                await
                    session
                        .Query<E>()
                        .Where(predicate)
                        .SingleOrDefaultAsync()
                        .ConfigureAwait(false);

            return Optional<E>.OfNullable(requested);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<IList<E>> 
        DoGetMatching<E>(Expression<Func<E,bool>> predicate)
        {
            return
                await 
                    session
                        .Query<E>()
                        .Where(predicate)
                        .ToListAsync()
                        .ConfigureAwait(false);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<IList<E>> 
        DoGetAll<E>()
        {
            return
                await 
                    session
                        .Query<E>()
                        .Where( entity => true)
                        .ToListAsync()
                        .ConfigureAwait(false);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<INamedQuery<E>> 
        DoGetNamedQuery<E>(string queryName)
        {
            return 
                Task.FromResult<INamedQuery<E>>(
                    new NhibernateNamedQuery<E>(queryName,this));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<bool> 
        DoHasUnique<K,E>(K key)
        {
            return 
                await 
                    session
                        .GetAsync<E>(key)
                        .ConfigureAwait(false) != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task<bool> 
        DoHasMatching<E>(Expression<Func<E,bool>> predicate)
        {
            return
                await
                    session
                        .Query<E>()
                        .Where(predicate)
                        .AnyAsync()
                        .ConfigureAwait(false);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> 
        DoHasNamedQuery<E>(string queryName)
        {
            return 
                Task.FromResult(
                    session.GetNamedQuery(queryName) != null);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task 
        DoCommit()
        {
            try
            {
                await session.FlushAsync().ConfigureAwait(false);
                await transaction.CommitAsync().ConfigureAwait(false);

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
        public override async Task 
        DoRollback()
        {
            try
            {
                await transaction.RollbackAsync();
                await base.DoRollback();
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
