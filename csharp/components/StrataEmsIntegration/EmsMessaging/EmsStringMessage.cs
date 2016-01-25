//  ##########################################################################
//  # File Name: EmsTextMessage.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Integration.Messaging;
using TIBCO.EMS;

namespace Strata.EmsIntegration.EmsMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class EmsStringMessage:
        EmsMessage,
        IStringMessage
    {
        public override Message MessageImp {get{return TextMessageImp;}}
        public TextMessage      TextMessageImp { get; protected set; }

        public String 
        Payload
        {
            get { return TextMessageImp.Text; }
            set { TextMessageImp.Text = value; }
        }

        public 
        EmsStringMessage(TextMessage imp)
        {
            TextMessageImp = imp;
        }        
    }
}

//  ##########################################################################
