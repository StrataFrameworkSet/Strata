//  ##########################################################################
//  # File Name: ReceivedReplyState.cs
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
    class ReceivedReplyState:
        AbstractRequestState
    {

        private static IRequestState instance = new ReceivedReplyState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new ReceivedReplyState object.
        /// </summary>
        /// 
        private ReceivedReplyState()
            : base("ReceivedReply")
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.ReceiveOutput(IRequest, IMessage, ReceiveAction)"/>
        /// 
        public override void 
	    ReceiveOutput(IRequest request, IStringMessage output, ReceiveAction action)
	    {
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the singleton ReceivedReplyState object.
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
            throw new InvalidOperationException();
        }
    }
}

//  ##########################################################################
