//////////////////////////////////////////////////////////////////////////////
// Trigger.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import strata.foundation.core.utility.ISynchronizer;
import strata.foundation.core.utility.ReadLock;
import strata.foundation.core.utility.ReadWriteLockSynchronizer;
import strata.foundation.core.utility.WriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public
class Trigger<C>
    implements ITrigger<C>
{
    private final Map<C,AtomicBoolean> itsConditions;
    private Runnable             itsAction;
    private boolean              itsTriggerIndicator;
    private final ISynchronizer  itsSynchronizer;

    public
    Trigger()
    {
        itsConditions = new HashMap<>();
        itsAction = null;
        itsTriggerIndicator = false;
        itsSynchronizer = new ReadWriteLockSynchronizer();
    }

    @Override
    public ITrigger
    insertCondition(C condition)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            itsConditions.put(condition,new AtomicBoolean(false));
            return this;
        }
    }

    @Override
    public ITrigger
    removeCondition(C condition)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            itsConditions.remove(condition);
            return this;
        }
    }

    @Override
    public ITrigger
    setAction(Runnable action)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            itsAction = action;
            return this;
        }
    }

    @Override
    public ITrigger
    setCondition(C condition)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            if (itsConditions.containsKey(condition))
            {
                itsConditions
                    .get(condition)
                    .set(true);
                if (mustTrigger())
                    itsAction.run();
            }

            return this;
        }
    }

    @Override
    public ITrigger
    clearCondition(C condition)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            if (itsConditions.containsKey(condition))
                itsConditions
                    .get(condition)
                    .set(false);

            return this;
        }
    }

    @Override
    public boolean
    hasCondition(C condition)
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return
                itsConditions
                    .get(condition)
                    .get();
        }
    }

    @Override
    public boolean
    hasTriggered()
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return mustTrigger();
        }
    }

    private boolean
    mustTrigger()
    {
        return
            itsConditions
                .values()
                .stream()
                .map(condition -> condition.get())
                .allMatch(condition -> condition == true) &&
            itsAction != null;
    }
}

//////////////////////////////////////////////////////////////////////////////
