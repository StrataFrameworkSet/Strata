//  ##########################################################################
//  # File Name: IRequest.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Common.Utility;
using Strata.Integration.Messaging;

namespace Strata.Integration.Requester
{
    public delegate void ReceiveAction(IMessage message);

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IRequest:
        ICopyable
    {
        String             CorrelationId { get; }
        IRequestContext    Context { get;set; }
        IRequestRepository Repository { get; }
        IMessagingSession  Session { get; }
        int                ResendTimeout { get; }
        int                ResendLimit { get; }
        String             Input { get; }
        String             Output { get; }
        IRequestState      CurrentState { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if resend limit has been exceeded.
        /// </summary>
        /// 
        bool HasExceededResendLimit();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Send the specified message string.  
        /// </summary>
        /// 
        /// <exception cref="InvalidOperationException"/>
        /// 
        void SendInput(String input);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Receives message and executes the specified action if
        /// in the appropriate state.
        /// </summary>
        /// 
        /// <exception cref="InvalidOperationException"/>
        /// 
        void ReceiveOutput(IStringMessage output, ReceiveAction action);

    }
}

//  ##########################################################################
