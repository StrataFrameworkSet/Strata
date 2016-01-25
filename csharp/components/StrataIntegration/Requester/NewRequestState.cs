//  ##########################################################################
//  # File Name: NewRequestState.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
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
    class NewRequestState:
        AbstractRequestState
    {
        private static IRequestState instance = new NewRequestState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a singleton NewRequestState object.
        /// </summary>
        /// 
        private NewRequestState()
            : base("NewRequest")
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.SendInput(IRequest, IMessage, IMessageSender)"/>
        /// 
        public override void SendInput(IRequest request, String input)
        {
            Request castedRequest = (Request)request;

            castedRequest.Input = input;

            ChangeState(
                castedRequest, 
                new RequestStateTransition(
                    request, this, new PendingReplyState(castedRequest)));

        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.ReceiveOutput(IRequest, IMessage, ReceiveAction)"/>
        /// 
        public override void ReceiveOutput(IRequest request, IStringMessage output, ReceiveAction action)
        {
            throw new InvalidOperationException();
        }



        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the singleton PendingReplyState object.
        /// </summary>
        /// 
        public static IRequestState GetInstance()
        {
            return instance;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRequestState.ChangeState(Request, RequestStateTransition)"/>
        /// 
        protected override void ChangeState(Request request, RequestStateTransition transition)
        {
            if (transition.Destination is PendingReplyState)
            {
                request.ChangeState(transition);
                return;
            }

            throw new InvalidOperationException();
        }
    }
}

//  ##########################################################################
