package strata1.integrator.messaging;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract
class MessageSenderTest
{
    private IMessagingSession itsSession;
    private IMessageSender    itsTarget;
    private IMessageReceiver  itsReceiver;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsSession = createMessagingSession();
        itsTarget  = itsSession.createMessageSender( "foo.test" );
        itsReceiver = itsSession.createMessageReceiver( "foo.test" );
    }

    @After
    public void 
    tearDown() throws Exception
    {      
        itsReceiver = null;
        itsTarget = null;
        itsSession.close();
        itsSession = null;
    }

    @Test
    public void 
    testSetTimeToLive()
    {
        itsTarget.setTimeToLive( 5000 );
        assertEquals( 5000,itsTarget.getTimeToLive() );
        itsTarget.setTimeToLive( 15000 );
        assertEquals( 15000,itsTarget.getTimeToLive() );
    }

    @Test
    public void 
    testGetSession()
    {
        assertEquals( itsSession,itsTarget.getSession() );
    }

    @Test
    public void 
    testGetTimeToLive()
    {
        itsTarget.setTimeToLive( 5000 );
        assertEquals( 5000,itsTarget.getTimeToLive() );
        itsTarget.setTimeToLive( 15000 );
        assertEquals( 15000,itsTarget.getTimeToLive() );
    }

    @Test
    public void 
    testSend() 
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
        
        itsTarget.send( expected1 );
        itsTarget.send( expected2 );
        itsTarget.send( expected3 );
        
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

    protected abstract IMessagingSession 
    createMessagingSession();

}

// ##########################################################################
