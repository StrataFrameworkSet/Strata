//  ##########################################################################
//  # File Name: IMessage.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

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
    interface IMessage
    {
        String       MessageId { get; set; }
        String       CorrelationId { get; set; }
        String       ReturnAddress { get; set; }
        DeliveryMode DeliveryMode { get; set; }

        void
        SetStringProperty(String key,String value);

        void
        SetLongProperty(String key,long value);

        String
        GetStringProperty(String key);

        long
        GetLongProperty(String key);

        bool
        HasProperty(String key);
    }
}

//  ##########################################################################
