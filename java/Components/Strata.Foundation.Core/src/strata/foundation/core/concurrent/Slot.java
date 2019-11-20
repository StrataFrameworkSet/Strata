//////////////////////////////////////////////////////////////////////////////
// Slot.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.UUID;

public
class Slot
    implements Comparable<Slot>
{
    private final UUID itsValue;

    public
    Slot()
    {
        itsValue = UUID.randomUUID();
    }

    @Override
    public int
    compareTo(Slot other)
    {
        return itsValue.compareTo(other.itsValue);
    }

    @Override
    public boolean
    equals(Object other)
    {
        return
            other instanceof Slot &&
                itsValue.equals(((Slot)other).itsValue);
    }

    @Override
    public int
    hashCode()
    {
        return itsValue.hashCode();
    }
}

//////////////////////////////////////////////////////////////////////////////
