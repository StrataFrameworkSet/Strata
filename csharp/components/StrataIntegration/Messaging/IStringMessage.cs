//  ##########################################################################
//  # File Name: ITextMessage.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
