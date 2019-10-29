//  ##########################################################################
//  # File Name: IOrganization.cs
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
    interface IOrganization
    {
        string     Name { get; set; }
        ISet<long> MemberIds { get; set; }
    }
}

//  ##########################################################################
