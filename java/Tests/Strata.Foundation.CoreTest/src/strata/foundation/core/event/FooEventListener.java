//////////////////////////////////////////////////////////////////////////////
// FooEventListener.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public
class FooEventListener
    implements IFooEventListener
{
    private final IFooEventReceiver itsReceiver;
    private FooEvent                itsExpected;
    private FooEvent                itsActual;

    public
    FooEventListener(IFooEventReceiver receiver)
    {
        itsReceiver = receiver;
    }

    @Override
    public void
    onEvent(FooEvent actual)
    {
        System.out.println("onEvent");
        itsActual = actual;
        itsReceiver.stopListening();
    }

    @Override
    public void
    onException(Exception exception)
    {
        exception.printStackTrace();
    }

    public FooEventListener
    setExpected(FooEvent expected)
    {
        itsExpected = expected;
        return this;
    }

    public FooEvent
    getExpected()
    {
        return itsExpected;
    }

    public boolean
    receivedActual()
    {
        return itsActual != null;
    }

    public void
    checkAssertions()
    {
        assertNotNull("Expected is null",itsExpected);
        assertNotNull("Actual is null",itsActual);

        EventIdentifiersData expectedIds = itsExpected.getIdentifiers();
        FooData              expectedSource = itsExpected.getSource();
        EventIdentifiersData actualIds = itsActual.getIdentifiers();
        FooData              actualSource = itsActual.getSource();

        assertEquals("EventIds !=",expectedIds.getEventId(),actualIds.getEventId());
        assertEquals("CorrelationIds !=",expectedIds.getCorrelationId(),actualIds.getCorrelationId());
        assertEquals("Timestamps !=",expectedIds.getTimestamp(),actualIds.getTimestamp());
        assertEquals("Ids !=",expectedSource.getId(),actualSource.getId());
        assertEquals("Xs !=",expectedSource.getX(),actualSource.getX());
        assertEquals("Ys !=",expectedSource.getY(),actualSource.getY());
    }
}

//////////////////////////////////////////////////////////////////////////////
