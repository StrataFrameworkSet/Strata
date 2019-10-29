//  ##########################################################################
//  # File Name: OrganizationRepository.cs
//  ##########################################################################

using System;
using Strata.Domain.Core.Repository;
using Strata.Domain.Core.UnitOfWork;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class OrganizationRepository:
        AbstractRepository<long,IOrganization>
    {
        public 
        OrganizationRepository(IUnitOfWorkProvider provider): 
            base(provider) {}
    }
}

//  ##########################################################################
