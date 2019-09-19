//////////////////////////////////////////////////////////////////////////////
// FooEventListener.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.event;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public
class FooEventListener
    implements IFooEventListener
{
    private final IFooEventReceiver itsReceiver;
    private Queue<FooEvent>         itsExpected;
    private Queue<FooEvent>         itsActual;

    public
    FooEventListener(IFooEventReceiver receiver)
    {
        itsReceiver = receiver;
        itsExpected = new ConcurrentLinkedQueue<>();
        itsActual   = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void
    onEvent(FooEvent actual)
    {
        System.out.println("onEvent");
        itsActual.add(actual);
        itsReceiver.stopListening();
    }

    @Override
    public void
    onException(Exception exception)
    {
        exception.printStackTrace();
    }

    public FooEventListener
    insertExpected(FooEvent expected)
    {
        itsExpected.add(expected);
        return this;
    }

    public Queue<FooEvent>
    getExpected()
    {
        return itsExpected;
    }

    public boolean
    receivedActual()
    {
        return !itsActual.isEmpty();
    }

    public void
    checkAssertions()
    {
        assertFalse("Expected is empty",itsExpected.isEmpty());
        assertFalse("Actual is empty",itsActual.isEmpty());
        assertEquals("Expected.size != Actual.size",itsExpected.size(),itsActual.size());

        while (!itsExpected.isEmpty())
        {
            FooEvent expected = itsExpected.remove();
            FooEvent actual = itsActual.remove();

            EventIdentifiersData expectedIds = expected.getIdentifiers();
            FooData              expectedSource = expected.getSource();
            EventIdentifiersData actualIds = actual.getIdentifiers();
            FooData              actualSource = actual.getSource();

            assertEquals("EventIds !=",expectedIds.getEventId(),actualIds.getEventId());
            assertEquals("CorrelationIds !=",expectedIds.getCorrelationId(),actualIds.getCorrelationId());
            assertEquals("Timestamps !=",expectedIds.getTimestamp(),actualIds.getTimestamp());
            assertEquals("Ids !=",expectedSource.getId(),actualSource.getId());
            assertEquals("Xs !=",expectedSource.getX(),actualSource.getX());
            assertEquals("Ys !=",expectedSource.getY(),actualSource.getY());
        }
    }
}

//////////////////////////////////////////////////////////////////////////////
