//  ##########################################################################
//  # File Name: IMessagingSessionFactory.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Integration.Messaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Factory for creating <c>IMessagingSession</c> instances.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IMessagingSessionFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="parameters">creation parameters</param>
        /// <returns>a new messaging session</returns>
        /// 
        IMessagingSession
        CreateMessagingSession(IDictionary<String,Object> parameters);
    }
}

//  ##########################################################################
