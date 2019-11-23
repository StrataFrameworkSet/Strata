//////////////////////////////////////////////////////////////////////////////
// ITrigger.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

public
interface ITrigger<C>
{
    ITrigger
    insertCondition(C condition);

    ITrigger
    removeCondition(C condition);

    ITrigger
    setAction(Runnable action);

    ITrigger
    setCondition(C condition);

    ITrigger
    clearCondition(C condition);

    boolean
    hasCondition(C condition);

    boolean
    hasTriggered();

}

//////////////////////////////////////////////////////////////////////////////