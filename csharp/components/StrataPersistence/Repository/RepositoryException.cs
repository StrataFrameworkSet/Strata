//  ##########################################################################
//  # File Name: RepositoryException.cs
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
    public abstract
    class RepositoryException:
        Exception
    {
        public 
        RepositoryException(String message):
            base(message) {}

        public 
        RepositoryException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
