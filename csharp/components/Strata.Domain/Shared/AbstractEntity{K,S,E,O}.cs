//  ##########################################################################
//  # File Name: AbstractEntity.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.Shared
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractEntity<K,S,E,O>:
        AbstractDomainEventSource<S,E,O>,
        IEntity<K>,
        IDomainEventSource<S,E,O>
        where S: IDomainEventSource<S,E,O>
        where E: IDomainEvent<S>
        where O: IDomainEventObserver<E>
    {
        public virtual K        PrimaryId { get; set; }
        public virtual int      Version { get; set; }

        public virtual DateTime Created { get; set; }
        public virtual DateTime LastModified { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractEntity()
        {
            Created      = DateTime.Now;
            LastModified = Created;
        }
    }
}

//  ##########################################################################
