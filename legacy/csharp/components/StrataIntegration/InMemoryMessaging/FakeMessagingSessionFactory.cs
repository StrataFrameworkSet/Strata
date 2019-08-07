//  ##########################################################################
//  # File Name: FakeMessagingSessionFactory.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using Strata.Integration.Messaging;

namespace Strata.Integration.InMemoryMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class FakeMessagingSessionFactory:
        IMessagingSessionFactory
    {            
        public 
        FakeMessagingSessionFactory() {}

        public IMessagingSession 
        CreateMessagingSession(IDictionary<string,object> parameters)
        {
            return new FakeMessagingSession(null);
        }
    }
}

//  ##########################################################################
