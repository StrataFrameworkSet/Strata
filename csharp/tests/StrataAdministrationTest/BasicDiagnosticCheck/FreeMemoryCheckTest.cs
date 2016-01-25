using System;
using System.Linq;
using Strata.Administration.Diagnostic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace Strata.Administration.BasicDiagnosticCheck
{
    [TestClass]
    public 
    class FreeMemoryCheckTest
    {
	    private FreeMemoryCheck target;
        private const long MB = 1000000;

        [TestInitialize]
	    public void 
        SetUp()
	    {
		    target = new FreeMemoryCheck( "FreeMemory" );
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
		    IDiagnosticReporter  reporter = new TextWriterDiagnosticReporter();
		
		    result.AttachReporter( reporter );
            target.FreeMemoryBytesMinimum = 100*MB;
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
		    IDiagnosticReporter  reporter = new TextWriterDiagnosticReporter();
		
		    result.AttachReporter( reporter );
            target.FreeMemoryBytesMinimum = 25000*MB;
		    target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( target.Name )  );
            Assert.AreEqual( 0,result.GetNumberOfSuccesses() );
            Assert.AreEqual( 1,result.GetNumberOfFailures() );
	    }
    }
}
