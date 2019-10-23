//  ##########################################################################
//  # File Name: IDomainEventObserver.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.Core.Shared
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IDomainEventObserver<E>
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        void
        OnEvent(E evnt);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        void
        OnException(Exception exception);
    }
}

//  ##########################################################################
