﻿//  ##########################################################################
//  # File Name: AbstractServiceEventSource.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Utility;
using System.Collections.Generic;

namespace Strata.Application.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractServiceEventSource<S,E,O>:
        IServiceEventSource<S,E,O>
        where S: IServiceEventSource<S,E,O>
        where E: IServiceEvent<S>
        where O: IServiceEventObserver<E>
    {
        public virtual ISet<O> Observers { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractServiceEventSource()
        {
            Observers = new HashSet<O>();
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
