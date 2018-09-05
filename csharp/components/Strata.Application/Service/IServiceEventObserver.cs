//  ##########################################################################
//  # File Name: IServiceEventObserver.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Application.Service
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IServiceEventObserver<E>
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
