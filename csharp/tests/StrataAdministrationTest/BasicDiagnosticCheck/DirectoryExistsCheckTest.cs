using System;
using System.Linq;
using Strata.Administration.Diagnostic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace Strata.Administration.BasicDiagnosticCheck
{
    [TestClass]
    public 
    class DirectoryExistsCheckTest
    {
	    private DirectoryExistsCheck target;

        [TestInitialize]
	    public void 
        SetUp()
	    {
		    target = new DirectoryExistsCheck( "TempDirExists" );
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
		
		    result.AttachReporter( reporter );
            target.Path = @"C:\Temp";
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
            target.Path = @"C:\NoSuchDirectory";
		    target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( target.Name )  );
            Assert.AreEqual( 0,result.GetNumberOfSuccesses() );
            Assert.AreEqual( 1,result.GetNumberOfFailures() );
	    }
    }
}
