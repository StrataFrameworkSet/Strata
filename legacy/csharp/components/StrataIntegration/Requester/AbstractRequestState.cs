//  ##########################################################################
//  # File Name: AbstractRequestState.cs
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
    public abstract
    class AbstractRequestState :
        IRequestState
    {

        public string StateName { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a state object with the specified name.
        /// </summary>
        /// 
        public AbstractRequestState(String name)
        {
            StateName = name;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.EnterState(IRequest, RequestStateTransition)"/>
        /// 
        public virtual void EnterState(IRequest request, RequestStateTransition transition)
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.ExitState(IRequest, RequestStateTransition)"/>
        /// 
        public virtual void ExitState(IRequest request, RequestStateTransition transition)
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.SendInput(IRequest, IMessage, IMessageSender)"/>
        /// 
        public virtual void SendInput(IRequest request, String input)
        {
            throw new InvalidOperationException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IRequestState.ReceiveOutput(IRequest, IMessage, ReceiveAction)"/>
        /// 
        public virtual void ReceiveOutput(IRequest request, IStringMessage output, ReceiveAction action)
        {
            throw new InvalidOperationException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// State specific behavior when changing from one state to another.
        /// </summary>
        /// 
        protected abstract void ChangeState(Request request, RequestStateTransition transition);
    }
}

//  ##########################################################################
