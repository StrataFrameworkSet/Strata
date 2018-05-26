//  ##########################################################################
//  # File Name: IRequestFactory.cs
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
    public
    interface IRequestFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates request context.
        /// </summary>
        /// 
        /// <param name="session"></param>
        IRequestContext 
        CreateRequestContext(IMessagingSession session);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a request.
        /// </summary>
        /// 
        /// <param name="id"></param>
        /// <param name="context"></param>
        /// 
        IRequest 
        CreateRequest(String id,IRequestContext context);
    }
}

//  ##########################################################################
