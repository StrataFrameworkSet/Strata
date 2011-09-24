// ##########################################################################
// # File Name:	SingletonProxyTest.java
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

package strata1.common.utility;

import static org.junit.Assert.*;
import strata1.common.money.Money;
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
public class SingletonProxyTest
{
    private CopyableObject itsExpected;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        itsExpected = new CopyableObject( "TestSingleton",23,3.5 );
        SingletonProxy.setInstance( CopyableObject.class,itsExpected );
    }

    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        SingletonProxy.clearInstance( CopyableObject.class );
        itsExpected = null;
    }

    /**
     * Test method for {@link strata1.common.utility.SingletonProxy#setInstance(java.lang.Class, java.lang.Object)}.
     */
    @Test
    public void testSetInstance()
    {
        Money expected = new Money( 23.57 );
        SingletonProxy.setInstance( Money.class,expected );
        
        assertEquals( expected,SingletonProxy.getInstance( Money.class ) );
    }

    /**
     * Test method for {@link strata1.common.utility.SingletonProxy#clearInstance(java.lang.Class)}.
     */
    @Test
    public void testClearInstance()
    {
        SingletonProxy.clearInstance( CopyableObject.class );
    }

    /**
     * Test method for {@link strata1.common.utility.SingletonProxy#getInstance(java.lang.Class)}.
     */
    @Test
    public void testGetInstance()
    {
        Money expected = new Money( 23.57 );
        SingletonProxy.setInstance( Money.class,expected );
        
        assertEquals( expected,SingletonProxy.getInstance( Money.class ) );
    }

}

// ##########################################################################
