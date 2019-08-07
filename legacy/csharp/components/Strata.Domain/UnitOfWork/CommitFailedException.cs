//  ##########################################################################
//  # File Name: CommitFailedException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates an invocation of 
    /// <c>IUnitOfWork.Commit()</c> failed.
    /// </summary>
    ///  
    public
    class CommitFailedException:
        UnitOfWorkException
    {
        public 
        CommitFailedException(String message):
            base(message) {}

        public 
        CommitFailedException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
