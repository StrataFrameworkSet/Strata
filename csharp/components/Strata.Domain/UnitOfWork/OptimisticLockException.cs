//  ##########################################################################
//  # File Name: OptimisticLockException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates optimistic lock contention for an entity.
    /// </summary>
    ///  
    public
    class OptimisticLockException:
        CommitFailedException
    {
        public
        OptimisticLockException(String message):
            base(message) {}

        public
        OptimisticLockException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
