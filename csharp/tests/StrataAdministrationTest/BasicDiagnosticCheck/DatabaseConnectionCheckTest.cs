using System;
using System.Linq;
using Strata.Administration.Diagnostic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace Strata.Administration.BasicDiagnosticCheck
{
    [TestClass]
    public 
    class DatabaseConnectionCheckTest
    {
	    private DatabaseConnectionCheck target;

        [TestInitialize]
	    public void 
        SetUp()
	    {
		    target = new DatabaseConnectionCheck( "DatabaseConnect" );
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
            target.ConnectionString = @"Server=PHX1VDEVSQL103\CGSQL;initial catalog=TbaManager;User Id=tbadbtest;Password=tbamm1sql;";
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
            target.ConnectionString = @"Server=L0115057\NOSUCHSERVER;initial catalog=TbaManager1;Integrated Security=True";
		    target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( target.Name )  );
            Assert.AreEqual( 0,result.GetNumberOfSuccesses() );
            Assert.AreEqual( 1,result.GetNumberOfFailures() );
	    }
    }
}
