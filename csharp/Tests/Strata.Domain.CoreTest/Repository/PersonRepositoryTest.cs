//  ##########################################################################
//  # File Name: PersonRepositoryTest.cs
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
    class PersonRepositoryTest
    {
        private IUnitOfWorkProvider itsProvider;
        private IPersonRepository itsTarget;

        [SetUp]
        public void
        SetUp()
        {
            itsProvider = CreateUnitOfWorkProvider();
            itsTarget = new PersonRepository(itsProvider);
        }

        [TearDown]
        public async Task
        TearDown()
        {
            INamedQuery<IPerson> finder = await (itsTarget.GetNamedQuery("GetAll"));
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();

            foreach (IPerson p in await (finder.GetAll()))
                await (itsTarget.Remove(p));

            await (unitOfWork.Commit());
            itsTarget = null;
            itsProvider = null;
        }

        [Test]
        public async Task
        TestInsertPerson()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IPerson expected =
                            new Person()
                            {
                                Name = new PersonName("John","Liebenau"),
                                DateOfBirth = new DateTime(1967,4,25)
                            };

            IPerson actual = await (itsTarget.Insert(expected));

            await (unitOfWork.Commit());
            Assert.AreEqual(expected.Name,actual.Name);
            Assert.AreEqual(expected.DateOfBirth,actual.DateOfBirth);
        }

        [Test]
        public async Task
        TestUpdatePerson()

        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IPerson expected =
                new Person()
                {
                    Name = new PersonName("John","Liebenau"),
                    DateOfBirth = new DateTime(1967,4,25)
                };
            IPerson actual = null;


            expected = await(itsTarget.Insert( expected ));
            await(unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();
            expected.Name.MiddleName = "Friedrich";
            actual = await(itsTarget.Update( expected ));
            await(unitOfWork.Commit());

            Assert.AreEqual(expected.Name,actual.Name);
            Assert.AreEqual(expected.DateOfBirth,actual.DateOfBirth);
            
        }

        [Test]
        public async Task
        TestRemovePerson()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IPerson expected =
                new Person()
                {
                    Name = new PersonName("John","Liebenau"),
                    DateOfBirth = new DateTime(1967,4,25)
                };
            IPerson actual = null;


            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();

            Assert.IsTrue(await (itsTarget.HasUnique(expected.PrimaryId)));
            await (itsTarget.Remove(expected));
            await (unitOfWork.Commit());

            Assert.IsFalse(await (itsTarget.HasUnique(expected.PrimaryId)));
        }

        [Test]
        public async Task
        TestGetPersonByPartyKey()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IPerson expected =
                new Person()
                {
                    Name = new PersonName("John","Liebenau"),
                    DateOfBirth = new DateTime(1967,4,25)
                };
            IPerson actual = null;


            expected = await(itsTarget.Insert(expected));
            await(unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();
            Assert.IsTrue(await (itsTarget.HasUnique(expected.PrimaryId)));
            actual = (await itsTarget.GetUnique(expected.PrimaryId)).Get();
            Assert.AreEqual(expected,actual);
        }

        [Test]
        public async Task
        TestGetPersonByName()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IPerson expected =
                new Person()
                {
                    Name = new PersonName("John","Liebenau"),
                    DateOfBirth = new DateTime(1967,4,25)
                };
            IPerson actual = null;


            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();
            Assert.IsTrue(await (itsTarget.HasUnique(expected.PrimaryId)));
            actual = (await itsTarget.GetUniqueMatching(entity => entity.Name.Equals(expected.Name))).Get();
            Assert.AreEqual(expected,actual);
        }

        [Test]
        public async Task
        TestGetFinder()
        {
            INamedQuery<IPerson> query = await (itsTarget.GetNamedQuery("GetByName"));

            Assert.NotNull(query);
            Assert.AreEqual("GetByName",query.Name);
        }

        [Test]
        public async Task
        TestHasPersonWithPartyKey()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IPerson expected =
                new Person()
                {
                    Name = new PersonName("John","Liebenau"),
                    DateOfBirth = new DateTime(1967,4,25)
                };
            IPerson actual = null;


            expected = await (itsTarget.Insert(expected));
            await (unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();
            Assert.IsTrue(await (itsTarget.HasUnique(expected.PrimaryId)));
        }

        [Ignore("")]
        [Test]
        public async Task
        TestHasPersonWithName()
        {
            IUnitOfWork unitOfWork = itsProvider.GetUnitOfWork();
            IPerson expected =
                new Person()
                {
                    Name = new PersonName("John","Liebenau"),
                    DateOfBirth = new DateTime(1967,4,25)
                };
            IPerson actual = null;


            expected = await(itsTarget.Insert(expected));
            await(unitOfWork.Commit());

            unitOfWork = itsProvider.GetUnitOfWork();
            Assert.IsTrue(await(itsTarget.HasMatching(entity => entity.Name.Equals(expected.Name))));
        }

        [Test]
        public async Task
        TestHasFinder()
        {
            Assert.IsTrue(await (itsTarget.HasNamedQuery("GetByName")));
        }

        protected abstract IUnitOfWorkProvider
        CreateUnitOfWorkProvider();
    }
}

//  ##########################################################################
