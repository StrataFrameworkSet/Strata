//  ##########################################################################
//  # File Name: FakeObjectMessage.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Integration.Messaging;
using System.Runtime.Serialization;

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
    class FakeObjectMessage:
        FakeMessage,
        IObjectMessage
    {
        public object Payload { get; set; }
    }
}

//  ##########################################################################
