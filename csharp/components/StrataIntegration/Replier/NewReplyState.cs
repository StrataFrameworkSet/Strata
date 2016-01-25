//  ##########################################################################
//  # File Name: NewReplyState.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// The initial state of a reply object.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class NewReplyState:
        AbstractReplyState
    {
        private static IReplyState instance = new NewReplyState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a singleton NewReplyState object.
        /// </summary>
        /// 
        private 
        NewReplyState():
            base("NewReply") {}

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.ReceiveInput"/>
        /// 
        public override void 
        ReceiveInput(IReply reply, IStringMessage input, ReceiveAction action)
        {
            Reply castedReply = (Reply) reply;

		    castedReply.Input = input.Payload;
            castedReply.Action = action;
		    
		    ChangeState(
			    castedReply,
			    new ReplyStateTransition( 
				    reply,
				    this,
				    ReceivedRequestState.GetInstance() ) ); 
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the singleton NewReplyState object.
        /// </summary>
        /// 
        public static IReplyState 
        GetInstance()
        {
            return instance;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see 
        /// cref=
        /// "AbstractReplyState.ChangeState(Reply, ReplyStateTransition)"/>
        /// 
        protected override void 
        ChangeState(Reply context,ReplyStateTransition transition)
        {
            if (transition.Destination is ReceivedRequestState)
            {
                context.ChangeState(transition);
                return;
            }

            throw new InvalidOperationException();
        }
    }
}

//  ##########################################################################
