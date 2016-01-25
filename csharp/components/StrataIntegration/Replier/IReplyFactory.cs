//  ##########################################################################
//  # File Name: IReplyFactory.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A factory to create <c>IReplyRepository</c> and 
    /// <c>IReply</c> objects.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IReplyFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates reply context.
        /// </summary>
        /// 
        /// <param name="session"></param>
        IReplyContext 
        CreateReplyContext(IMessagingSession session);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new reply with the supplied repository.
        /// </summary>
        /// 
        /// <param name="id"></param>
        /// <param name="context"></param>
        /// 
        IReply 
        CreateReply(String id,IReplyContext context);
    }
}

//  ##########################################################################
