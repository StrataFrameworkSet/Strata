//  ##########################################################################
//  # File Name: RequestStateTransition.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Integration.Requester
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class RequestStateTransition
    {
        public IRequest Request { get; protected set; }
        public IRequestState Source { get; protected set; }
        public IRequestState Destination { get; protected set; }

        public RequestStateTransition(IRequest r, IRequestState s, IRequestState d)
        {
            Request = r;
            Source = s;
            Destination = d;
        }
    }
}

//  ##########################################################################
