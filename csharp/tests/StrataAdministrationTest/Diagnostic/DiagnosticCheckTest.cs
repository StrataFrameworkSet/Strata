using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace Strata.Administration.Diagnostic
{
    [TestClass]
    public 
    class DiagnosticCheckTest
    {
	    private DiagnosticCheck target;
	    private static String   NAME = "XYZ";

        [TestInitialize]
	    public void 
        SetUp()
	    {
		    target = new MockDiagnosticCheck( NAME,true,true,true,false );
	    }

        [TestCleanup]
	    public void 
        TearDown()
	    {
		    target = null;
	    }

        [TestMethod]
	    public void 
        TestDiagnosticCheck()
	    {
		    Assert.AreEqual( 
		        NAME,
		        new MockDiagnosticCheck( NAME,true,true,true,true ).Name );
	    }

        [TestMethod]
	    public void 
        TestRunDiagnostic()
	    {
		    MockDiagnosticResult result   = new MockDiagnosticResult();
		    IDiagnosticReporter  reporter = new Mock<IDiagnosticReporter>().Object;
		
		    result.AttachReporter( reporter );
		    target.RunDiagnostic( result );
		    Assert.IsTrue( result.ActualRun.Contains( NAME )  );
	    }
    }
}
