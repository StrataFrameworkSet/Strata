using Moq;
using NUnit.Framework;
using Strata.Diagnostic.Core;

namespace Strata.Diagnostic.Database
{
    [TestFixture]
    public 
    class DatabaseConnectionCheckTest
    {
	    private DatabaseConnectionCheck target;

        [SetUp]
	    public void 
        SetUp()
	    {
		    target = new DatabaseConnectionCheck( "DatabaseConnect" );
	    }

        [TearDown]
	    public void 
        TearDown()
	    {
		    target = null;
	    }

        [Ignore("skipped")]
        [Test]
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

        [Test]
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
