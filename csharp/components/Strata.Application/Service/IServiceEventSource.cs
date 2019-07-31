//  ##########################################################################
//  # File Name: IServiceEventSource.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;

namespace Strata.Application.Service
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IServiceEventSource<S,E,O>
        where S: IServiceEventSource<S,E,O>
        where E: IServiceEvent<S>
        where O: IServiceEventObserver<E>
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
