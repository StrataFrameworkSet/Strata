//  ##########################################################################
//  # File Name: IParty.cs
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
    public
    interface IParty:
        IEntity<long>
    {
        ISet<IContactInformation> ContactInformation { get; set; }
    }
}

//  ##########################################################################
