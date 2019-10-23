//  ##########################################################################
//  # File Name: RolledBackUnitOfWorkState.cs
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
    class RolledBackUnitOfWorkState:
        AbstractUnitOfWorkState
    {
        private static readonly RolledBackUnitOfWorkState instance = 
            new RolledBackUnitOfWorkState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>RolledBackUnitOfWorkState</c> instance.
        /// </summary>
        /// 
        private
        RolledBackUnitOfWorkState():
            base("RolledBack") {}

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
        IsRolledBack(AbstractUnitOfWork context)
        {
            return true;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public static RolledBackUnitOfWorkState
        GetInstance()
        {
            return instance;
        }
    }
}

//  ##########################################################################
