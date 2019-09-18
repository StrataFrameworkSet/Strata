//////////////////////////////////////////////////////////////////////////////
// EventSenderTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.foundation.core.action.IActionQueue;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract
class EventSenderTest
{
    private Module            itsModule;
    private IFooEventSender   itsTarget;
    private IFooEventReceiver itsReceiver;
    private IActionQueue      itsActionQueue;

    @Before
    public void
    setUp()
    {
        Injector injector = Guice.createInjector(getModule());

        itsTarget = injector.getInstance(IFooEventSender.class);
        itsReceiver = injector.getInstance(IFooEventReceiver.class);
        itsActionQueue = injector.getInstance(IActionQueue.class);
    }

    @After
    public void
    tearDown()
    {
        itsReceiver.stopListening();
    }

    @Test
    public void
    testSend()
        throws Exception
    {
        FooEvent expected =
            FooEvent.newBuilder()
                .setIdentifiers(
                    EventIdentifiersData.newBuilder()
                        .setEventId(UUID.randomUUID().toString())
                        .setCorrelationId("1111111111111")
                        .setTimestamp(Instant.now())
                        .build())
                .setEventType(StandardEventType.CREATED)
                .setSource(
                    FooData.newBuilder()
                        .setId("ABCDEFGHIJK")
                        .setX("!@#$%^&*")
                        .setY(23).build())
                .build();
        FooEventListener listener =
            new FooEventListener(itsReceiver)
                .setExpected(expected);

        itsReceiver.startListening(listener);
        assertTrue(itsReceiver.isListening());

        itsTarget.send(expected);
        itsActionQueue.execute();
        sleep(5);
        assertFalse("Should have stopped listening at this point",itsReceiver.isListening());
        listener.checkAssertions();
    }

    protected abstract Module
    getModule();

    protected void
    sleep(int seconds)
    {
        try
        {
            Thread.sleep(seconds*1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

//////////////////////////////////////////////////////////////////////////////
