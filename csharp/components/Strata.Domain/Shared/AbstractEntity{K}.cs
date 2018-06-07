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
    public
    class AbstractEntity<K>:
        IEntity<K>
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
