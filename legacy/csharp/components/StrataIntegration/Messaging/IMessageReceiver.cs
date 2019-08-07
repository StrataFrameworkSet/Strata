//  ##########################################################################
//  # File Name: IMessageReceiver.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
namespace Strata.Integration.Messaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IMessageReceiver
    {
        IMessageListener Listener { get; set; }
        String           Selector { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        void
        StartListening();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        void
        StopListening();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        bool
        IsListening();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        IMessage
        Receive();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        IMessage
        ReceiveWithTimeout(long milliseconds);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        IMessage
        ReceiveNoWait();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        void
        Close();
    }
}

//  ##########################################################################
