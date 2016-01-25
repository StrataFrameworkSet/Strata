//  ##########################################################################
//  # File Name: IMessagingProxy.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Integration.MessagingProxy
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IMessagingProxy
    {
        String ReturnAddress { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        void
        Activate();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        void
        Deactivate();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        void
        Close();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        bool
        IsActivated();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        bool
        HasPendingReceivers();
    }
}

//  ##########################################################################
