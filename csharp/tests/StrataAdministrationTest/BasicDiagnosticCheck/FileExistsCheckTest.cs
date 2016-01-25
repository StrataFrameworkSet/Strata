using System;
using System.IO;
using System.Linq;
using Strata.Administration.Diagnostic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace Strata.Administration.BasicDiagnosticCheck
{
    [TestClass]
    public 
    class FileExistsCheckTest
    {
	    private FileExistsCheck target;
        private const String FILE_PATH = @"C:\Temp\FileExists.test";

        [TestInitialize]
	    public void 
        SetUp()
	    {
		    target = new FileExistsCheck( "TempDirExists" );
	    }

        [TestCleanup]
	    public void 
        TearDown()
	    {
		    target = null;
	    }

        [TestMethod]
	    public void 
        TestRunDiagnosticSuccess()
	    {
		    MockDiagnosticResult result   = new MockDiagnosticResult();
		    IDiagnosticReporter  reporter = new Mock<IDiagnosticReporter>().Object;
		
            if ( !File.Exists(FILE_PATH) )
                File.Create( FILE_PATH );

		    result.AttachReporter( reporter );
            target.Path = FILE_PATH;
		    target.RunDiagnostic( result );

		    Assert.IsTrue( result.ActualRun.Contains( target.Name )  );
            Assert.AreEqual( 1,result.GetNumberOfSuccesses() );
            Assert.AreEqual( 0,result.GetNumberOfFailures() );
	    }

        [TestMethod]
	    public void 
        TestRunDiagnosticFailure()
	    {
		    MockDiagnosticResult result   = new MockDiagnosticResult();
		    IDiagnosticReporter  reporter = new Mock<IDiagnosticReporter>().Object;
		
		    result.AttachReporter( reporter );
            target.Path = @"C:\Temp\NoSuchFile.test";
		    target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( target.Name )  );
            Assert.AreEqual( 0,result.GetNumberOfSuccesses() );
            Assert.AreEqual( 1,result.GetNumberOfFailures() );
	    }
    }
}
