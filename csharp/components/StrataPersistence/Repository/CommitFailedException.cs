//  ##########################################################################
//  # File Name: CommitFailedException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates an invocation of 
    /// <c>IUnitOfWork.Commit()</c> failed.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class CommitFailedException:
        RepositoryException
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
