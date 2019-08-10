// ##########################################################################
// # File Name:	CopyableTest.java
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

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CopyableTest
{
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

    /**
     * Test method for {@link strata1.common.utility.ICopyable#copy()}.
     */
    @Test
    public void 
    testCopy()
    {
        CopyableObject expected = new CopyableObject( "X",23,23.57 );
        CopyableObject actual   = expected.copy();
        
        assertEquals( expected,actual );
    }

}

// ##########################################################################
