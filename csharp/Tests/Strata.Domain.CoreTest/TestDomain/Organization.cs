//  ##########################################################################
//  # File Name: Organization.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using Strata.Foundation.Core.Utility;

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

        public
        Organization():
            this(null) {}

        public
        Organization(string name)
        {
            Name = name;
            MemberIds = new HashSet<long>();
        }

        public override bool
        Equals(object other)
        {
            if (other is IOrganization)
                return Equals((IOrganization)other);

            return false;
        }

        public override int 
        GetHashCode()
        {
            return 
                new HashCodeBuilder()
                    .Append(Name)
                    .HashCode;
        }

        public bool 
        Equals([AllowNull] IOrganization other)
        {
            if (other == null)
                return false;

            return
                ContactInformation.SetEquals(other.ContactInformation) &&
                Name.Equals(other.Name) &&
                MemberIds.SetEquals(other.MemberIds);
                
        }
    }
}

//  ##########################################################################
