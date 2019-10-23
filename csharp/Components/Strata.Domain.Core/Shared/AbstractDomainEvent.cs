//  ##########################################################################
//  # File Name: AbstractDomainEvent.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.Core.Shared
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractDomainEvent<S>:
        IDomainEvent<S>
    {
        public virtual string   Name { get; protected set; }
        public virtual DateTime Timestamp { get; protected set; }
        public virtual S        Source { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractDomainEvent(string name,S source):
            this(name,DateTime.Now,source) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractDomainEvent(string name,DateTime timestamp,S source)
        {
            Name      = name;
            Timestamp = timestamp;
            Source    = source;
        }
    }
}

//  ##########################################################################
