﻿//  ##########################################################################
//  # File Name: AbstractDomainEventSource.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.DomainEvent
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractDomainEventSource<S,E,O>:
        IDomainEventSource<S,E,O>
        where S: IDomainEventSource<S,E,O>
        where E: IDomainEvent<S>
        where O: IDomainEventObserver<E>
    {
        public virtual ISet<O> Observers { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractDomainEventSource()
        {
            Observers = new HashSet<O>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractDomainEventSource(AbstractDomainEventSource<S,E,O> other)
        {
            Observers = new HashSet<O>(other.Observers);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual S 
        AttachFrom(S other)
        {
            return Attach(other.Observers);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual S
        Attach(ISet<O> observers)
        {
            observers.ForEach((O observer) => Observers.Add(observer));
            return GetSelf();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual S
        Attach(O observer)
        {
            Observers.Add(observer);
            return GetSelf();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual S
        Detach(O observer)
        {
            Observers.Remove(observer);
            return GetSelf();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool
        Has(O observer)
        {
            return Observers.Contains(observer);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual S
        Notify(E evnt)
        {
            Observers.ForEach((O observer) => observer.OnEvent(evnt));
            return GetSelf();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected abstract S
        GetSelf();
    }
}

//  ##########################################################################
