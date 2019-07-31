//  ##########################################################################
//  # File Name: DirectoryTest.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    [TestClass]
    public 
    class DirectoryTest
    {
        private IDirectory target;

        [TestInitialize]
        public void
        SetUp()
        {
            target = new Directory( @"C:\Temp\DirectoryTest" );
            target.Create();
        }

        [TestCleanup]
        public void
        TearDown()
        {
            target.Delete();    
        }

        [TestMethod]
        public void
        TestAbsolutePath()
        {
            Assert.AreEqual( @"C:\Temp\DirectoryTest",target.AbsolutePath );
        }

        [TestMethod]
        public void
        TestRelativePath()
        {
            Assert.AreEqual( @"DirectoryTest",target.RelativePath );
        }

        [TestMethod]
        public void
        TestExtension()
        {
            Assert.AreEqual( "",target.Extension );
        }

        [TestMethod]
        public void
        TestExists()
        {
            Assert.IsTrue( target.Exists );
        }

        [TestMethod]
        public void
        TestParent()
        {
            IDirectory parent = target.Parent;

            Assert.AreEqual( @"C:\Temp",parent.AbsolutePath );
        }

        [TestMethod]
        public void
        TestDirectories()
        {
            Assert.AreEqual( 0,target.Directories.Count );
        }

        [TestMethod]
        public void
        TestFiles()
        {
            Assert.AreEqual( 0,target.Files.Count );            
        }

        [TestMethod]
        public void
        TestIsEmpty()
        {
            Assert.IsTrue( target.IsEmpty );
        }
    }
}

//  ##########################################################################
