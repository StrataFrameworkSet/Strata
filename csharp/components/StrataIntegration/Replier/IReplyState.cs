//  ##########################################################################
//  # File Name: IReplyState.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IReplyState
    {
        String Name { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes actions appropriate for a reply entering a state.
        /// </summary>
        /// 
        void EnterState(IReply reply, ReplyStateTransition transition);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes actions appropriate for a reply exiting a state.
        /// </summary>
        /// 
        void ExitState(IReply reply, ReplyStateTransition transition);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// State specific behavior for receiving message.
        /// </summary>
        /// 
        void ReceiveInput(IReply reply, IStringMessage input, ReceiveAction action);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// State specific behavior for sending the specified message 
        /// using the specified sender.  
        /// </summary>
        /// 
        void SendOutput(IReply reply,String output);
    }
}

//  ##########################################################################
