//  ##########################################################################
//  # File Name: CommittedUnitOfWorkState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using Strata.Domain.NamedQuery;

namespace Strata.Domain.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class CommittedUnitOfWorkState:
        AbstractUnitOfWorkState
    {
        private static readonly CommittedUnitOfWorkState instance = 
            new CommittedUnitOfWorkState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>CommittedUnitOfWorkState</c> instance.
        /// </summary>
        /// 
        private
        CommittedUnitOfWorkState():
            base("Committed") {}

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
        IsCommitted(AbstractUnitOfWork context)
        {
            return true;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public static CommittedUnitOfWorkState
        GetInstance()
        {
            return instance;
        }
    }
}

//  ##########################################################################
