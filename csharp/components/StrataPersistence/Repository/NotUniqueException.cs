//  ##########################################################################
//  # File Name: NotUniqueException.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
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
    class NotUniqueException:
        RepositoryException
    {
        public 
        NotUniqueException(String message):
            base(message) {}

        public 
        NotUniqueException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
