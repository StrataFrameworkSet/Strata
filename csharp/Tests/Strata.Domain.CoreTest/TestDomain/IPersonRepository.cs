//  ##########################################################################
//  # File Name: IPersonRepository.cs
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
    interface IPersonRepository:
        IRepository<long,IPerson> {}
}

//  ##########################################################################
