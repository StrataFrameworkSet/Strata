//  ##########################################################################
//  # File Name: IOrganizationRepository.cs
//  ##########################################################################

using System;
using Strata.Domain.Core.Repository;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IOrganizationRepository:
        IRepository<long,IOrganization>
    {
    }
}

//  ##########################################################################
