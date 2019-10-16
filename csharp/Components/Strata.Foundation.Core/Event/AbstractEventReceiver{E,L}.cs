//  ##########################################################################
//  # File Name: AbstractEventReceiver.cs
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
    public abstract
    class AbstractEventReceiver<E,L>:
        IEventReceiver<E,L>
        where L:IEventListener<E>
    {
        public L Listener { get; set; }

        protected
        AbstractEventReceiver()
        {
            Listener = default(L);
        }

        public abstract void
        StartListening();

        public virtual void 
        StartListening(L listener)
        {
            Listener = listener;
            StartListening();
        }

        public abstract void
        StopListening();

        public virtual bool 
        HasListener()
        {
            return Listener != null;
        }

        public abstract bool
        IsListening();
    }
}

//  ##########################################################################
