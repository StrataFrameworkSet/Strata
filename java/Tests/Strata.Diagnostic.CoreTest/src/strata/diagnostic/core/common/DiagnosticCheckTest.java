// ##########################################################################
// # File Name:		DiagnosticCheckTest.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata.diagnostic.core.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.diagnostic.core.evaluation.DiagnosticCheck;

import static org.junit.Assert.assertEquals;
import static strata.foundation.core.utility.Awaiter.await;


/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class DiagnosticCheckTest
{
	private DiagnosticCheck itsTarget;
	private static final String NAME = "XYZ";

	/************************************************************************
	 *  
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		itsTarget = new MockDiagnosticCheck( NAME,true,true,true,false );
	}

	/************************************************************************
	 *  
	 *
	 * @throws Exception
	 */
	@After
	public void
	tearDown() throws Exception
	{
		itsTarget = null;
	}

	/**
	 * Test method for 
	 * {@link DiagnosticCheck#DiagnosticCheck(String)}.
	 */
	@Test
	public void
	testDiagnosticCheck()
	{
		assertEquals( 
		    NAME,
		    new MockDiagnosticCheck( NAME,true,true,true,true ).getName() );
	}

	/**
	 * Test method for 
	 * {@link DiagnosticCheck#runDiagnostic(IDiagnosticResult)}.
	 */
	@Test
	public void
	testRunDiagnostic()
	{
		MockDiagnosticResult   result   = new MockDiagnosticResult();
		MockDiagnosticReporter reporter = new MockDiagnosticReporter();
		
		result.attachReporter( reporter );
		await(itsTarget.runDiagnostic( result ));
		result.verify();
	}

}


// ##########################################################################
