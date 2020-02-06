//  ##########################################################################
//  # File Name: DiagnosticRepositoryTest.cs
//  ##########################################################################

using System;
using NUnit.Framework;
using Strata.Diagnostic.Core.Common;
using Strata.Diagnostic.Core.Evaluation;

namespace Strata.Diagnostic.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [TestFixture]
    public
    class DiagnosticRepositoryTest
    {
        private IDiagnosticRepository itsTarget;
        private FreeMemoryCheck       checkFreeMemory;
        private DirectoryExistsCheck  checkDirectoryExists;
        private IDiagnostic           checkMock;
        private DiagnosticSuite       suite;

        [SetUp]
        public void 
        SetUp()
        {
            itsTarget = new DiagnosticRepository();

            checkFreeMemory = new FreeMemoryCheck("FreeMemory");
            checkFreeMemory.FreeMemoryBytesMinimum = 1000;
            checkDirectoryExists = new DirectoryExistsCheck("TempExists");
            checkDirectoryExists.Path = @"C:\Temp";
            checkMock = new MockDiagnosticCheck("MockCheck",true,false,false,false);

            suite = new DiagnosticSuite("Suite");
            suite
                .AddDiagnostic(checkFreeMemory)
                .AddDiagnostic(checkDirectoryExists)
                .AddDiagnostic(checkMock);
        }

        [TearDown]
        public void
        TearDown()
        {
            itsTarget.Clear();
            itsTarget = null;
        }

        [Test]
        public void
        TestInsert()
        {
            Assert.IsFalse(itsTarget.HasUnique(suite.Name));
            Assert.IsFalse(itsTarget.HasUnique(checkFreeMemory.Name));
            Assert.IsFalse(itsTarget.HasUnique(checkDirectoryExists.Name));
            Assert.IsFalse(itsTarget.HasUnique(checkMock.Name));
            Assert.AreEqual(0,itsTarget.GetAll().Count);

            itsTarget.Insert(suite);

            Assert.IsTrue(itsTarget.HasUnique(suite.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkFreeMemory.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkDirectoryExists.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkMock.Name));
            Assert.AreEqual(4,itsTarget.GetAll().Count);

        }

        [Test]
        public void
        TestRemove()
        {
            Assert.IsFalse(itsTarget.HasUnique(suite.Name));
            Assert.IsFalse(itsTarget.HasUnique(checkFreeMemory.Name));
            Assert.IsFalse(itsTarget.HasUnique(checkDirectoryExists.Name));
            Assert.IsFalse(itsTarget.HasUnique(checkMock.Name));
            Assert.AreEqual(0,itsTarget.GetAll().Count);

            itsTarget.Insert(suite);

            Assert.IsTrue(itsTarget.HasUnique(suite.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkFreeMemory.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkDirectoryExists.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkMock.Name));
            Assert.AreEqual(4,itsTarget.GetAll().Count);

            itsTarget.Remove(suite);

            Assert.IsFalse(itsTarget.HasUnique(suite.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkFreeMemory.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkDirectoryExists.Name));
            Assert.IsTrue(itsTarget.HasUnique(checkMock.Name));
            Assert.AreEqual(3,itsTarget.GetAll().Count);
        }

        [Test]
        public void
        TestGetMatching()
        {
            Assert.IsFalse(itsTarget.HasMatching(diagnostic => diagnostic is DiagnosticSuite));

            itsTarget.Insert(suite);

            Assert.IsTrue(itsTarget.HasMatching(diagnostic => diagnostic is DiagnosticSuite));
            Assert.IsTrue(itsTarget.GetMatching(d => d is DiagnosticSuite).Contains(suite));

            itsTarget.Remove(suite);

            Assert.IsFalse(itsTarget.HasMatching(diagnostic => diagnostic is DiagnosticSuite));
            Assert.IsFalse(itsTarget.GetMatching(d => d is DiagnosticSuite).Contains(suite));
        }
    }
}

//  ##########################################################################
