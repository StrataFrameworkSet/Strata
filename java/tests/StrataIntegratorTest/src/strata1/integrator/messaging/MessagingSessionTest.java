// ##########################################################################
// # File Name:	MessagingSessionTest.java
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
class MessagingSessionTest
{
    private IMessagingSession itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = createMessagingSesssion();
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
     * Test method for {@link IMessagingSession#createMessageSender(String)}.
     */
    @Test
    public void 
    testCreateMessageSender()
    {
        IMessageSender sender = itsTarget.createMessageSender( "foo" );
        
        assertNotNull( sender );
        assertEquals( itsTarget,sender.getSession() );
    }

    /**
     * Test method for {@link IMessagingSession#createMessageReceiver(String)}.
     */
    @Test
    public void 
    testCreateMessageReceiverString()
    {
        IMessageReceiver receiver = itsTarget.createMessageReceiver( "foo" );
        
        assertNotNull( receiver );
        assertEquals( itsTarget,receiver.getSession() );
        assertNull( receiver.getListener() );
        assertEquals( "",receiver.getSelector() );
    }

    /**
     * Test method for {@link IMessagingSession#createMessageReceiver(String,String)}.
     */
    @Test
    public void 
    testCreateMessageReceiverStringString()
    {
        String           selector = "ReturnAddress=foo";
        IMessageReceiver receiver = 
            itsTarget.createMessageReceiver( "foo",selector );
        
        assertNotNull( receiver );
        assertEquals( itsTarget,receiver.getSession() );
        assertNull( receiver.getListener() );
        assertEquals( selector,receiver.getSelector() );
    }

    /**
     * Test method for {@link IMessagingSession#createStringMessage()}.
     */
    @Test
    public void 
    testCreateStringMessage()
    {
        IStringMessage message = itsTarget.createStringMessage();
        
        assertNotNull( message );
        assertNull( message.getPayload() );
    }

    /**
     * Test method for {@link IMessagingSession#createMapMessage()}.
     */
    @Test
    public void 
    testCreateMapMessage()
    {
        IMapMessage message = itsTarget.createMapMessage();
        
        assertNotNull( message );
        assertTrue( message.getItemKeys().isEmpty() );
    }

    /**
     * Test method for {@link IMessagingSession#createObjectMessage()}.
     */
    @Test
    public void 
    testCreateObjectMessage()
    {
        IObjectMessage message = itsTarget.createObjectMessage();
        
        assertNotNull( message );
        assertNull( message.getPayload() );
    }

    /**
     * Test method for {@link IMessagingSession#startReceiving()}.
     */
    @Test
    public void 
    testStartReceiving()
    {
        assertFalse( itsTarget.isReceiving() );
        itsTarget.startReceiving();
        assertTrue( itsTarget.isReceiving() );
        itsTarget.startReceiving();
        assertTrue( itsTarget.isReceiving() );
    }

    /**
     * Test method for {@link IMessagingSession#stopReceiving()}.
     */
    @Test
    public void 
    testStopReceiving()
    {
        assertFalse( itsTarget.isReceiving() );
        itsTarget.startReceiving();
        assertTrue( itsTarget.isReceiving() );
        itsTarget.stopReceiving();
        assertFalse( itsTarget.isReceiving() );
        itsTarget.stopReceiving();
        assertFalse( itsTarget.isReceiving() );
    }

    /**
     * Test method for {@link IMessagingSession#isReceiving()}.
     */
    @Test
    public void 
    testIsReceiving()
    {
        assertFalse( itsTarget.isReceiving() );
        itsTarget.startReceiving();
        assertTrue( itsTarget.isReceiving() );
        itsTarget.startReceiving();
        assertTrue( itsTarget.isReceiving() );
        itsTarget.stopReceiving();
        assertFalse( itsTarget.isReceiving() );
        itsTarget.stopReceiving();
        assertFalse( itsTarget.isReceiving() );
    }

    protected abstract IMessagingSession 
    createMessagingSesssion();

}

// ##########################################################################
