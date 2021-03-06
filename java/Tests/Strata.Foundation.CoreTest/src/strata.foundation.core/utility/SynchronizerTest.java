// ##########################################################################
// # File Name:	SynchronizerTest.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.core.utility;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SynchronizerTest
{

    /************************************************************************
     *  
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /************************************************************************
     *  
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * Test method for 
     */
    @Test
    public void testLockForReading()
    {
        assertEquals( "","" );
    }

    /**
     * Test method for 
     */
    @Test
    public void testLockForWriting()
    {
    }

    /**
     * Test method for 
     */
    @Test
    public void testUnlockFromReading()
    {
    }

    /**
     * Test method for 
     */
    @Test
    public void testUnlockFromWriting()
    {
    }

    @Test
    public void
    testJoin()
        throws Exception
    {
        CompletionStage<String> stage =
            CompletableFuture.supplyAsync(() -> "Hello");

        assertEquals("Hello",stage.toCompletableFuture().join());
        assertEquals("Hello",stage.toCompletableFuture().join());
    }

}

// ##########################################################################
