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
    /// Provides a default implementation of <c>IReplyContext</c>.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class ReplyContext:
        IReplyContext
    {
        public IReplyRepository  Repository { get; protected set; }
        public IMessagingSession Messaging { get; protected set; }
	
        public String            ReplyChannelId { get; protected set; }
        public String            RequestChannelId { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new reply context.
        /// </summary>
        /// 
        /// <param name="r"></param>
        /// <param name="m"></param>
        /// <param name="senderId"></param>
        /// <param name="receiverId"></param>
        /// <param name="repository"></param>
        public 
	    ReplyContext(IReplyRepository r, IMessagingSession m, string senderId, string receiverId)
	    {
            Repository        = r;
            Messaging         = m;
            ReplyChannelId   = senderId;
            RequestChannelId = receiverId;
	    }

    }
}

//  ##########################################################################
