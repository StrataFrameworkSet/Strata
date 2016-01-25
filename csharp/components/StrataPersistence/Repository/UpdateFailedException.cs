//  ##########################################################################
//  # File Name: UpdateFailedException.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates that updating an entity 
    /// in a repository has failed.
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class UpdateFailedException:
        RepositoryException
    {
        public 
        UpdateFailedException(String message):
            base(message) {}

        public 
        UpdateFailedException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
