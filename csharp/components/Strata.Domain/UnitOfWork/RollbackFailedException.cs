//  ##########################################################################
//  # File Name: RollbackFailedException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Domain.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class RollbackFailedException:
        UnitOfWorkException
    {
        public IList<Exception> Causes { get; protected set; }

        public 
        RollbackFailedException(String message):
            base(message)
        {
            Causes = new List<Exception>();
        }

        public 
        RollbackFailedException(String message,Exception cause):
            base(message,cause)
        {
            Causes = new List<Exception>();
        }
    }
}

//  ##########################################################################
