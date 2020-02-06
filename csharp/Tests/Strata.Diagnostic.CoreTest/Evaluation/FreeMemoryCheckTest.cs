using System.Threading.Tasks;
using NUnit.Framework;
using Strata.Diagnostic.Core.Common;
using Strata.Diagnostic.Core.Report;

namespace Strata.Diagnostic.Core.Evaluation
{
    [TestFixture]
    public 
    class FreeMemoryCheckTest
    {
	    private FreeMemoryCheck target;
        private const long MB = 1000000;

        [SetUp]
	    public void 
        SetUp()
	    {
		    target = new FreeMemoryCheck( "FreeMemory" );
	    }

        [TearDown]
	    public void 
        TearDown()
	    {
		    target = null;
	    }

        [Test]
	    public async Task
        TestRunDiagnosticSuccess()
	    {
		    MockDiagnosticResult result   = new MockDiagnosticResult();
		    IDiagnosticReporter  reporter = new TextWriterDiagnosticReporter();
		
		    result.AttachReporter( reporter );
            target.FreeMemoryBytesMinimum = 100*MB;
		    await target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( target.Name )  );
            Assert.AreEqual( 1,result.GetNumberOfSuccesses() );
            Assert.AreEqual( 0,result.GetNumberOfFailures() );
	    }

        [Test]
	    public async Task
        TestRunDiagnosticFailure()
	    {
		    MockDiagnosticResult result   = new MockDiagnosticResult();
		    IDiagnosticReporter  reporter = new TextWriterDiagnosticReporter();
		
		    result.AttachReporter( reporter );
            target.FreeMemoryBytesMinimum = 25000*MB;
		    await target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( target.Name )  );
            Assert.AreEqual( 0,result.GetNumberOfSuccesses() );
            Assert.AreEqual( 1,result.GetNumberOfFailures() );
	    }
    }
}
