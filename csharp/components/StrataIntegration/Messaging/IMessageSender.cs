//  ##########################################################################
//  # File Name: IMessageSender.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

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
    interface IMessageSender
    {
        long TimeToLiveInMilliseconds { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="message"></param>
        /// 
        void
        Send(IMessage message);
    }
}

//  ##########################################################################
