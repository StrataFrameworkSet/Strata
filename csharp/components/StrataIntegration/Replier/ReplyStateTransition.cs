//  ##########################################################################
//  # File Name: RequestStateTransition.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// State transition for <c>IReply</c> objects.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class ReplyStateTransition
    {
        public IReply       Reply { get; protected set; }
        public IReplyState  Source { get; protected set; }
        public IReplyState  Destination { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a transition from state s to state d.
        /// </summary>
        /// 
        /// <param name="r">reply object</param>
        /// <param name="s">source state</param>
        /// <param name="d">destination state</param>
        /// 
        public 
        ReplyStateTransition(IReply r, IReplyState s, IReplyState d)
        {
            Reply = r;
            Source = s;
            Destination = d;
        }
    }
}

//  ##########################################################################
