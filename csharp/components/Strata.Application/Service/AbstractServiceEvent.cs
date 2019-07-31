//  ##########################################################################
//  # File Name: AbstractServiceEvent.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Application.Service
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractServiceEvent<S>:
        IServiceEvent<S>
    {
        public virtual string   Name { get; protected set; }
        public virtual DateTime Timestamp { get; protected set; }
        public virtual S        Source { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractServiceEvent(string name,S source):
            this(name,DateTime.Now,source) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractServiceEvent(string name,DateTime timestamp,S source)
        {
            Name      = name;
            Timestamp = timestamp;
            Source    = source;
        }
    }
}

//  ##########################################################################
