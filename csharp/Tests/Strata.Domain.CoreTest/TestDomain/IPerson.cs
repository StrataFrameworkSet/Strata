﻿//  ##########################################################################
//  # File Name: IPerson.cs
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
    interface IPerson:
        IParty,
        IEquatable<IPerson>
    {
        PersonName Name { get; set; }
        DateTime   DateOfBirth { get; set; }
    }
}

//  ##########################################################################
