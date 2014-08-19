// ##########################################################################
// # File Name:	MessageReceiverTest.java
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
class MessageReceiverTest
{
    private IMessagingSession itsSession;
    private IMessageSender    itsSender;
    private IMessageReceiver  itsTarget;

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
        itsSession = createMessagingSession();
        itsSender  = itsSession.createMessageSender( "foo.test" );
        itsTarget  = itsSession.createMessageReceiver( "foo.test" );
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
        itsSender = null;
        itsSession = null;
    }

    /**
     * Test method for {@link IMessageReceiver#setListener(IMessageListener)}.
     * @throws MixedModeException 
     */
    @Test
    public void 
    testSetListener() 
        throws MixedModeException
    {
        IMessageListener listener = new MockMessageListener();
        
        assertNull( itsTarget.getListener() );
        itsTarget.setListener( listener );
        assertNotNull( itsTarget.getListener() );
        assertEquals( listener,itsTarget.getListener() );
    }

    /**
     * Test method for {@link IMessageReceiver#setSelector(ISelector)}.
     */
    @Test
    public void 
    testSetSelector()
    {
        ISelector selector = createSelector( "ReturnAddress=foo" );
        
        assertEquals( "",itsTarget.getSelector().toString() );
        itsTarget.setSelector( selector );
        assertNotNull( itsTarget.getSelector() );
        assertEquals( selector,itsTarget.getSelector() );
    }

    /**
     * Test method for {@link IMessageReceiver#getSession()}.
     */
    @Test
    public void 
    testGetSession()
    {
        assertEquals( itsSession,itsTarget.getSession() );
    }

    /**
     * Test method for {@link IMessageReceiver#getListener()}.
     * @throws MixedModeException 
     */
    @Test
    public void 
    testGetListener() 
        throws MixedModeException
    {
        IMessageListener listener = new MockMessageListener();
        
        assertNull( itsTarget.getListener() );
        itsTarget.setListener( listener );
        assertNotNull( itsTarget.getListener() );
        assertEquals( listener,itsTarget.getListener() );
    }

    /**
     * Test method for {@link IMessageReceiver#getSelector()}.
     */
    @Test
    public void 
    testGetSelector()
    {
        ISelector selector = createSelector( "ReturnAddress=foo" );
        
        assertEquals( "",itsTarget.getSelector().toString() );
        itsTarget.setSelector( selector );
        assertNotNull( itsTarget.getSelector() );
        assertEquals( selector,itsTarget.getSelector() );
    }

    /**
     * Test method for {@link IMessageReceiver#startListening()}.
     * @throws MixedModeException 
     */
    @Test
    public void 
    testStartListening() throws MixedModeException
    {
        IMessageListener listener = new MockMessageListener();
        
        assertFalse( itsTarget.isListening() );
        
        itsTarget
            .setListener( listener )
            .startListening();
        
        assertTrue( itsTarget.isListening() );
    }

    /**
     * Test method for {@link IMessageReceiver#stopListening()}.
     * @throws MixedModeException 
     */
    @Test
    public void 
    testStopListening() 
        throws MixedModeException
    {
        IMessageListener listener = new MockMessageListener();
        
        assertFalse( itsTarget.isListening() );       
        itsTarget
            .setListener( listener )
            .startListening();
        
        assertTrue( itsTarget.isListening() );
        itsTarget.stopListening();
        assertFalse( itsTarget.isListening() );               
    }

    /**
     * Test method for {@link IMessageReceiver#isListening()}.
     * @throws MixedModeException 
     */
    @Test
    public void 
    testIsListening() 
        throws MixedModeException
    {
        IMessageListener listener = new MockMessageListener();
        
        assertFalse( itsTarget.isListening() );       
        itsTarget
            .setListener( listener )
            .startListening();
        
        assertTrue( itsTarget.isListening() );
        itsTarget.stopListening();
        assertFalse( itsTarget.isListening() );               
    }

    /**
     * Test method for {@link IMessageReceiver#receive()}.
     * @throws MixedModeException 
     */
    @Test
    public void 
    testReceive() 
        throws MixedModeException
    {
        IStringMessage expected1 = itsSession.createStringMessage();
        IStringMessage expected2 = itsSession.createStringMessage();
        IStringMessage expected3 = itsSession.createStringMessage();
        IMessage       actual1   = null;
        IMessage       actual2   = null;
        IMessage       actual3   = null;
        
        expected1.setPayload( "TestMessage1" );
        expected2.setPayload( "TestMessage2" );
        expected3.setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );
        
        actual1 = itsTarget.receive();
        actual2 = itsTarget.receive();
        actual3 = itsTarget.receive();
        
        assertTrue( actual1 instanceof IStringMessage );
        assertTrue( actual2 instanceof IStringMessage );
        assertTrue( actual3 instanceof IStringMessage );
               
        assertEquals( expected1.getPayload(),((IStringMessage)actual1).getPayload() );
        assertEquals( expected2.getPayload(),((IStringMessage)actual2).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actual3).getPayload() );
    }

    /**
     * Test method for {@link IMessageReceiver#receive(long)}.
     * @throws MixedModeException 
     * @throws NoMessageReceivedException 
     */
    @Test
    public void 
    testReceiveLong() 
        throws MixedModeException,NoMessageReceivedException
    {
        IStringMessage expected1 = itsSession.createStringMessage();
        IStringMessage expected2 = itsSession.createStringMessage();
        IStringMessage expected3 = itsSession.createStringMessage();
        IMessage       actual1   = null;
        IMessage       actual2   = null;
        IMessage       actual3   = null;
        
        expected1.setPayload( "TestMessage1" );
        expected2.setPayload( "TestMessage2" );
        expected3.setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );
        
        actual1 = itsTarget.receive( 10 );
        actual2 = itsTarget.receive( 10);
        actual3 = itsTarget.receive( 10 );
        
        assertTrue( actual1 instanceof IStringMessage );
        assertTrue( actual2 instanceof IStringMessage );
        assertTrue( actual3 instanceof IStringMessage );
               
        assertEquals( expected1.getPayload(),((IStringMessage)actual1).getPayload() );
        assertEquals( expected2.getPayload(),((IStringMessage)actual2).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actual3).getPayload() );
    }

    /**
     * Test method for {@link IMessageReceiver#receiveNoWait()}.
     * @throws NoMessageReceivedException 
     * @throws MixedModeException 
     */
    @Test
    public void 
    testReceiveNoWait1() 
        throws MixedModeException,NoMessageReceivedException
    {
        IStringMessage expected1 = itsSession.createStringMessage();
        IStringMessage expected2 = itsSession.createStringMessage();
        IStringMessage expected3 = itsSession.createStringMessage();
        IMessage       actual1   = null;
        IMessage       actual2   = null;
        IMessage       actual3   = null;
        
        expected1.setPayload( "TestMessage1" );
        expected2.setPayload( "TestMessage2" );
        expected3.setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );
        
        actual1 = itsTarget.receiveNoWait();
        actual2 = itsTarget.receiveNoWait();
        actual3 = itsTarget.receiveNoWait();
        
        assertTrue( actual1 instanceof IStringMessage );
        assertTrue( actual2 instanceof IStringMessage );
        assertTrue( actual3 instanceof IStringMessage );
               
        assertEquals( expected1.getPayload(),((IStringMessage)actual1).getPayload() );
        assertEquals( expected2.getPayload(),((IStringMessage)actual2).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actual3).getPayload() );
    }
 
    /**
     * Test method for {@link IMessageReceiver#receiveNoWait()}.
     * @throws NoMessageReceivedException 
     * @throws MixedModeException 
     */
    @Test
    public void 
    testReceiveNoWait2() 
        throws MixedModeException,NoMessageReceivedException
    {
        IStringMessage expected1 = itsSession.createStringMessage();
        IStringMessage expected2 = itsSession.createStringMessage();
        IStringMessage expected3 = itsSession.createStringMessage();
        IMessage       actual1   = null;
        IMessage       actual2   = null;
        IMessage       actual3   = null;
        
        itsTarget.setSelector( createSelector("ReturnAddress=foo" ) );
        
        expected1
            .setReturnAddress( "foo" )
            .setPayload( "TestMessage1" );
        
        expected2.setPayload( "TestMessage2" );
        expected3
            .setReturnAddress( "foo" )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );
        
        actual1 = itsTarget.receiveNoWait();
        actual3 = itsTarget.receiveNoWait();
        
        assertTrue( actual1 instanceof IStringMessage );
        assertTrue( actual3 instanceof IStringMessage );
               
        assertEquals( expected1.getPayload(),((IStringMessage)actual1).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actual3).getPayload() );
        
        try
        {
            actual2 = itsTarget.receiveNoWait();
            fail( "Should have thrown exception." );
        }
        catch (NoMessageReceivedException e) {}
    }

    protected abstract IMessagingSession 
    createMessagingSession();

    protected abstract ISelector
    createSelector(String expression);
}

// ##########################################################################
