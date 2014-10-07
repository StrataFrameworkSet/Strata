// ##########################################################################
// # File Name:	MessageTest.java
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

import static org.junit.Assert.*;
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
class MessageTest
{
    private IMessage itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
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
    }

    /**
     * Test method for {@link IMessage#setMessageId(String)}.
     * Test method for {@link IMessage#getMessageId()}.
     */
    @Test
    public void 
    testSetGetMessageId()
    {
        String expectedId = "1a2b3c4d5e6f7g";
        
        itsTarget.setMessageId( expectedId );        
        assertEquals( expectedId,itsTarget.getMessageId() );
    }

    /**
     * Test method for {@link IMessage#setCorrelationId(String)}.
     * Test method for {@link IMessage#getCorrelationId()}.
     */
    @Test
    public void 
    testSetGetCorrelationId()
    {
        String expectedId = "a1b2c3d4e5f6g7";
        
        itsTarget.setCorrelationId( expectedId );        
        assertEquals( expectedId,itsTarget.getCorrelationId() );
        
        expectedId = "xxxxxxxxxxxxxxxxxxxxx";
        itsTarget.setCorrelationId( expectedId );        
        assertEquals( expectedId,itsTarget.getCorrelationId() );
        
    }

    /**
     * Test method for {@link IMessage#setReturnAddress(String)}.
     * Test method for {@link IMessage#getReturnAddress()}.
     */
    @Test
    public void 
    testSetGetReturnAddress()
    {
        String expectedAddress = "xxxxxyyyyzzzz";
        
        itsTarget.setReturnAddress( expectedAddress );        
        assertEquals( expectedAddress,itsTarget.getReturnAddress() );
    }

    /**
     * Test method for {@link IMessage#setDeliveryMode(DeliveryMode)}.
     * Test method for {@link IMessage#getDeliveryMode()}.
     */
    @Test
    public void 
    testSetGetDeliveryMode()
    {
        DeliveryMode expected = DeliveryMode.PERSISTENT;
        
        itsTarget.setDeliveryMode( expected );
        assertEquals( expected,itsTarget.getDeliveryMode() );
    }

    /**
     * Test method for {@link IMessage#setByteProperty(String, byte)}.
     * Test method for {@link IMessage#getByteProperty(String)}.
     */
    @Test
    public void 
    testSetGetByteProperty()
    {
        byte expected = 5;
        
        itsTarget.setByteProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getByteProperty( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setBooleanProperty(String, boolean)}.
     * Test method for {@link IMessage#getBooleanProperty(String)}.
     */
    @Test
    public void 
    testSetGetBooleanProperty()
    {
        boolean expected = true;
        
        itsTarget.setBooleanProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getBooleanProperty( "Foo" ) );
        
        expected = false;
        itsTarget.setBooleanProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getBooleanProperty( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setShortProperty(String, short)}.
     * Test method for {@link IMessage#getShortProperty(String)}.
     */
    @Test
    public void 
    testSetGetShortProperty()
    {
        short expected = 7;
        
        itsTarget.setShortProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getShortProperty( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setIntProperty(String, int)}.
     * Test method for {@link IMessage#getIntProperty(String,)}.
     */
    @Test
    public void 
    testSetGetIntProperty()
    {
        int expected = 11;
        
        itsTarget.setIntProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getIntProperty( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setLongProperty(String, long)}.
     * Test method for {@link IMessage#getLongProperty(String)}.
     */
    @Test
    public void 
    testSetGetLongProperty()
    {
        long expected = 13;
        
        itsTarget.setLongProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getLongProperty( "Foo" ) );
    }

    /**
     * Test method for {@link IMessage#setFloatProperty(String, float)}.
     * Test method for {@link IMessage#getFloatProperty(String)}.
     */
    @Test
    public void 
    testSetGetFloatProperty()
    {
        float expected = 17.19f;
        
        itsTarget.setFloatProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getFloatProperty( "Foo" ),0.0000001 );
    }

    /**
     * Test method for {@link IMessage#setDoubleProperty(String, double)}.
     * Test method for {@link IMessage#getDoubleProperty(String)}.
     */
    @Test
    public void 
    testSetGetDoubleProperty()
    {
        double expected = 23.29;
        
        itsTarget.setDoubleProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getDoubleProperty( "Foo" ),0.0000001 );
    }

    /**
     * Test method for {@link IMessage#setStringProperty(String,String)}.
     * Test method for {@link IMessage#getStringProperty(String)}.
     */
    @Test
    public void 
    testSetGetStringProperty()
    {
        String expected = "expected-string-value";
        
        itsTarget.setStringProperty( "Foo",expected );
        assertEquals( expected,itsTarget.getStringProperty( "Foo" ));
    }

    /**
     * Test method for {@link IMessage#hasProperty(String)}.
     */
    @Test
    public void testHasProperty()
    {
        String expected = "expected-string-value";
        
        assertFalse( itsTarget.hasProperty( "Foo" ));
        itsTarget.setStringProperty( "Foo",expected );
        assertTrue( itsTarget.hasProperty( "Foo" ));
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected abstract IMessage 
    getTarget();

}

// ##########################################################################
