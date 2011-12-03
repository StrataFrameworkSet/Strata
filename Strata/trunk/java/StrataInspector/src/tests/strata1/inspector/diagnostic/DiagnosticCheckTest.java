// ##########################################################################
// # File Name:		DiagnosticCheckTest.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.inspector.diagnostic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class DiagnosticCheckTest
{
	private DiagnosticCheck     itsTarget;
	private static final String NAME = "XYZ";

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		itsTarget = new MockDiagnosticCheck( NAME,true,true,true,false );
	}

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		itsTarget = null;
	}

	/**
	 * Test method for {@link strata1.server.diagnostics.DiagnosticCheck#DiagnosticCheck(java.lang.String)}.
	 */
	@Test
	public void testDiagnosticCheck()
	{
		assertEquals( NAME,new MockDiagnosticCheck( NAME,true,true,true,true ).getName() );
	}

	/**
	 * Test method for {@link strata1.server.diagnostics.DiagnosticCheck#runDiagnostic(strata1.server.diagnostics.DiagnosticResult)}.
	 */
	@Test
	public void testRunDiagnostic()
	{
		MockDiagnosticResult   result   = new MockDiagnosticResult();
		MockDiagnosticReporter reporter = new MockDiagnosticReporter();
		
		result.attachReporter( reporter );
		itsTarget.runDiagnostic( result );
		result.verify();
	}

}


// ##########################################################################
