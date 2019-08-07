package strata1.integrator.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract
class MessageSenderTest
{
    private IMessagingSession itsSession;
    private IMessageSender    itsTarget;
    private IMessageReceiver  itsReceiver;
    private static final long SECOND = 1000;
    private static final long MINUTE = 60*SECOND;
    private static final long HOUR   = 60*MINUTE;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsSession = createMessagingSession();
        itsTarget  = itsSession.createMessageSender( "foo-test" );
        itsReceiver = itsSession.createMessageReceiver( "foo-test" );
        itsSession.startReceiving();
    }

    @After
    public void 
    tearDown() throws Exception
    {      
        itsReceiver.close();
        itsSession.stopReceiving();
        itsReceiver = null;
        itsTarget = null;
        itsSession.close();
        itsSession = null;
    }

    @Test
    public void 
    testSetTimeToLive()
    {
        itsTarget.setTimeToLive( MINUTE );
        assertEquals( MINUTE,itsTarget.getTimeToLive() );
        itsTarget.setTimeToLive( 24*HOUR );
        assertEquals( 24*HOUR,itsTarget.getTimeToLive() );
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
        itsTarget.setTimeToLive( MINUTE );
        assertEquals( MINUTE,itsTarget.getTimeToLive() );
        itsTarget.setTimeToLive( 24*HOUR );
        assertEquals( 24*HOUR,itsTarget.getTimeToLive() );
    }

    @Test
    public void 
    testSend() 
        throws MixedModeException,NoMessageReceivedException
    {
        IStringMessage expected1 = itsSession.createStringMessage();
        IStringMessage expected2 = itsSession.createStringMessage();
        IStringMessage expected3 = itsSession.createStringMessage();
        List<IMessage> actuals   = new ArrayList<IMessage>();
        
        expected1.setPayload( "TestMessage1" );
        expected2.setPayload( "TestMessage2" );
        expected3.setPayload( "TestMessage3" );
        
        itsTarget.send( expected1 );
        itsTarget.send( expected2 );
        itsTarget.send( expected3 );
        
        actuals.add( itsReceiver.receive(5000) );
        actuals.add( itsReceiver.receive(5000) );
        actuals.add( itsReceiver.receive(5000) );
        
        actuals = resequence( actuals );
        
        assertEquals( 3,actuals.size() );
        
        
        assertTrue( actuals.get(0) instanceof IStringMessage );
        assertTrue( actuals.get(1) instanceof IStringMessage );
        assertTrue( actuals.get(2) instanceof IStringMessage );
         
        
        assertEquals( expected1.getPayload(),((IStringMessage)actuals.get(0)).getPayload() );
        assertEquals( expected2.getPayload(),((IStringMessage)actuals.get(1)).getPayload() );
        assertEquals( expected3.getPayload(),((IStringMessage)actuals.get(2)).getPayload() );
    }

    protected abstract IMessagingSession 
    createMessagingSession();

    private List<IMessage> 
    resequence(List<IMessage> messages)
    {
        // Resequenced messages for platforms like SQS that do not guarantee FIFO
        Collections.sort( 
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
            } );
        
        return messages;
    }
}

// ##########################################################################
