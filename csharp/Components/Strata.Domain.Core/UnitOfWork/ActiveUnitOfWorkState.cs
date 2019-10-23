//  ##########################################################################
//  # File Name: ActiveUnitOfWorkState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using Strata.Domain.Core.NamedQuery;

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
        public override T
        Insert<K,T>(AbstractUnitOfWork context,T entity)
        {
            return context.DoInsert<K,T>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T
        Update<K,T>(AbstractUnitOfWork context,T entity)
        {
            return context.DoUpdate<K,T>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void
        Remove<K,T>(AbstractUnitOfWork context,T entity)
        {
            context.DoRemove<K,T>(entity);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T
        GetUniqueWithKey<K,T>(AbstractUnitOfWork context,K key)
        {
            return context.DoGetUniqueWithKey<K,T>( key );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override T
        GetUniqueWithPredicate<T>(
            AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate)
        {
            return context.DoGetUniqueWithPredicate<T>( predicate );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T>
        GetAllWithPredicate<T>(
            AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate)
        {
            return context.DoGetAllWithPredicate<T>( predicate );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override IList<T>
        GetAll<T>(AbstractUnitOfWork context)
        {
            return context.DoGetAll<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override INamedQuery<T>
        GetNamedQuery<T>(AbstractUnitOfWork context,string queryName)
        {
            return context.DoGetNamedQuery<T>( queryName );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool
        HasUniqueWithKey<K,T>(AbstractUnitOfWork context,K key)
        {
            return context.DoHasUniqueWithKey<K,T>( key );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool
        HasUniqueWithPredicate<T>(
            AbstractUnitOfWork context,
            Expression<Func<T,bool>> predicate)
        {
            return context.DoHasAnyWithPredicate<T>( predicate );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override bool
        HasNamedQuery<T>(AbstractUnitOfWork context,string queryName)
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
        public override void
        Commit(AbstractUnitOfWork context)
        {
            try
            {
                context.DoCommit();
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
        public override void
        Rollback(AbstractUnitOfWork context)
        {
            try
            {
                context.DoRollback();
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
