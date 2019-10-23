//  ##########################################################################
//  # File Name: IDomainEventSource.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Domain.Core.Shared
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IDomainEventSource<S,E,O>
        where S: IDomainEventSource<S,E,O>
        where E: IDomainEvent<S>
        where O: IDomainEventObserver<E>
    {
        ISet<O> Observers { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        S
        AttachFrom(S other);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        S
        Attach(ISet<O> observers);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        S
        Attach(O observer);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        S
        Detach(O observer);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        bool
        Has(O observer);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        S
        Notify(E evnt);

    }
}

//  ##########################################################################
