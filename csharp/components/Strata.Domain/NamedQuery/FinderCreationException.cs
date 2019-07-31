//  ##########################################################################
//  # File Name: FinderCreationException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.NamedQuery
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
        Exception
    {
        public 
        FinderCreationException(string message):
            base(message) {}

        public 
        FinderCreationException(string message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
