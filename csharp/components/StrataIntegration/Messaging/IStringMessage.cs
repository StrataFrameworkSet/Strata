//  ##########################################################################
//  # File Name: ITextMessage.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Integration.Messaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IStringMessage:
        IMessage
    {
        String Payload { get; set; }
    }
}

//  ##########################################################################
