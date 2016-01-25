//  ##########################################################################
//  # File Name: PendingReplyState.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Threading;
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
    class PendingReplyState:
        AbstractRequestState
    {
        private Timer replyTimer;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new PendingReplyState object.
        /// </summary>
        /// 
        public PendingReplyState(Request request)
            : base("PendingReply")
        {
            replyTimer = new Timer(this.OnTimeout, request, -1, 0);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.EnterState(IRequest, RequestStateTransition)"/>
        /// 
        public override void 
	    EnterState(IRequest request,RequestStateTransition transition)
	    {
		    Request         castedRequest = (Request)request;
		    IRequestState   source  = transition.Source;

		
		    if ( 
			    (source == NewRequestState.GetInstance()) || 
			    (source is PendingReplyState) )
		    {
		        String            id = request.Context.RequestChannelId;
                IMessagingSession factory = castedRequest.Session;
                IMessageSender    sender = factory.CreateMessageSender(id);
                IStringMessage      message = factory.CreateStringMessage();

                message.CorrelationId = request.CorrelationId;
                message.Payload = request.Input;
			    sender.Send( message );
			    castedRequest.IncrementResendCount();
                startReplyTimer(castedRequest);
		    }
	    }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.ExitState(IRequest, RequestStateTransition)"/>
        /// 
        public override void 
	    ExitState(IRequest request,RequestStateTransition transition)
	    {
		    stopReplyTimer();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.ReceiveOutput(IRequest, IMessage, ReceiveAction)"/>
        /// 
        public override void 
	    ReceiveOutput(IRequest request, IStringMessage output, ReceiveAction action) 
	    {
            ((Request)request).Output = output.Payload;
		    action(output);
		    ChangeState(
                (Request)request,
			    new RequestStateTransition(
                    request,
				    this,
				    ReceivedReplyState.GetInstance() ) );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="AbstractRequestState.ChangeState(Request, RequestStateTransition)"/>
        /// 
        protected override void 
	    ChangeState(Request request,RequestStateTransition transition) 
	    {
		    IRequestState destination = transition.Destination;
		
		    if ( 
			    (destination is PendingReplyState) ||
			    (destination == ReceivedReplyState.GetInstance()) ||
			    (destination == ExceededResendLimitState.GetInstance()) )
		    {
			    request.ChangeState( transition );
			    return;
		    }
		
		    throw new InvalidOperationException();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Starts reply timer.
        /// </summary>
        /// 
        protected void
	    startReplyTimer(Request request)
	    {
		    int milliseconds = request.ResendTimeout*1000;
            replyTimer.Change(milliseconds, 0);
	    }


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Perform actions when response times out.
        /// </summary>
        /// 
        protected void OnTimeout(Object r)
        {
            Request request = (Request)r;

            RequestStateTransition transition = null;

            if (request.HasExceededResendLimit())
                transition =
                    new RequestStateTransition(
                        request,
                        request.CurrentState,
                        ExceededResendLimitState.GetInstance());
            else
                transition =
                    new RequestStateTransition(
                        request,
                        request.CurrentState,
                        request.CurrentState);

            request.ChangeState(transition);

        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Stops reply timer.
        /// </summary>
        /// 
        protected void
        stopReplyTimer()
        {
            replyTimer.Change(Timeout.Infinite, Timeout.Infinite);
        }
    }
}

//  ##########################################################################
