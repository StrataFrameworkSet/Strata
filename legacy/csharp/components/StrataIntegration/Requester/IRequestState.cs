//  ##########################################################################
//  # File Name: IRequestState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Requester
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
    interface IRequestState
    {
        String StateName { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes actions appropriate for a request entering a state.
        /// </summary>
        /// 
        void EnterState(IRequest request, RequestStateTransition transition);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes actions appropriate for a request exiting a state.
        /// </summary>
        /// 
        void ExitState(IRequest request, RequestStateTransition transition);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// State specific behavior for sending the specified message 
        /// using the specified sender.  
        /// </summary>
        /// 
        void SendInput(IRequest request, String input);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// State specific behavior for receiving message.
        /// </summary>
        /// 
        void ReceiveOutput(IRequest request, IStringMessage output, ReceiveAction action);

    }
}

//  ##########################################################################
