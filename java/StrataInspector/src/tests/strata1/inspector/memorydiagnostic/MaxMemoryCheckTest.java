// ##########################################################################
// # File Name:	MaxMemoryCheckTest.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInspector Framework.
// #
// #   			The StrataInspector Framework is Max software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Max Software Foundation, either version 3 of the 
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

package strata1.inspector.memorydiagnostic;

import static org.junit.Assert.assertEquals;
import strata1.inspector.diagnostic.MockDiagnosticResult;
import strata1.inspector.outputstreamdiagnosticreporter.
                                PrintStreamDiagnosticReporter;
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
class MaxMemoryCheckTest
{
	private static final long MEGABYTE = 1000000L;
	
	private MaxMemoryCheck itsTarget;
	
	
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
		itsTarget = new MaxMemoryCheck( "MaxMemoryCheck" );
		itsTarget.setMaxRange( new MemoryRange(10*MEGABYTE,Long.MAX_VALUE) );
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
	}

	/**
	 * Test method for {@link MaxMemoryCheck#MaxMemoryCheck(java.lang.String)}.
	 */
	@Test
	public void 
	testMaxMemoryCheck()
	{
		itsTarget = new MaxMemoryCheck( "Foo" );
		
		assertEquals( 0L,itsTarget.getMaxRange().getMinimumBtyes() );
		assertEquals( 
		    Long.MAX_VALUE,
		    itsTarget.getMaxRange().getMaximumBtyes() );
	}

	/**
	 * Test method for {@link MaxMemoryCheck#setMaxRange(MemoryRange)}.
	 */
	@Test
	public void 
	testSetMaxRange()
	{
		MemoryRange expected = new MemoryRange( 10L,20L );
		
		itsTarget.setMaxRange( expected );
		assertEquals( expected,itsTarget.getMaxRange() );
	}

	/**
	 * Test method for {@link DiagnosticCheck#runDiagnostic(DiagnosticResult)}.
	 */
	@Test
	public void 
	testRunDiagnostic()
	{
		MockDiagnosticResult result = new MockDiagnosticResult();
		
		result.attachReporter( new PrintStreamDiagnosticReporter() );
		result.beginReports();
		itsTarget.runDiagnostic( result );
		result.endReports();
	}

}


// ##########################################################################
