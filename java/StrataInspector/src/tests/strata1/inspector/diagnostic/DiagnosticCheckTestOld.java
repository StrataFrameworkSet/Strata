package strata1.inspector.diagnostic;

import junit.framework.TestCase;

public class DiagnosticCheckTestOld extends TestCase
{
	private DiagnosticCheck     itsTarget;
	private static final String NAME = "XYZ";
	
	public DiagnosticCheckTestOld(String arg0)
	{
		super( arg0 );
		itsTarget = null;
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		itsTarget = new MockDiagnosticCheck( NAME,true,true,true,false );
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
		itsTarget = null;
	}

	/*
	 * Test method for 'com.capgroup.af.solar.application.DiagnosticCheck.DiagnosticCheck(String)'
	 */
	public void testDiagnosticCheck()
	{
		assertEquals( NAME,new MockDiagnosticCheck( NAME,true,true,true,true ).getName() );
	}

	/*
	 * Test method for 'com.capgroup.af.solar.application.DiagnosticCheck.runDiagnostic(IDiagnosticResult)'
	 */
	public void testRunDiagnostic()
	{
		MockDiagnosticResult result = new MockDiagnosticResult();
		
		itsTarget.runDiagnostic( result );
		result.verify();
	}
}


// ##########################################################################
