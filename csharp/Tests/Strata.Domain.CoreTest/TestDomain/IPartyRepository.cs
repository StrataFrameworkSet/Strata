//  ##########################################################################
//  # File Name: IPartyRepository.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Strata.Domain.Core.Repository;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IPartyRepository:
        IRepository<long,IParty>
    {
        Task<IList<IParty>>
        GetMembersOf(IOrganization organization);

        Task<bool>
        HasMembersOf(IOrganization organization);

    }
}

//  ##########################################################################
