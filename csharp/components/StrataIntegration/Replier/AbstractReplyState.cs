//  ##########################################################################
//  # File Name: AbstractReplyState.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all reply states.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractReplyState :
        IReplyState
    {
        public String Name { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a state object with the specified name.
        /// </summary>
        /// 
        protected 
        AbstractReplyState(String name)
        {
            Name = name;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.EnterState(IReply,ReplyStateTransition)"/>
        /// <summary>
        /// Subclasses must override to provide behavior if needed when 
        /// entering a state.
        /// </summary>
        /// 
        public virtual void 
        EnterState(IReply reply, ReplyStateTransition transition) {}

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.ExitState(IReply, ReplyStateTransition)"/>
        /// <summary>
        /// Subclasses must override to provide behavior if needed when 
        /// exiting a state.
        /// </summary>
        /// 
        public virtual void 
        ExitState(IReply reply, ReplyStateTransition transition) {}

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.ReceiveInput"/>
        /// <summary>
        /// Subclasses must override if they represent a state where
        /// <c>ReceiveInput</c> is valid;
        /// </summary>
        /// 
        public virtual void 
        ReceiveInput(IReply reply,IStringMessage input,ReceiveAction action)
        {
            throw 
                new InvalidOperationException(
                    "Cannot call ReceiveInput(IMessage,ReceiveAction) in the " + 
                    Name + 
                    " state.");
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReplyState.SendOutput"/>
        /// <summary>
        /// Subclasses must override if they represent a state where
        /// <c>SendOutput</c> is valid;
        /// </summary>
        /// 
        public virtual void 
        SendOutput(IReply reply,String output)
        {
            throw 
                new InvalidOperationException(
                    "Cannot call SendOutput(String) in the " + 
                    Name + 
                    " state.");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Subclasses must override to provide state-specific
        /// transition logic.
        /// </summary>
        /// 
        protected abstract void 
        ChangeState(Reply context,ReplyStateTransition transition);
    }
}

//  ##########################################################################
