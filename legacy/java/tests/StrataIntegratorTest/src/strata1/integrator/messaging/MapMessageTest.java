// ##########################################################################
// # File Name:	MapMessageTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegratorTest Framework.
// #
// #   			The StrataIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.integrator.messaging;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class MapMessageTest
    extends MessageTest
{
    private IMapMessage itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        super.setUp();
        itsTarget = getTarget();
    }

    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @After
    public void 
    tearDown() throws Exception
    {
        itsTarget = null;
        super.tearDown();
    }

    /**
     * Test method for {@link IMessage#setByte(String, byte)}.
     * Test method for {@link IMessage#getByte(String)}.
     */
    @Test
    public void 
    testSetGetByte()
    {
        byte expected = 5;
        
        itsTarget.setByte( "Foo",expected );
        assertEquals( expected,itsTarget.getByte( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setBytes(String, byte[])}.
     * Test method for {@link IMessage#getBytes(String)}.
     */
    @Test
    public void 
    testSetGetBytes()
    {
        byte[] expected = "abc".getBytes();
        
        itsTarget.setBytes( "Foo",expected );
        assertEquals( 3,itsTarget.getBytes( "Foo" ).length );
        assertEquals( expected[0],itsTarget.getBytes( "Foo" )[0] );
        assertEquals( expected[1],itsTarget.getBytes( "Foo" )[1] );
        assertEquals( expected[2],itsTarget.getBytes( "Foo" )[2] );
    }

    /**
     * Test method for {@link IMessage#setBoolean(String, boolean)}.
     * Test method for {@link IMessage#getBoolean(String)}.
     */
    @Test
    public void 
    testSetGetBoolean()
    {
        boolean expected = true;
        
        itsTarget.setBoolean( "Foo",expected );
        assertEquals( expected,itsTarget.getBoolean( "Foo" ) );
        
        expected = false;
        itsTarget.setBoolean( "Foo",expected );
        assertEquals( expected,itsTarget.getBoolean( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setShort(String, short)}.
     * Test method for {@link IMessage#getShort(String)}.
     */
    @Test
    public void 
    testSetGetShort()
    {
        short expected = 7;
        
        itsTarget.setShort( "Foo",expected );
        assertEquals( expected,itsTarget.getShort( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setInt(String, int)}.
     * Test method for {@link IMessage#getInt(String,)}.
     */
    @Test
    public void 
    testSetGetInt()
    {
        int expected = 11;
        
        itsTarget.setInt( "Foo",expected );
        assertEquals( expected,itsTarget.getInt( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setLong(String, long)}.
     * Test method for {@link IMessage#getLong(String)}.
     */
    @Test
    public void 
    testSetGetLong()
    {
        long expected = 13;
        
        itsTarget.setLong( "Foo",expected );
        assertEquals( expected,itsTarget.getLong( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setFloat(String, float)}.
     * Test method for {@link IMessage#getFloat(String)}.
     */
    @Test
    public void 
    testSetGetFloat()
    {
        float expected = 17.19f;
        
        itsTarget.setFloat( "Foo",expected );
        assertEquals( expected,itsTarget.getFloat( "Foo" ),0.0000001 );
    }

    /**
     * Test method for {@link IMessage#setDouble(String, double)}.
     * Test method for {@link IMessage#getDouble(String)}.
     */
    @Test
    public void 
    testSetGetDouble()
    {
        double expected = 23.29;
        
        itsTarget.setDouble( "Foo",expected );
        assertEquals( expected,itsTarget.getDouble( "Foo" ),0.0000001 );
    }

    /**
     * Test method for {@link IMessage#setString(String,String)}.
     * Test method for {@link IMessage#getString(String)}.
     */
    @Test
    public void 
    testSetGetString()
    {
        String expected = "expected-string-value";
        
        itsTarget.setString( "Foo",expected );
        assertEquals( expected,itsTarget.getString( "Foo" ));
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    protected abstract IMapMessage
    getTarget();

}

// ##########################################################################
