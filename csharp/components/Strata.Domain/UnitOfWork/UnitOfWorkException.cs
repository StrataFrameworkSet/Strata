//  ##########################################################################
//  # File Name: UnitOfWorkException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class UnitOfWorkException:
        Exception
    {
        protected 
        UnitOfWorkException(String message):
            base(message) {}

        protected 
        UnitOfWorkException(String message,Exception cause):
            base(message,cause) {}
    }
}

//  ##########################################################################
