//  ##########################################################################
//  # File Name: FakeTextMessage.cs
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
    class FakeTextMessage:
        FakeMessage,
        IStringMessage
    {
        public String Payload { get; set; }
    }
}

//  ##########################################################################
