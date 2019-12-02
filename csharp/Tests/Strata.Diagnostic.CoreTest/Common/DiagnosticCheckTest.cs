using System;
using System.Threading.Tasks;
using Moq;
using NUnit.Framework;
using Strata.Diagnostic.Core.Evaluation;

namespace Strata.Diagnostic.Core.Common
{
    [TestFixture]
    public 
    class DiagnosticCheckTest
    {
	    private DiagnosticCheck target;
	    private static string   NAME = "XYZ";

        [SetUp]
	    public void 
        SetUp()
	    {
		    target = new MockDiagnosticCheck( NAME,true,true,true,false );
	    }

        [TearDown]
	    public void 
        TearDown()
	    {
		    target = null;
	    }

        [Test]
	    public void 
        TestDiagnosticCheck()
	    {
		    Assert.AreEqual( 
		        NAME,
		        new MockDiagnosticCheck( NAME,true,true,true,true ).Name );
	    }

        [Test]
	    public async Task 
        TestRunDiagnostic()
	    {
		    MockDiagnosticResult result   = new MockDiagnosticResult();
		    IDiagnosticReporter  reporter = new Mock<IDiagnosticReporter>().Object;
		
		    result.AttachReporter( reporter );
		    await target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( NAME )  );
	    }
    }
}
