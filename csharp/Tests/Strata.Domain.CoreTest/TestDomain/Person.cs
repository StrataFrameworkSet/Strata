//  ##########################################################################
//  # File Name: Person.cs
//  ##########################################################################

using System;
using System.Diagnostics.CodeAnalysis;
using Strata.Foundation.Core.Utility;
using Strata.Foundation.Core.Value;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class Person:
        AbstractParty,
        IPerson
    {
        public virtual PersonName Name { get; set; }
        public virtual DateTime   DateOfBirth { get; set; }

        public override bool
        Equals(object other)
        {
            if (other is IPerson)
                return Equals((IPerson)other);

            return false;
        }

        public override int
        GetHashCode()
        {
            return
                new HashCodeBuilder()
                    .Append(Name.FirstName)
                    .Append(Name.LastName)
                    .HashCode;
        }

        public bool
        Equals([AllowNull] IPerson other)
        {
            if (other == null)
                return false;

            return
                ContactInformation.SetEquals(other.ContactInformation) &&
                Name.Equals(other.Name) &&
                DateOfBirth.Equals(other.DateOfBirth);

        }
    }
}

//  ##########################################################################
