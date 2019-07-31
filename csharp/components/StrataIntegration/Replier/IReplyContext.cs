//  ##########################################################################
//  # File Name: IReplyContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IReplyContext
    {
        IReplyRepository  Repository { get; }
        IMessagingSession Messaging { get; }

        String            ReplyChannelId { get; }
        String            RequestChannelId { get; }
    }
}

//  ##########################################################################
