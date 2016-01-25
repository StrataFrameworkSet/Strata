//  ##########################################################################
//  # File Name: EmsMessageListener.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Integration.Messaging;

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
    class EmsMessageListener:
        TIBCO.EMS.IMessageListener
    {
        private IMessageListener listener;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="l"></param>
        /// 
        public 
        EmsMessageListener(IMessageListener l)
        {
            listener = l;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        /// 
        /// <param name="m"></param>
        /// 
        public void 
        OnMessage(TIBCO.EMS.Message m)
        {
            if ( m is TIBCO.EMS.TextMessage)
                listener.OnMessage(new EmsStringMessage((TIBCO.EMS.TextMessage)m));
            else if (m is TIBCO.EMS.ObjectMessage)
                listener.OnMessage(new EmsObjectMessage((TIBCO.EMS.ObjectMessage)m));

        }
    }
}

//  ##########################################################################
