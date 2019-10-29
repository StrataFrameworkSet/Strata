//  ##########################################################################
//  # File Name: Organization.cs
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class Organization:
        AbstractParty,
        IOrganization
    {
        public virtual string     Name { get; set; }
        public virtual ISet<long> MemberIds { get; set; }
    }
}

//  ##########################################################################
