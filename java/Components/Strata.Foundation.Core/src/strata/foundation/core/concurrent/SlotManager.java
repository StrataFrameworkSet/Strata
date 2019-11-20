//////////////////////////////////////////////////////////////////////////////
// SlotManager.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public
class SlotManager
    implements ISlotManager
{
    private final BlockingQueue<Slot> itsSlots;

    public SlotManager(int numberOfSlots)
    {
        itsSlots = new ArrayBlockingQueue<>(numberOfSlots);

        for (int i=0;i<numberOfSlots;i++)
            itsSlots.add(new Slot());
    }

    @Override
    public Slot
    checkOut()
    {
        try
        {
            return itsSlots.poll(90,TimeUnit.SECONDS);
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void
    checkIn(Slot slot)
    {
        try
        {
            itsSlots.put(slot);
        }
        catch (InterruptedException e) {}
    }

    @Override
    public int
    getAvailability()
    {
        return itsSlots.remainingCapacity();
    }

    @Override
    public boolean
    hasAvailability()
    {
        return getAvailability() > 0;
    }

}

//////////////////////////////////////////////////////////////////////////////
