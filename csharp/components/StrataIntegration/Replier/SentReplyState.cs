//  ##########################################################################
//  # File Name: SentReplyState.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
    class SentReplyState:
        AbstractReplyState
    {
        private static IReplyState instance = new SentReplyState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new ReceivedReplyState object.
        /// </summary>
        /// 
        private 
        SentReplyState(): 
            base("SentReply")
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.EnterState(IReply,ReplyStateTransition)"/>
        /// <summary>
        /// Completes the task of sending output if the
        /// source state is <c>ReceivedRequestState</c>.
        /// </summary>
        /// 
        public override void 
	    EnterState(IReply reply, ReplyStateTransition transition)
	    {
		    IReplyState source = transition.Source;
		
		    if ( source is ReceivedRequestState )
		    {
		        String            id = reply.Context.ReplyChannelId;
		        IMessagingSession factory = reply.Factory;
		        IMessageSender    sender  = factory.CreateMessageSender(id);
		        IStringMessage      message = factory.CreateStringMessage();

		        message.CorrelationId = reply.CorrelationId;
		        message.Payload       = reply.Output;

                sender.Send(message);
		    }
	    }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.ReceiveInput"/>
        /// <summary>
        /// Already sent a reply so original reply is automatically resent
        /// for duplicate requests. 
        /// </summary>
        /// 
        public override void 
	    ReceiveInput(IReply reply, IStringMessage input, ReceiveAction action)
        {
            String            id      = reply.Context.ReplyChannelId;
		    IMessagingSession factory = reply.Factory;
		    IMessageSender    sender  = factory.CreateMessageSender(id);
		    IStringMessage      message = factory.CreateStringMessage();

		    message.CorrelationId = reply.CorrelationId;
		    message.Payload       = reply.Output;

            sender.Send(message);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the singleton ReceivedReplyState object.
        /// </summary>
        /// 
        public static IReplyState 
        GetInstance()
        {
            return instance;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractReplyState.ChangeState(Reply, ReplyStateTransition)"/>
        /// <summary>
        /// Cannot change state because 
        /// <c>SentReplyState</c> is a final state.
        /// </summary>
        /// 
        protected override void 
        ChangeState(Reply reply, ReplyStateTransition transition)
        {
            throw new InvalidOperationException();
        }
    }
}

//  ##########################################################################
