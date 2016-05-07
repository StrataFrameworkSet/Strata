// ##########################################################################
// # File Name:	DiagnosticSuiteTest.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInspector Framework.
// #
// #   			The StrataInspector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInspector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInspector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.inspector.diagnostic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DiagnosticSuiteTest
{
	private DiagnosticSuite itsTarget;
	
	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void 
	setUp() 
		throws Exception
	{
		itsTarget = new DiagnosticSuite( "Foo Suite" );
	}

	/************************************************************************
	 *  
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void 
	tearDown() 
		throws Exception
	{
		itsTarget = null;
	}

	/**
	 * Test method for {@link strata1.inspector.diagnostic.DiagnosticSuite#runDiagnostic(strata1.inspector.diagnostic.IDiagnosticResult)}.
	 */
	@Test
	public void 
	testRunDiagnostic()
	{
		MockDiagnosticResult result = new MockDiagnosticResult();
		
		itsTarget.runDiagnostic( result );
	}

	/**
	 * Test method for {@link strata1.inspector.diagnostic.DiagnosticSuite#addDiagnostic(strata1.inspector.diagnostic.IDiagnostic)}.
	 */
	@Test
	public void 
	testAddDiagnostic()
	{
	}

	/**
	 * Test method for {@link strata1.inspector.diagnostic.DiagnosticSuite#removeDiagnostic(java.lang.String)}.
	 */
	@Test
	public void 
	testRemoveDiagnostic()
	{
	}

	/**
	 * Test method for {@link strata1.inspector.diagnostic.DiagnosticSuite#getDiagnostic(java.lang.String)}.
	 */
	@Test
	public void 
	testGetDiagnostic()
	{
	}

	/**
	 * Test method for {@link strata1.inspector.diagnostic.DiagnosticSuite#hasDiagnostic(java.lang.String)}.
	 */
	@Test
	public void 
	testHasDiagnostic()
	{
	}

}


// ##########################################################################
