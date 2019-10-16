//  ##########################################################################
//  # File Name: IEventReceiver{E,L}.cs
//  # Copyright: 2019, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Event
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IEventReceiver<E,L>
        where L:IEventListener<E>
    {
        L Listener { get; set; }

        void
        StartListening();

        void
        StartListening(L listener);

        void
        StopListening();

        bool
        HasListener();

        bool
        IsListening();
    }
}

//  ##########################################################################
