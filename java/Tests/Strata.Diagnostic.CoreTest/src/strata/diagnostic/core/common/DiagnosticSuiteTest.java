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

package strata.diagnostic.core.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static strata.foundation.core.utility.Awaiter.await;

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
	 * @throws Exception
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
	 * @throws Exception
	 */
	@After
	public void 
	tearDown() 
		throws Exception
	{
		itsTarget = null;
	}

	@Test
	public void 
	testRunDiagnostic()
	{
		MockDiagnosticResult result = new MockDiagnosticResult();
		
		await(itsTarget.runDiagnostic( result ));
	}

	@Test
	public void 
	testAddDiagnostic()
	{
	}

	@Test
	public void 
	testRemoveDiagnostic()
	{
	}

	@Test
	public void 
	testGetDiagnostic()
	{
	}

	@Test
	public void 
	testHasDiagnostic()
	{
	}

}


// ##########################################################################
