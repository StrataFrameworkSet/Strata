//  ##########################################################################
//  # File Name: PartyRepository.cs
//  ##########################################################################

using System.Collections.Generic;
using System.Threading.Tasks;
using Strata.Domain.Core.Repository;
using Strata.Domain.Core.UnitOfWork;

namespace Strata.Domain.Core.TestDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class PartyRepository:
        AbstractRepository<long,IParty>,
        IPartyRepository
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        PartyRepository(IUnitOfWorkProvider provider): 
            base(provider) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public async Task<IList<IParty>> 
        GetMembersOf(IOrganization organization)
        {
            IList<IParty> members = new List<IParty>();

            foreach (long memberId in organization.MemberIds)
                (await GetUnique(memberId))
                    .IfPresent(member => members.Add(member));

            return members;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public async Task<bool> 
        HasMembersOf(IOrganization organization)
        {
            foreach (long memberId in organization.MemberIds)
                if (await HasUnique(memberId))
                    return true;

            return false;
        }
    }
}

//  ##########################################################################
