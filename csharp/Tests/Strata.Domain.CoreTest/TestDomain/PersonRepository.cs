//  ##########################################################################
//  # File Name: PersonRepository.cs
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
    class PersonRepository:
        AbstractRepository<long,IPerson>,
        IPersonRepository
    {
        public 
        PersonRepository(IUnitOfWorkProvider provider): 
            base(provider) {}
    }
}

//  ##########################################################################
