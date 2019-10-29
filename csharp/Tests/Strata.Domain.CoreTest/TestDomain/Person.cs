//  ##########################################################################
//  # File Name: Person.cs
//  ##########################################################################

using System;
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
    }
}

//  ##########################################################################
