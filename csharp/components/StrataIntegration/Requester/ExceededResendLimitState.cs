//  ##########################################################################
//  # File Name: ExceededResendLimitState.cs
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
    class ExceededResendLimitState:
        AbstractRequestState
    {
        private static IRequestState instance = new ExceededResendLimitState();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new ExceededResendLimitState object.
        /// </summary>
        /// 
        private ExceededResendLimitState()
            : base("ExceededResendLimit")
        {
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
        /// <summary>
        /// Returns the singleton ExceededResendLimitState object.
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
            IRequestState destination = transition.Destination;

            if (destination == ReceivedReplyState.GetInstance())
            {
                request.ChangeState(transition);
                return;
            }

            throw new InvalidOperationException();
        }
    }
}

//  ##########################################################################
