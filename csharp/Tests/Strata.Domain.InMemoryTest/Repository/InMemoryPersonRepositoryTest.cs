//  ##########################################################################
//  # File Name: InMemoryOrganizationRepositoryTest.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using NUnit.Framework;
using Strata.Domain.Core.Repository;
using Strata.Domain.Core.TestDomain;
using Strata.Domain.Core.UnitOfWork;
using Strata.Domain.InMemory.NamedQuery;
using Strata.Domain.InMemory.UnitOfWOrk;

namespace Strata.Domain.InMemory.Repository
{
    
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [TestFixture]
    public
    class InMemoryPersonRepositoryTest:
        PersonRepositoryTest
    {
        protected override IUnitOfWorkProvider 
        CreateUnitOfWorkProvider()
        {
            InMemoryUnitOfWorkProvider provider =
                new InMemoryUnitOfWorkProvider();

            provider
                .InsertReplicator(
                    new EntityReplicator<IParty>(original => original))
                .InsertRetriever<long,IParty>(party => party.PrimaryId)
                .InsertReplicator(
                    new EntityReplicator<IPerson>(
                        orginal => 
                            new Person()
                            {
                                PrimaryId = 
                                    orginal.PrimaryId == 0 
                                        ? new Random().Next() 
                                        : orginal.PrimaryId,
                                ContactInformation = 
                                    new HashSet<IContactInformation>(
                                        orginal.ContactInformation),
                                Name = orginal.Name,
                                DateOfBirth = orginal.DateOfBirth
                            }))
                .InsertRetriever<long,IPerson>(person => person.PrimaryId)
                .InsertReplicator(
                    new EntityReplicator<IOrganization>(
                        original =>
                            new Organization()
                            {
                                PrimaryId = 
                                    original.PrimaryId == 0 
                                        ? new Random().Next()
                                        : original.PrimaryId,
                                ContactInformation = 
                                    new HashSet<IContactInformation>(
                                        original.ContactInformation),
                                Name = original.Name,
                                MemberIds = new HashSet<long>(original.MemberIds)
                            }))
                .InsertRetriever<long,IOrganization>(org => org.PrimaryId);
            new InMemoryNamedQuery<IPerson>(
                provider,
                "GetAll",
                new GetAllPredicate<IPerson>() );
        
            new InMemoryNamedQuery<IPerson>(
                provider,
                "GetByName",
                new EntityPredicate<IPerson>(
                    (target,inputs) =>
                            target.Name.Equals(inputs["name"])));

            new InMemoryNamedQuery<IPerson>(
                provider,
                "HasPersonWithName",
                new EntityPredicate<IPerson>(
                    (target,inputs) =>
                        target.Name.Equals(inputs["name"])));

            new InMemoryNamedQuery<IOrganization>(
                provider,
                "GetAll",
                new GetAllPredicate<IOrganization>() );
        
            new InMemoryNamedQuery<IOrganization>(
                provider,
                "GetByName",
                new EntityPredicate<IOrganization>(
                    (target,inputs) =>
                        target.Name.Equals(inputs["name"])));

            new InMemoryNamedQuery<IOrganization>(
                provider,
                "HasOrganizationWithName",
                new EntityPredicate<IOrganization>(
                    (target,inputs) =>
                        target.Name.Equals(inputs["name"])));


            return provider;
        }
}
}

//  ##########################################################################
