//  ##########################################################################
//  # File Name: OrganizationRepositoryTest.cs
//  ##########################################################################

using Strata.Domain.Core.TestDomain;
using Strata.Domain.Core.UnitOfWork;
using System;
using System.Threading.Tasks;
using NUnit.Framework;
using NUnit.Framework.Internal;
using Strata.Domain.Core.NamedQuery;

namespace Strata.Domain.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [TestFixture]
    public abstract
    class OrganizationRepositoryTest
    {
        private IUnitOfWorkProvider itsProvider;
        private IOrganizationRepository itsTarget;

        [SetUp]
        public void
        SetUp()
        {
            itsProvider = CreateUnitOfWorkProvider();
            itsTarget = new OrganizationRepository(itsProvider);
        }

        [TearDown]
        public async Task
        TearDown()
        {
            INamedQuery<IOrganization> query = await (itsTarget.GetNamedQuery("GetAll"));
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();

            foreach (IOrganization p in await (query.GetAll()))
                await (itsTarget.Remove(p));

            await (unitOfWork.Commit());
            itsTarget = null;
            itsProvider = null;
        }

        [Test]
        public async Task
        TestInsertOrganization()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IOrganization expected = new Organization("FooBar Inc.");
            IOrganization actual = null;

            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            actual = (await itsTarget.GetUnique(expected.PrimaryId)).Get();
            Assert.AreEqual(expected,actual);
        }

        [Test]
        public async Task
        TestUpdateOrganization()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IOrganization expected = new Organization("FooBar Inc.");
            IOrganization actual = null;

            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();
            expected.MemberIds.Add(10L);
            actual = await (itsTarget.Update(expected));
            await (unitOfWork.Commit());
            Assert.AreEqual(expected,actual);
        }

        [Test]
        public async Task
        TestRemoveOrganization()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IOrganization expected = new Organization("FooBar Inc.");

            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();
            Assert.IsTrue(await itsTarget.HasUnique(expected.PrimaryId));
            await (itsTarget.Remove(expected));
            await (unitOfWork.Commit());

            Assert.IsFalse(await itsTarget.HasUnique(expected.PrimaryId));
        }

        [Test]
        public async Task
        TestGetOrganization()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IOrganization expected = new Organization("FooBar Inc.");
            IOrganization actual = null;

            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            Assert.IsTrue(await itsTarget.HasUnique(expected.PrimaryId));
            actual = (await itsTarget.GetUnique(expected.PrimaryId)).Get();
            Assert.AreEqual(expected,actual);
        }

        [Ignore("")]
        [Test]
        public async Task
        TestGetOrganizationByName()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IOrganization expected = new Organization("FooBar Inc.");
            IOrganization actual = null;

            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            Assert.IsTrue(await (itsTarget.HasUnique(expected.PrimaryId)));
            actual = (await itsTarget.GetUniqueMatching(org => org.Name.Equals(expected.Name))).Get();
            Assert.AreEqual(expected,actual);

        }

        [Test]
        public async Task
        TestGetNamedQuery()
        {
            INamedQuery<IOrganization> finder = await itsTarget.GetNamedQuery("GetByName");

            Assert.NotNull(finder);
            Assert.AreEqual("GetByName",finder.Name);
        }

        [Test]
        public async Task
        TestHasOrganizationWithPartyKey()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IOrganization expected = new Organization("FooBar Inc.");

            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            Assert.IsTrue(await (itsTarget.HasUnique(expected.PrimaryId)));
        }

        [Test]
        public async Task
         TestHasOrganizationWithName()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IOrganization expected = new Organization("FooBar Inc.");

            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            Assert.IsTrue(await (itsTarget.HasMatching(org => org.Name.Equals(expected.Name))));
        }

        [Test]
        public async Task
        testHasFinder()
        {
            Assert.IsTrue(await (itsTarget.HasNamedQuery("GetByName")));
        }

        protected abstract IUnitOfWorkProvider
        CreateUnitOfWorkProvider();
    }
}

//  ##########################################################################
