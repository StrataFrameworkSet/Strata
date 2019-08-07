//  ##########################################################################
//  # File Name: IMessageListener.cs
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
    interface IMessageListener
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="message"></param>
        /// 
        void
        OnMessage(IStringMessage message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="message"></param>
        /// 
        void
        OnMessage(IMapMessage message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="message"></param>
        /// 
        void
        OnMessage(IObjectMessage message);
    }
}

//  ##########################################################################
