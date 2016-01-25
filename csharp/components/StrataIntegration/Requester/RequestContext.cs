//  ##########################################################################
//  # File Name: RequestContext.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Requester
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides a default implementation of <c>IRequestContext</c>.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class RequestContext:
        IRequestContext
    {
        public IRequestRepository  Repository { get; protected set; }
        public IMessagingSession   Messaging { get; protected set; }

        public String              RequestChannelId { get; protected set; }
        public String              ReplyChannelId { get; protected set; }

        public int                 ResendTimeout { get; protected set; }
        public int                 ResendLimit { get; protected set; }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new request context.
        /// </summary>
        /// 
        /// <param name="repository"></param>
        /// <param name="messaging"></param>
        /// <param name="requestChannelId"></param>
        /// <param name="replyChannelId"></param>
        /// <param name="timeout"></param>
        /// <param name="limit"></param>
        public 
	    RequestContext(IRequestRepository repository, IMessagingSession messaging, string requestChannelId, string replyChannelId, int timeout, int limit)
	    {
            Repository       = repository;
            Messaging        = messaging;
            RequestChannelId = requestChannelId;
            ReplyChannelId   = replyChannelId;
            ResendTimeout    = timeout;
            ResendLimit      = limit;
	    }

    }
}

//  ##########################################################################
