//  ##########################################################################
//  # File Name: FinderCreationException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Exception that indicates that removing an entity 
    /// from a repository has failed.
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class FinderCreationException:
        RepositoryException
    {
        public 
        FinderCreationException(String message):
            base(message) {}

        public 
        FinderCreationException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
