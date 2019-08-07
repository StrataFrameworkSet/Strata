//  ##########################################################################
//  # File Name: AbstractRequestFactory.cs
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
    public abstract 
    class AbstractRequestFactory:
        IRequestFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new abstract request factory.
        /// </summary>
        /// 
        public 
        AbstractRequestFactory() {}


        //////////////////////////////////////////////////////////////////////
        /// <see 
        /// cref="IRequestFactory.CreateRequestContext(IMessagingFactory)"/>
        /// 
        public abstract IRequestContext
        CreateRequestContext(IMessagingSession session);

        //////////////////////////////////////////////////////////////////////
        /// <see 
        /// cref="IRequestFactory.CreateRequest(String,IRequestContext)"/>
        /// 
        public IRequest 
        CreateRequest(String id,IRequestContext context)
        {
            return new Request(id,context);
        }
    }
}

//  ##########################################################################
