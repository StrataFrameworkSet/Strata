//  ##########################################################################
//  # File Name: NotUniqueException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.Core.NamedQuery
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
        Exception
    {
        public 
        NotUniqueException(string message):
            base(message) {}

        public 
        NotUniqueException(string message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
