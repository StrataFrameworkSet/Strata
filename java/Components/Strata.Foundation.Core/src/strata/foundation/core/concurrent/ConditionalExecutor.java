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
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public
class ConditionalExecutor<C>
    implements IConditionalExecutor<C>
{
    private final Map<C,AtomicBoolean> itsConditions;
    private Runnable                   itsAction;
    private boolean                    itsTriggerIndicator;
    private final ISynchronizer        itsSynchronizer;

    public ConditionalExecutor()
    {
        itsConditions = new HashMap<>();
        itsAction = null;
        itsTriggerIndicator = false;
        itsSynchronizer = new ReadWriteLockSynchronizer();
    }

    @Override
    public IConditionalExecutor
    insertCondition(C condition)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            itsConditions.put(condition,new AtomicBoolean(false));
            return this;
        }
    }

    @Override
    public IConditionalExecutor
    removeCondition(C condition)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            itsConditions.remove(condition);
            return this;
        }
    }

    @Override
    public IConditionalExecutor
    setCondition(C condition)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            if (itsConditions.containsKey(condition))
            {
                itsConditions
                    .get(condition)
                    .set(true);
                if (mustExecute())
                    itsAction.run();
            }

            return this;
        }
    }

    @Override
    public IConditionalExecutor
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
    public IConditionalExecutor
    setAction(Runnable action)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            itsAction = action;
            return this;
        }
    }

    @Override
    public Set<C>
    getConditions()
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return itsConditions.keySet();
        }
    }

    @Override
    public boolean
    isConditionTrue(C condition)
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return
                hasCondition(condition)
                    ? itsConditions.get(condition).get()
                    : false;
        }
    }

    @Override
    public boolean
    hasCondition(C condition)
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return itsConditions.containsKey(condition);
        }
    }

    @Override
    public boolean
    hasExecuted()
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return mustExecute();
        }
    }

    private boolean
    mustExecute()
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
