//  ##########################################################################
//  # File Name: ActiveUnitOfWorkState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Strata.Domain.Core.NamedQuery;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class ActiveUnitOfWorkState:
        AbstractUnitOfWorkState
    {
        private static readonly ActiveUnitOfWorkState instance = 
            new ActiveUnitOfWorkState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>ActiveUnitOfWorkState</c> instance.
        /// </summary>
        /// 
        private
        ActiveUnitOfWorkState():
            base("Active") {}

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
        public override Task<E> 
        Insert<K,E>(AbstractUnitOfWork context,E entity)
        {
            return context.DoInsert<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<E> 
        Update<K,E>(AbstractUnitOfWork context,E entity)
        {
            return context.DoUpdate<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task
        Remove<K,E>(AbstractUnitOfWork context,E entity)
        {
            return context.DoRemove<K,E>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<Optional<T>> GetUnique<K,T>(AbstractUnitOfWork context,K key)
        {
            return context.DoGetUnique<K,T>( key );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<Optional<T>> GetUniqueMatching<T>(AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate)
        {
            return context.DoGetUniqueMatching<T>( predicate );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<IList<T>> GetMatching<T>(AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate)
        {
            return context.DoGetMatching<T>( predicate );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<IList<T>> GetAll<T>(AbstractUnitOfWork context)
        {
            return context.DoGetAll<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<INamedQuery<T>> GetNamedQuery<T>(AbstractUnitOfWork context,string queryName)
        {
            return context.DoGetNamedQuery<T>( queryName );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> HasUnique<K,T>(AbstractUnitOfWork context,K key)
        {
            return context.DoHasUnique<K,T>( key );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> HasUniqueMatching<T>(AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate)
        {
            return context.DoHasMatching<T>( predicate );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override Task<bool> HasNamedQuery<T>(AbstractUnitOfWork context,string queryName)
        {
            return context.DoHasNamedQuery<T>( queryName );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool
        IsActive(AbstractUnitOfWork context)
        {
            return true;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task
        Commit(AbstractUnitOfWork context)
        {
            try
            {
                await context.DoCommit();
                context.State = CommittedUnitOfWorkState.GetInstance();
            }
            catch (Exception e)
            {
                context.State = FailedUnitOfWorkState.GetInstance();
                throw new CommitFailedException( e.Message,e );
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override async Task
        Rollback(AbstractUnitOfWork context)
        {
            try
            {
                await context.DoRollback();
                context.State = RolledBackUnitOfWorkState.GetInstance();
            }
            catch (Exception e)
            {
                throw new RollbackFailedException(e.Message,e);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public static ActiveUnitOfWorkState
        GetInstance()
        {
            return instance;
        }
    }
}

//  ##########################################################################
