// ##########################################################################
// # File Name:	TotalMemoryCheckTest.java
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

package strata.diagnostic.core.evaluation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.diagnostic.core.common.MockDiagnosticResult;
import strata.diagnostic.core.report.PrintStreamDiagnosticReporter;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TotalMemoryCheckTest
{
	private static final long MEGABYTE = 1000000L;
	
	private TotalMemoryCheck itsTarget;
	
	
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
		itsTarget = new TotalMemoryCheck( "TotalMemoryCheck" );
		itsTarget.setTotalRange( new MemoryRange(10*MEGABYTE,Long.MAX_VALUE) );
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
	}

	@Test
	public void 
	testTotalMemoryCheck()
	{
		itsTarget = new TotalMemoryCheck( "Foo" );
		
		assertEquals( 0L,itsTarget.getTotalRange().getMinimumBtyes() );
		assertEquals( Long.MAX_VALUE,itsTarget.getTotalRange().getMaximumBtyes() );
	}

	@Test
	public void 
	testSetTotalRange()
	{
		MemoryRange expected = new MemoryRange( 10L,20L );
		
		itsTarget.setTotalRange( expected );
		assertEquals( expected,itsTarget.getTotalRange() );
	}

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
