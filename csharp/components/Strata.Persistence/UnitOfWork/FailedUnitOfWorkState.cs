//  ##########################################################################
//  # File Name: FailedUnitOfWorkState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using Strata.Persistence.NamedQuery;

namespace Strata.Persistence.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class FailedUnitOfWorkState:
        AbstractUnitOfWorkState
    {
        private static readonly FailedUnitOfWorkState instance = 
            new FailedUnitOfWorkState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>FailedUnitOfWorkState</c> instance.
        /// </summary>
        /// 
        private
        FailedUnitOfWorkState():
            base("Failed") {}

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
        public override bool
        IsFailed(AbstractUnitOfWork context)
        {
            return true;
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
                throw new RollbackFailedException( e.Message,e );
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public static FailedUnitOfWorkState
        GetInstance()
        {
            return instance;
        }
    }
}

//  ##########################################################################
