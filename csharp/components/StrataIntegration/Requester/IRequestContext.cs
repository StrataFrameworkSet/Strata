//  ##########################################################################
//  # File Name: EmsProxyInitializationContext.cs
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
    interface IRequestContext
    {
        IRequestRepository Repository { get; }
        IMessagingSession  Messaging { get; }

        String             RequestChannelId { get; }
        String             ReplyChannelId { get; }

        int                ResendTimeout { get; }
        int                ResendLimit { get; }
    }
}

//  ##########################################################################
