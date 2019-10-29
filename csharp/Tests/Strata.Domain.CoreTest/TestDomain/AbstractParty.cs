//  ##########################################################################
//  # File Name: AbstractParty.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using Strata.Domain.Core.Entity;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractParty:
        AbstractEntity<long>,
        IParty
    {
        public virtual ISet<IContactInformation> 
        ContactInformation { get; set; }
    }
}

//  ##########################################################################
