// ##########################################################################
// # File Name:	StompJmsMessageReceiverTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataJmsIntegratorTest Framework.
// #
// #   			The StrataJmsIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataJmsIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataJmsIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.jmsintegrator.jmsmessaging;

import static org.junit.Assert.*;
import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.IObjectMessage;
import strata1.integrator.messaging.IStringMessage;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class StompJmsMessageReceiverTest
{
    private IMessagingSession itsSessionA;
    private IMessagingSession itsSessionB;
    
    private IMessageSender    itsSender;
    private IMessageReceiver  itsReceiver;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsSessionA = createMessagingSession();
        itsSessionB = createMessagingSession();
        
        itsSessionA.startReceiving();
        itsSessionB.startReceiving();
        
        itsSender = itsSessionA.createMessageSender( "foo.test" );
        itsReceiver = itsSessionB.createMessageReceiver( "foo.test" );
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
        cleanUpQueue( "foo.test" );
        
        itsReceiver.close();
        
        itsSessionA.stopReceiving();
        itsSessionB.stopReceiving();

        itsSessionA.close();
        itsSessionB.close();

        itsSessionA = null;
        itsSessionB = null;
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
        IStringMessage expected1 = itsSessionA.createStringMessage();
        IStringMessage expected2 = itsSessionA.createStringMessage();
        IStringMessage expected3 = itsSessionA.createStringMessage();
        IMessage       actual1   = null;
        IMessage       actual2   = null;
        IMessage       actual3   = null;
        
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
        
        sleepIfNeeded( 3000 );
        
        actual1 = itsReceiver.receive();
        actual2 = itsReceiver.receive();
        actual3 = itsReceiver.receive();
        
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
     * @throws InterruptedException 
     */
    @Test
    public void 
    testReceiveLong1() 
        throws MixedModeException,NoMessageReceivedException,InterruptedException
    {
        IStringMessage expected1 = itsSessionA.createStringMessage();
        IStringMessage expected2 = itsSessionA.createStringMessage();
        IStringMessage expected3 = itsSessionA.createStringMessage();
        IMessage       actual1   = null;
        IMessage       actual2   = null;
        IMessage       actual3   = null;
        
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
          
        sleepIfNeeded( 5000 );
        
        actual1 = itsReceiver.receive( 10000 );
        actual2 = itsReceiver.receive( 10000);
        actual3 = itsReceiver.receive( 10000 );
        
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
     * @throws InterruptedException 
     */
    @Test
    public void 
    testReceiveLong2() 
        throws MixedModeException,NoMessageReceivedException,InterruptedException
    {
        IStringMessage expected1 = itsSessionA.createStringMessage();
        IMapMessage    expected2 = itsSessionA.createMapMessage();
        IStringMessage expected3 = itsSessionA.createStringMessage();
        IMessage       actual1   = null;
        IMessage       actual2   = null;
        IMessage       actual3   = null;
        
        itsReceiver.close();
        itsReceiver = 
            itsSessionB.createMessageReceiver( 
                "foo.test",
                "FooProperty  >=  5" );
        
        expected1
            .setCorrelationId( "testReceiveLong2" )
            .setIntProperty( "FooProperty",3 )
            .setPayload( "TestMessage1" );
        expected2
            .setCorrelationId( "testReceiveLong2" )
            .setIntProperty( "FooProperty",5 )
            .setString( "TestMessage","TestMessage2" );
        expected3
            .setCorrelationId( "testReceiveLong2" )
            .setIntProperty( "FooProperty",7 )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );

        actual2 = itsReceiver.receive( 10000 );    
        actual3 = itsReceiver.receive( 10000 );
              
        assertTrue( actual2 instanceof IMapMessage );
        assertTrue( actual3 instanceof IStringMessage );
               
        assertEquals( expected2.getString("TestMessage"),((IMapMessage)actual2).getString("TestMessage") );
        assertEquals( expected3.getPayload(),((IStringMessage)actual3).getPayload() );
        
        
        try
        {
            actual1 = itsReceiver.receive( 10 );
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
        IStringMessage   expected1 = itsSessionA.createStringMessage();
        IMapMessage      expected2 = itsSessionA.createMapMessage();
        IObjectMessage   expected3 = itsSessionA.createObjectMessage();
        IMessage         actual1   = null;
        IMessage         actual2   = null;
        IMessage         actual3   = null;
        
        itsReceiver.close();
        receiver1 = 
            itsSessionB.createMessageReceiver( 
                "foo.test",
                "FooProperty  >=  5" );
        receiver2 = 
            itsSessionB.createMessageReceiver( 
                "foo.test",
                "FooProperty  <  5" );
        
        expected1
            .setCorrelationId( "testReceiveLong3" )
            .setIntProperty( "FooProperty",3 )
            .setPayload( "TestMessage1" );
        expected2
            .setCorrelationId( "testReceiveLong3" )
            .setIntProperty( "FooProperty",5 )
            .setString( "TestMessage","TestMessage2" );
        expected3
            .setCorrelationId( "testReceiveLong3" )
            .setIntProperty( "FooProperty",7 )
            .setPayload( "TestMessage3" );
        
        itsSender.send( expected1 );
        itsSender.send( expected2 );
        itsSender.send( expected3 );

        actual2 = receiver1.receive( 10000 );    
        actual3 = receiver1.receive( 10000 );
              
        assertTrue( actual2 instanceof IMapMessage );
        assertTrue( actual3 instanceof IObjectMessage );
               
        assertEquals( expected2.getString("TestMessage"),((IMapMessage)actual2).getString("TestMessage") );
        assertEquals( expected3.getPayload(),((IObjectMessage)actual3).getPayload() );
         
        actual1 = receiver2.receive( 10 );
        
        assertTrue( actual1 instanceof IStringMessage );
        assertEquals( expected1.getPayload(),((IStringMessage)actual1).getPayload() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    protected IMessagingSession 
    createMessagingSession()
    {
        String host =
            "ssl://ec2-54-68-199-128.us-west-2.compute.amazonaws.com:61618";
            //"ssl://localhost:61618";
        
        StompJmsConnectionFactory factory = null;
        TrustManager[]            manager = null;
        
        factory = new StompJmsConnectionFactory();
        factory.setBrokerURI( host );

        manager = 
            new TrustManager[] 
            { 
                new X509TrustManager()
                {
                    public X509Certificate[] 
                    getAcceptedIssuers()
                    {
                        return null;
                    }
        
                    public void 
                    checkClientTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
        
                    public void 
                    checkServerTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
                } 
            };
                
        try
        {
            SSLContext context;

            context = SSLContext.getInstance("SSL");
            context.init( null,manager,new SecureRandom() );
            factory.setSslContext( context );
        }
        catch(Exception e)
        {
            throw new IllegalStateException( e );
        }        
        
        factory.setUsername( "strata-activemq-user" );
        factory.setPassword( "Dbr6pzyX" );
        
        return new JmsQueueMessagingSession(factory);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    protected IMessagingSession 
    createMessagingSessionB()
    {
        String host =
            //"ssl://ec2-54-68-199-128.us-west-2.compute.amazonaws.com:61617";
            "ssl://localhost:61617";
        
        ActiveMQSslConnectionFactory factory = null;
        TrustManager[]               manager = null;
        
        factory = new ActiveMQSslConnectionFactory( host );

        manager = 
            new TrustManager[] 
            { 
                new X509TrustManager()
                {
                    public X509Certificate[] 
                    getAcceptedIssuers()
                    {
                        return null;
                    }
        
                    public void 
                    checkClientTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
        
                    public void 
                    checkServerTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
                } 
            };
        
        
        factory.setKeyAndTrustManagers(
            null, 
            manager, 
            new SecureRandom());
        
        factory.setUserName( "strata-activemq-user" );
        factory.setPassword( "Dbr6pzyX" );
        factory.setTransformer( new StompMessageTransformer() );
        return new JmsQueueMessagingSession(factory);
    }
    /************************************************************************
     * {@inheritDoc} 
     */
    protected IMessagingSession 
    createMessagingSessionC()
    {
        String host =
            "tcp://ec2-54-68-199-128.us-west-2.compute.amazonaws.com:61616";
        
        ActiveMQSslConnectionFactory factory = null;
        TrustManager[]               manager = null;
        
        factory = new ActiveMQSslConnectionFactory( host );

        manager = 
            new TrustManager[] 
            { 
                new X509TrustManager()
                {
                    public X509Certificate[] 
                    getAcceptedIssuers()
                    {
                        return null;
                    }
        
                    public void 
                    checkClientTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
        
                    public void 
                    checkServerTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
                } 
            };
        
        
        factory.setKeyAndTrustManagers(
            null, 
            manager, 
            new SecureRandom());
        
        factory.setUserName( "strata-activemq-user" );
        factory.setPassword( "Dbr6pzyX" );
        
        return new JmsQueueMessagingSession(factory);
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    protected void 
    sleepIfNeeded(long millis)
    {
        try
        {
            Thread.sleep( millis );
        }
        catch(InterruptedException e) {}
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
            itsSessionB.createMessageReceiver( queue );
        
        try
        {
            IMessage message = cleaner.receive( 2000 );
            
            while ( message != null )
            {
                System.out.println( "Removing message: " + message.getCorrelationId() );
            
                message = cleaner.receive( 2000 );
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
