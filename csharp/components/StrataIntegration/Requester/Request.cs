//  ##########################################################################
//  # File Name: Request.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Common.Utility;
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
    class Request:
        IRequest
    {
        public virtual String          CorrelationId { get; protected set; }
        public virtual IRequestContext Context { get;  set; }

        public virtual IRequestRepository 
        Repository
        {
            get
            {
                return Context != null ? Context.Repository : null;
            }
        }

        public virtual IMessagingSession 
        Session
        {
            get
            {
                return Context != null ? Context.Messaging : null;
            }
        }

        public virtual int 
        ResendTimeout
        {
            get
            {
                return Context != null ? Context.ResendTimeout : 0;
            }
        }

        public virtual int 
        ResendLimit
        {
            get
            {
                return Context != null ? Context.ResendLimit : 0;
            }
        }

        public virtual String        Input { get; set; }
        public virtual String        Output { get; set; }
        public virtual IRequestState CurrentState { get; protected set; }

        private int    resendCount;
        private Object lockObject;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a request with the configured resend timeout and limit.
        /// </summary>
        /// 
        public 
        Request(String id, IRequestContext c)
        {
            CorrelationId = id;
            Context = c;
            Input = null;
            Output = null;
            resendCount = 0;
            lockObject = new Object();
            CurrentState = NewRequestState.GetInstance();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a request with the configured resend timeout and limit.
        /// </summary>
        /// 
        public 
        Request(Request other)
        {
            CorrelationId = other.CorrelationId;
            Context = other.Context;
            Input = other.Input;
            Output = other.Output;
            resendCount = other.resendCount;
            lockObject = new Object();
            CurrentState = other.CurrentState;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public virtual bool 
        Equals(Request other)
        {
            if (ReferenceEquals( null,other )) return false;
            if (ReferenceEquals( this,other )) return true;

            return 
                Equals( other.CorrelationId,CorrelationId ) && 
                Equals( other.Context,Context ) && 
                Equals( other.Input,Input ) && 
                Equals( other.Output,Output ) && 
                Equals( other.CurrentState,CurrentState );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public override bool 
        Equals(object obj)
        {
            if (ReferenceEquals( null,obj )) return false;
            if (ReferenceEquals( this,obj )) return true;
            if (obj.GetType() != typeof(Request)) return false;

            return Equals( (Request)obj );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public override int 
        GetHashCode()
        {
            unchecked
            {
                int result = 23;

                result = (result*397) ^ (CorrelationId != null ? CorrelationId.GetHashCode() : 0);
                result = (result*397) ^ (Context != null ? Context.GetHashCode() : 0);
                result = (result*397) ^ (Input != null ? Input.GetHashCode() : 0);
                result = (result*397) ^ (Output != null ? Output.GetHashCode() : 0);
                result = (result*397) ^ (CurrentState != null ? CurrentState.GetHashCode() : 0);

                return result;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public override String 
        ToString()
        {
            return 
                String.Format( 
                    "CorrelationId: {0}, Context: {1}, " + 
                    "Input: {2}, Output: {3}, CurrentState: {4}",
                    CorrelationId,
                    Context,
                    Input,
                    Output,
                    CurrentState );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if resend limit has been exceeded.
        /// </summary>
        /// 
        public virtual bool 
        HasExceededResendLimit()
        {
            lock (lockObject)
            {
                return resendCount >= ResendLimit;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Send the specified message using the specified sender.  
        /// Stores both the input and the sender for possible resending.
        /// </summary>
        /// 
        /// <exception cref="InvalidOperationException"/>
        /// 
        public virtual void 
        SendInput(String input)
        {
            lock (lockObject)
            {
                CurrentState.SendInput(this, input);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Receives message and executes the specified action if
        /// in the appropriate state.
        /// </summary>
        /// 
        /// <exception cref="InvalidOperationException"/>
        /// 
        public virtual void 
        ReceiveOutput(IStringMessage output, ReceiveAction action)
        {
            lock (lockObject)
            {
                CurrentState.ReceiveOutput(this, output, action);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public virtual void 
        IncrementResendCount()
        {
            lock (lockObject)
            {
                ++resendCount;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public virtual void 
        ChangeState(RequestStateTransition transition)
        {
            lock (lockObject)
            {
                CurrentState.ExitState(this, transition);
                CurrentState = transition.Destination;
                Repository.InsertOrUpdateRequest(this);
                CurrentState.EnterState(this, transition);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public virtual ICopyable 
        MakeCopy()
        {
            return new Request(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public virtual Object 
        Clone()
        {
            return new Request(this);
        }
    }
}

//  ##########################################################################
