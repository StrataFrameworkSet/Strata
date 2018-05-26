//  ##########################################################################
//  # File Name: EmsTextMessage.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Integration.Messaging;
using TIBCO.EMS;
using System.Runtime.Serialization;

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
    class EmsObjectMessage:
        EmsMessage,
        IObjectMessage
    {
        public override Message MessageImp {get{return ObjectMessageImp;}}
        public ObjectMessage    ObjectMessageImp { get; protected set; }

        public Object 
        Payload
        {
            get { return ObjectMessageImp.TheObject; }
            set { ObjectMessageImp.TheObject = value; }
        }

        public 
        EmsObjectMessage(ObjectMessage imp)
        {
            ObjectMessageImp = imp;
        }        
    }
}

//  ##########################################################################
