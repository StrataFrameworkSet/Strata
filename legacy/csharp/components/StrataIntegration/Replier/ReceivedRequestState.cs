//  ##########################################################################
//  # File Name: PendingReplyState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// State indicating a request has been received.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class ReceivedRequestState:
        AbstractReplyState
    {
        private static IReplyState instance = new ReceivedRequestState();
        
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new PendingReplyState object.
        /// </summary>
        /// 
        private 
        ReceivedRequestState():
            base("ReceivedRequest") {}

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.EnterState(IReply,ReplyStateTransition)"/>
        /// <summary>
        /// Execute the reply.ReceiveAction.
        /// </summary>
        /// 
        public override void 
        EnterState(IReply reply, ReplyStateTransition transition)
        {
            Reply castedReply = (Reply)reply;

            if ( castedReply.Action != null )
                castedReply.Action(castedReply.Input);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.ReceiveInput"/>
        /// <summary>
        /// Reply has already received a request so duplicates are ignored.
        /// </summary>
        /// 
        public override void 
	    ReceiveInput(IReply reply, IStringMessage input, ReceiveAction action) {}

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.SendOutput"/>
        /// 
        public override void 
        SendOutput(IReply reply,String output)
        {
            Reply castedReply = (Reply)reply;

            castedReply.Output = output;

            ChangeState(
                castedReply, 
                new ReplyStateTransition(
                    castedReply,
                    this,
                    SentReplyState.GetInstance()));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the singleton PendingReplyState object.
        /// </summary>
        /// 
        public static IReplyState 
        GetInstance()
        {
            return instance;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractReplyState.ChangeState(Reply, ReplyStateTransition)"/>
        /// 
        protected override void 
	    ChangeState(Reply context,ReplyStateTransition transition) 
	    {
		    IReplyState destination = transition.Destination;
		
		    if ( destination is SentReplyState )
		    {
			    context.ChangeState( transition );
			    return;
		    }
		
		    throw new InvalidOperationException();
	    }
    }
}

//  ##########################################################################
