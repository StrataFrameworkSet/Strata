//  ##########################################################################
//  # File Name: IReply.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Common.Utility;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    public delegate void ReceiveAction(String payload);

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represents a reply to a request.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IReply:
        ICopyable
    {
        String            CorrelationId { get; }
        IReplyContext     Context { get;set; }
        IReplyRepository  Repository { get; }
        IMessagingSession Factory { get; }
        String            Input { get; }
        String            Output { get; }
        IReplyState       CurrentState { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Receives message and executes the specified action if
        /// in the appropriate state.
        /// </summary>
        /// 
        /// <exception cref="InvalidOperationException"/>
        /// 
        void 
        ReceiveInput(IStringMessage input, ReceiveAction action);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Send the specified message using the specified sender.  
        /// Stores both the output and the sender for possible resending.
        /// </summary>
        /// 
        /// <exception cref="InvalidOperationException"/>
        /// 
        void 
        SendOutput(String output);
    }
}

//  ##########################################################################
