//  ##########################################################################
//  # File Name: InsertFailedException.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates that inserting an entity 
    /// into a repository has failed.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class InsertFailedException:
        RepositoryException
    {
        public 
        InsertFailedException(String message):
            base(message) {}

        public 
        InsertFailedException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
