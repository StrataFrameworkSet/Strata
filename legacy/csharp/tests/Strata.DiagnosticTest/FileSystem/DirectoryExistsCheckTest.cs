using Moq;
using NUnit.Framework;
using Strata.Diagnostic.Core;

namespace Strata.Diagnostic.FileSystem
{
    [TestFixture]
    public 
    class DirectoryExistsCheckTest
    {
	    private DirectoryExistsCheck target;

        [SetUp]
	    public void 
        SetUp()
	    {
		    target = new DirectoryExistsCheck( "TempDirExists" );
	    }

        [TearDown]
	    public void 
        TearDown()
	    {
		    target = null;
	    }

        [Test]
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

        [Test]
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
