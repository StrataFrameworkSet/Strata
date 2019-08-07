//  ##########################################################################
//  # File Name: Reply.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Common.Utility;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides a default implementation of <c>IReply</c>.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class Reply:
        IReply
    {
        public virtual String            CorrelationId { get; set; }
        public virtual IReplyContext     Context { get; set; }

        public virtual IReplyRepository  Repository 
        { 
            get { return Context != null ? Context.Repository : null; }  
        }
        public virtual IMessagingSession Factory
        { 
            get { return Context != null ? Context.Messaging : null; }  
        }

        public virtual String            Input { get; set; }
        public virtual String            Output { get; set; }
        public virtual ReceiveAction     Action { get; set; }

        public virtual IReplyState       CurrentState { get; protected set; }

        public virtual String CurrentStateName
        {
            get { return CurrentState.Name; }
            set
            {
                if ( NewReplyState.GetInstance().Name.Equals(value))
                    CurrentState = NewReplyState.GetInstance();
                else if ( ReceivedRequestState.GetInstance().Name.Equals(value))
                    CurrentState = ReceivedRequestState.GetInstance();
                else if ( SentReplyState.GetInstance().Name.Equals(value))
                    CurrentState = SentReplyState.GetInstance();
                else
                    throw new ArgumentException(value + " is not a recogized Reply State.");
            }
        }

        private Object lockObject;

        public 
        Reply()
        {
            CorrelationId = String.Empty;
            Context = null;
            Input = String.Empty;
            Output = String.Empty;
            lockObject = new Object();
            CurrentState = NewReplyState.GetInstance();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a request with the specified repository.
        /// </summary>
        /// 
        /// <param name="repository"></param>
        /// 
        public 
        Reply(String id,IReplyContext context)
        {
            CorrelationId = id;
            Context = context;
            Input = String.Empty;
            Output = String.Empty;
            lockObject = new Object();
            CurrentState = NewReplyState.GetInstance();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public virtual bool 
        Equals(Reply other)
        {
            if (ReferenceEquals( null,other )) return false;
            if (ReferenceEquals( this,other )) return true;

            return 
                Equals( other.CorrelationId,CorrelationId ) && 
                Equals( other.Context,Context ) && 
                Equals( other.Input,Input ) && 
                Equals( other.Output,Output ) && 
                Equals( other.CurrentState,CurrentState ) && 
                Equals( other.Action,Action );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public override bool 
        Equals(Object obj)
        {
            if (ReferenceEquals( null,obj )) return false;
            if (ReferenceEquals( this,obj )) return true;
            if (obj.GetType() != typeof(Reply)) return false;
            return Equals( (Reply)obj );
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
                int result = (CorrelationId != null ? CorrelationId.GetHashCode() : 0);
                result = (result*397) ^ (Context != null ? Context.GetHashCode() : 0);
                result = (result*397) ^ (Input != null ? Input.GetHashCode() : 0);
                result = (result*397) ^ (Output != null ? Output.GetHashCode() : 0);
                result = (result*397) ^ (CurrentState != null ? CurrentState.GetHashCode() : 0);
                result = (result*397) ^ (Action != null ? Action.GetHashCode() : 0);
                return result;
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ICloneable.Clone()"/>
        /// 
        public override String 
        ToString()
        {
            return 
                String.Format( 
                    "CorrelationId: {0}, Context: {1}, Input: {2}, " + 
                    "Output: {3}, Action: {4}, CurrentState: {5}",
                    CorrelationId,
                    Context,
                    Input,
                    Output,
                    Action,
                    CurrentState );
        }


        //////////////////////////////////////////////////////////////////////
        /// <see cref="ICloneable.Clone()"/>
        /// 
        public virtual ICopyable
        MakeCopy()
        {
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ICloneable.Clone()"/>
        /// 
        public virtual Object
        Clone()
        {
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReply.ReceiveInput(IMessage,ReceiveAction)"/>
        /// 
        public virtual void 
        ReceiveInput(IStringMessage input, ReceiveAction action)
        {
            lock (lockObject)
            {
                CurrentState.ReceiveInput(this, input, action);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IReply.SendOutput(IMessage,IMessageSender)"/>
        /// 
        public virtual void 
        SendOutput(String output)
        {
            lock (lockObject)
            {
                CurrentState.SendOutput( this,output );
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Changes state by applying the specified transition.
        /// </summary>
        /// 
        /// <param name="transition"></param>
        /// 
        public virtual void 
        ChangeState(ReplyStateTransition transition)
        {
            lock (lockObject)
            {
                CurrentState.ExitState(this, transition);
                CurrentState = transition.Destination;
                Repository.InsertOrUpdateReply(this);
                CurrentState.EnterState(this, transition);
            }
        }
   }
}

//  ##########################################################################
