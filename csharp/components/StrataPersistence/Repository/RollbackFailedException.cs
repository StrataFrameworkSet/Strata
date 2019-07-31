//  ##########################################################################
//  # File Name: RollbackFailedException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class RollbackFailedException:
        RepositoryException
    {
        public 
        RollbackFailedException(String message):
            base(message) {}

        public 
        RollbackFailedException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
