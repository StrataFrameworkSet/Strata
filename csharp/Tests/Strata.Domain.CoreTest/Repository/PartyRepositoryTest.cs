//  ##########################################################################
//  # File Name: PartyRepositoryTest.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using NUnit.Framework;
using Strata.Domain.Core.NamedQuery;
using Strata.Domain.Core.TestDomain;
using Strata.Domain.Core.UnitOfWork;
using Strata.Foundation.Core.Value;

namespace Strata.Domain.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [TestFixture]
    public abstract
    class PartyRepositoryTest
    {
        private IUnitOfWorkProvider     itsProvider;
        private IOrganizationRepository itsOrgRepository;
        private IPersonRepository       itsPersonRepository;
        private IPartyRepository        itsTarget;

        private IOrganization           parentOrg;
        private IOrganization           childOrg;
        private IPerson                 member;

        [SetUp]
        public async Task
        SetUp()
        {
            itsProvider = CreateUnitOfWorkProvider();
            itsOrgRepository =
                new OrganizationRepository(itsProvider);
            itsPersonRepository =
                new PersonRepository(itsProvider);
            itsTarget =
                new PartyRepository(itsProvider);

            parentOrg = new Organization() { Name = "Sapientia Systems LLC" };
            childOrg = new Organization() { Name = "Sapientia Development Systems" };
            member =
                new Person()
                {
                    Name = new PersonName("John","Liebenau"),
                    DateOfBirth = new DateTime(1967,4,25)
                };

            parentOrg = await itsOrgRepository.Insert(parentOrg);
            childOrg = await itsOrgRepository.Insert(childOrg);
            member = await itsPersonRepository.Insert(member);

            parentOrg.MemberIds.Add(childOrg.PrimaryId);
            parentOrg.MemberIds.Add(member.PrimaryId);
            childOrg.MemberIds.Add(member.PrimaryId);

            parentOrg = await itsOrgRepository.Update(parentOrg);
            childOrg = await itsOrgRepository.Update(childOrg);
            await itsProvider.GetUnitOfWork().Commit();
        }

        [TearDown]
        public async Task
        TearDown()
        {
            INamedQuery<IOrganization> orgFinder = await itsOrgRepository.GetNamedQuery("GetAll");
            INamedQuery<IPerson> personFinder = await itsPersonRepository.GetNamedQuery("GetAll");
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();

            foreach (IOrganization o in await orgFinder.GetAll())
                await itsOrgRepository.Remove(o);

            foreach (IPerson p in await personFinder.GetAll())
                await itsPersonRepository.Remove(p);

            await unitOfWork.Commit();
            itsOrgRepository = null;
            itsPersonRepository = null;
            itsTarget = null;
            itsProvider = null;

        }
        
        [Test]
        public async Task
        TestGetPartyByKey()
        {
            IParty actualParentOrg = (await itsTarget.GetUnique(parentOrg.PrimaryId)).Get();
            IParty actualChildOrg = (await itsTarget.GetUnique(childOrg.PrimaryId)).Get();
            IParty actualMember = (await itsTarget.GetUnique(member.PrimaryId)).Get();

            Assert.AreEqual(parentOrg,actualParentOrg);
            Assert.AreEqual(childOrg,actualChildOrg);
            Assert.AreEqual(member,actualMember);
        }

        [Test]
        public async Task
        TestGetMembersOf()
        {
            IList<IParty> parentMembers = await itsTarget.GetMembersOf(parentOrg);
            IList<IParty> childMembers = await itsTarget.GetMembersOf(childOrg);

            Assert.IsTrue(parentMembers.Contains(childOrg));
            Assert.IsTrue(parentMembers.Contains(member));
            Assert.IsTrue(childMembers.Contains(member));
        }

        [Test]
        public async Task
        TestHasPartyWithKey()
        {
            Assert.IsTrue(await itsTarget.HasUnique(parentOrg.PrimaryId));
            Assert.IsTrue(await itsTarget.HasUnique(childOrg.PrimaryId));
            Assert.IsTrue(await itsTarget.HasUnique(member.PrimaryId));
        }

        [Test]
        public async Task
        TestHasMembersOf()
        {
            Assert.IsTrue(await itsTarget.HasMembersOf(parentOrg));
            Assert.IsTrue(await itsTarget.HasMembersOf(childOrg));
        }

        protected abstract IUnitOfWorkProvider
        CreateUnitOfWorkProvider();
    }
}

//  ##########################################################################
