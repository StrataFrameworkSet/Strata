//  ##########################################################################
//  # File Name: FakeMessageBroker.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
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
    class FakeMessageBroker
    {
        private Dictionary<String,FakeMessageReceiver> receivers;

        public 
        FakeMessageBroker()
        {
            receivers = new Dictionary<String,FakeMessageReceiver>();
        }

        public void 
        RegisterReceiver(String channelId,FakeMessageReceiver receiver)
        {
            receivers[channelId] = receiver;
        }

        public void 
        RouteMessage(String channelId,IMessage message)
        {
            receivers[channelId].ReceiveMessage(message);
        }
    }
}

//  ##########################################################################
