//  ##########################################################################
//  # File Name: AbstractRequestFactory.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Integration.Messaging;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all reply factories. Sub-classes must implement
    /// the <c>CreateReplyContext()</c> method.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract 
    class AbstractReplyFactory:
        IReplyFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract IReplyContext 
        CreateReplyContext(IMessagingSession session);

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IReply 
        CreateReply(String id,IReplyContext context)
        {
            return new Reply(id,context);
        }
    }
}

//  ##########################################################################
