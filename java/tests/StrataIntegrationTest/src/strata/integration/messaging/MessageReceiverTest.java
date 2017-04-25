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

package strata.integration.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
        itsSender  = itsSession.createMessageSender( "foo-test" );
        itsTarget  = itsSession.createMessageReceiver( "foo-test" );
        
        itsSession.startReceiving();     
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
        itsTarget.close();
        cleanUpQueue( "foo-test" );
        itsSession.stopReceiving();
        itsTarget = null;
        itsSender = null;
        itsSession.close();
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
        String selector = "ReturnAddress='foo'";
        
        assertEquals( null,itsTarget.getSelector() );
        
        itsTarget.close();
        itsTarget = 
            itsSession.createMessageReceiver( 
                "foo-test",
                selector );
            
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
        itsTarget.stopListening();
        assertFalse( itsTarget.isListening() );               
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
        List<IMessage> actuals   = new ArrayList<IMessage>();
       
        expected1
            .setCorrelationId( "testReceive" )
            .setPayload( "TestMessage1" );
        expected2
            .setCorrelationId( "testReceive" )
            .setPayload( "TestMessage2" );
        expected3
            .setCorrelationId( "testReceive" )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );
        
        actuals.add( itsTarget.receive() );
        actuals.add( itsTarget.receive() );
        actuals.add( itsTarget.receive() );
        
        actuals = resequence( actuals );
        
        assertEquals( 3,actuals.size() );
        
        
        assertTrue( actuals.get(0) instanceof IStringMessage );
        assertTrue( actuals.get(1) instanceof IStringMessage );
        assertTrue( actuals.get(2) instanceof IStringMessage );
         
        
        assertEquals( expected1.getPayload(),((IStringMessage)actuals.get(0)).getPayload() );
        assertEquals( expected2.getPayload(),((IStringMessage)actuals.get(1)).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actuals.get(2)).getPayload() );
    }

    /**
     * Test method for {@link IMessageReceiver#receive(long)}.
     * @throws MixedModeException 
     * @throws NoMessageReceivedException 
     * @throws InterruptedException 
     */
    @Test
    public void 
    testReceiveLong1() 
        throws MixedModeException,NoMessageReceivedException,InterruptedException
    {
        IStringMessage expected1 = itsSession.createStringMessage();
        IStringMessage expected2 = itsSession.createStringMessage();
        IStringMessage expected3 = itsSession.createStringMessage();
        List<IMessage> actuals   = new ArrayList<IMessage>();
        
        expected1
            .setCorrelationId( "testReceiveLong1" )
            .setPayload( "TestMessage1" );
        expected2
            .setCorrelationId( "testReceiveLong1" )
            .setPayload( "TestMessage2" );
        expected3
            .setCorrelationId( "testReceiveLong1" )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );
        
        sleepIfNeeded( 20000 );
        
        actuals.add( itsTarget.receive(5000) );
        actuals.add( itsTarget.receive(5000) );
        actuals.add( itsTarget.receive(5000) );
        
        actuals = resequence( actuals );
        
        assertEquals( 3,actuals.size() );
        
        
        assertTrue( actuals.get(0) instanceof IStringMessage );
        assertTrue( actuals.get(1) instanceof IStringMessage );
        assertTrue( actuals.get(2) instanceof IStringMessage );
         
        
        assertEquals( expected1.getPayload(),((IStringMessage)actuals.get(0)).getPayload() );
        assertEquals( expected2.getPayload(),((IStringMessage)actuals.get(1)).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actuals.get(2)).getPayload() );
    }


    /**
     * Test method for {@link IMessageReceiver#receive(long)}.
     * @throws MixedModeException 
     * @throws NoMessageReceivedException 
     * @throws InterruptedException 
     */
    @Test
    public void 
    testReceiveLong2() 
        throws MixedModeException,NoMessageReceivedException,InterruptedException
    {
        IStringMessage expected1 = itsSession.createStringMessage();
        IMapMessage    expected2 = itsSession.createMapMessage();
        IStringMessage expected3 = itsSession.createStringMessage();
        List<IMessage> actuals   = new ArrayList<IMessage>();
        
        itsTarget.close();
        itsTarget = 
            itsSession.createMessageReceiver( 
                "foo-test",
                "FooProperty  >=  5" );
        
        expected1
            .setCorrelationId( "testReceiveLong2.1" )
            .setIntProperty( "FooProperty",3 )
            .setPayload( "TestMessage1" );
        expected2
            .setCorrelationId( "testReceiveLong2.2" )
            .setIntProperty( "FooProperty",5 )
            .setString( "TestMessage","TestMessage2" );
        expected3
            .setCorrelationId( "testReceiveLong2.3" )
            .setIntProperty( "FooProperty",7 )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );

        sleepIfNeeded( 20000 );

        actuals.add(itsTarget.receive( 10000 ));    
        actuals.add(itsTarget.receive( 10000 ));    
        
        actuals = 
            resequence(
                actuals,
                new Comparator<IMessage>()
                {
                    @Override
                    public int 
                    compare(IMessage x,IMessage y)
                    {
                        String xCorrelationId = x.getCorrelationId();
                        String yCorrelationId = y.getCorrelationId();
                        
                        return xCorrelationId.compareTo( yCorrelationId );
                    }     
                } );
        
        /*
        assertTrue( actual2 instanceof IMapMessage );
        assertTrue( actual3 instanceof IStringMessage );
        */       
        assertEquals( expected2.getString("TestMessage"),((IMapMessage)actuals.get(0)).getString("TestMessage") );
        assertEquals( expected3.getPayload(),((IStringMessage)actuals.get(1)).getPayload() );
        
        
        try
        {
            actuals.add( itsTarget.receive( 10 ) );
            fail( "Should have thrown exception." );
        }
        catch (NoMessageReceivedException e) {}

    }

    /**
     * Test method for {@link IMessageReceiver#receive(long)}.
     * @throws MixedModeException 
     * @throws NoMessageReceivedException 
     * @throws InterruptedException 
     */
    @Test
    public void 
    testReceiveLong3() 
        throws MixedModeException,NoMessageReceivedException,InterruptedException
    {
        IMessageReceiver receiver1 = null;
        IMessageReceiver receiver2 = null;
        IStringMessage   expected1 = itsSession.createStringMessage();
        IMapMessage      expected2 = itsSession.createMapMessage();
        IObjectMessage   expected3 = itsSession.createObjectMessage();
        List<IMessage>   actuals   = new ArrayList<IMessage>();
        IMessage         actual    = null;
        
        itsTarget.close();
        receiver1 = 
            itsSession.createMessageReceiver( 
                "foo-test",
                "FooProperty  >=  5" );
        receiver2 = 
            itsSession.createMessageReceiver( 
                "foo-test",
                "FooProperty  <  5" );
        
        expected1
            .setCorrelationId( "testReceiveLong3" )
            .setIntProperty( "FooProperty",3 )
            .setIntProperty( "SequenceNum",1 )
            .setPayload( "TestMessage1" );
        expected2
            .setCorrelationId( "testReceiveLong3" )
            .setIntProperty( "FooProperty",5 )
            .setIntProperty( "SequenceNum",2 )
            .setString( "TestMessage","TestMessage2" );
        expected3
            .setCorrelationId( "testReceiveLong3" )
            .setIntProperty( "FooProperty",7 )
            .setIntProperty( "SequenceNum",3 )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );

        sleepIfNeeded( 20000 );

        actuals.add( receiver1.receive( 10000 ) );    
        actuals.add( receiver1.receive( 10000 ) );    
        
        try
        {
            actuals.add( receiver1.receive( 10000 ) );    
            fail( "Should have thrown NoMessageReceivedException" );
        }
        catch (NoMessageReceivedException e) {}
          
        actuals = 
            resequence( 
                actuals,
                new Comparator<IMessage>()
                {
                    @Override
                    public int 
                    compare(IMessage x,IMessage y)
                    {
                        int seqX = x.getIntProperty( "SequenceNum" );
                        int seqY = y.getIntProperty( "SequenceNum" );
                        
                        return seqX - seqY;
                    }
                    
                } );
        
        assertTrue( actuals.get(0) instanceof IMapMessage );
        assertTrue( actuals.get(1) instanceof IObjectMessage );
              
        assertEquals( expected2.getString("TestMessage"),((IMapMessage)actuals.get(0)).getString("TestMessage") );
        assertEquals( expected3.getPayload(),((IObjectMessage)actuals.get(1)).getPayload() );
         
        actual = receiver2.receive( 10 );
        
        assertTrue( actual instanceof IStringMessage );
        assertEquals( expected1.getPayload(),((IStringMessage)actual).getPayload() );
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
        List<IMessage> actuals   = new ArrayList<IMessage>();
        
        expected1
            .setSequenceNum( 1L )
            .setCorrelationId( "testReceiveNoWait1" )
            .setPayload( "TestMessage1" );
        expected2
            .setSequenceNum( 2L )
            .setCorrelationId( "testReceiveNoWait1" )
            .setPayload( "TestMessage2" );
        expected3
            .setSequenceNum( 3L )
            .setCorrelationId( "testReceiveNoWait1" )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );

        sleepIfNeeded( 20000 );
        
        actuals.add( itsTarget.receiveNoWait() );
        actuals.add( itsTarget.receiveNoWait() );
        actuals.add( itsTarget.receiveNoWait() );
        
        actuals = 
            resequence( 
                actuals,                
                new Comparator<IMessage>() 
                {
                    @Override
                    public int 
                    compare(IMessage x,IMessage y)
                    {
                        return (int)(x.getSequenceNum()-y.getSequenceNum());
                    }
                } );
            
        assertEquals( 3,actuals.size() );
        
        
        assertTrue( actuals.get(0) instanceof IStringMessage );
        assertTrue( actuals.get(1) instanceof IStringMessage );
        assertTrue( actuals.get(2) instanceof IStringMessage );
         
        
        assertEquals( expected1.getPayload(),((IStringMessage)actuals.get(0)).getPayload() );
        assertEquals( expected2.getPayload(),((IStringMessage)actuals.get(1)).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actuals.get(2)).getPayload() );
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
        List<IMessage> actuals   = new ArrayList<IMessage>();
                
        itsTarget.close();
        itsTarget = 
            itsSession.createMessageReceiver( 
                "foo-test",
                "ReturnAddress='foo'" );
        
        expected1
            .setCorrelationId( "testReceiveNoWait2" )
            .setReturnAddress( "foo" )
            .setIntProperty( "SequenceNum",1 )
            .setPayload( "TestMessage1" );
        
        expected2
            .setCorrelationId( "testReceiveNoWait2" )
            .setIntProperty( "SequenceNum",2 )
            .setPayload( "TestMessage2" );
        expected3
            .setCorrelationId( "testReceiveNoWait2" )
            .setReturnAddress( "foo" )
            .setIntProperty( "SequenceNum",3 )
            .setPayload( "TestMessage3" );

        sleepIfNeeded( 20000 );

        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );
        
        actuals.add(  itsTarget.receiveNoWait() );
        actuals.add(  itsTarget.receiveNoWait() );
             
        actuals = 
            resequence( 
                actuals,
                new Comparator<IMessage>()
                {
                    @Override
                    public int 
                    compare(IMessage x,IMessage y)
                    {
                        int seqX = x.getIntProperty( "SequenceNum" );
                        int seqY = y.getIntProperty( "SequenceNum" );
                        
                        return seqX - seqY;
                    }
                    
                } );

        assertEquals( expected1.getPayload(),((IStringMessage)actuals.get(0)).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actuals.get(1)).getPayload() );
        
        try
        {
            actuals.add(  itsTarget.receiveNoWait() );
            fail( "Should have thrown exception." );
        }
        catch (NoMessageReceivedException e) {}
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected abstract IMessagingSession 
    createMessagingSession();

    /************************************************************************
     *  
     *
     * @return
     */
    protected long
    getCleanupTimeout()
    {
        return 2000;
    }
    
    /************************************************************************
     *  
     *
     * @param millis
     */
    protected void
    sleepIfNeeded(long millis) {}

    /************************************************************************
     * Resequences messages for platforms like SQS that do not guarantee FIFO 
     *
     * @param messages
     * @return
     */
    private List<IMessage> 
    resequence(List<IMessage> messages)
    {
        return 
            resequence( 
                messages,
                new Comparator<IMessage>() 
                {
                    @Override
                    public int 
                    compare(IMessage x,IMessage y)
                    {
                        IStringMessage m1 = (IStringMessage)x;
                        IStringMessage m2 = (IStringMessage)y;
                        
                        return m1.getPayload().compareTo( m2.getPayload() );
                    }
                });
    }

    /************************************************************************
     * Resequences messages for platforms like SQS that do not guarantee FIFO 
     *
     * @param messages
     * @return
     */
    private List<IMessage> 
    resequence(List<IMessage> messages,Comparator<IMessage> comparator)
    {
        Collections.sort( messages,comparator );        
        return messages;
    }
    
    /************************************************************************
     *  
     *
     * @param string
     * @throws MixedModeException 
     */
    private void 
    cleanUpQueue(String queue) 
        throws MixedModeException
    {
        IMessageReceiver cleaner = 
            itsSession.createMessageReceiver( queue );
        
        try
        {
            IMessage message = cleaner.receive(2000);
            
            while ( message != null )
            {
                System.out.println( "Removing message: " + message.getCorrelationId() );
            
                message = cleaner.receive(2000);
            }
        }
        catch (NoMessageReceivedException e) 
        {
            System.out.println( "Queue:" + queue + " cleaned up." );
        }
        finally
        {
            cleaner.close();
        }
    }
}

// ##########################################################################
