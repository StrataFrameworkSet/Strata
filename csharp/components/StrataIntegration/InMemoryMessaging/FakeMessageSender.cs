//  ##########################################################################
//  # File Name: FakeMessageSender.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
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
    class FakeMessageSender:
        IMessageSender
    {
        public long TimeToLiveInMilliseconds { get; set; }

        private FakeMessageBroker broker;
        private String channelId;

        public 
        FakeMessageSender(String id,FakeMessageBroker b)
        {
            channelId = id;
            broker = b;
        }

        public void 
        Send(IMessage message)
        {
            broker.RouteMessage(channelId,message);
        }
    }
}

//  ##########################################################################
