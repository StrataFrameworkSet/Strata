//////////////////////////////////////////////////////////////////////////////
// IConditionalExecutor.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.Set;

public
interface IConditionalExecutor<C>
{
    IConditionalExecutor
    insertCondition(C condition);

    IConditionalExecutor
    removeCondition(C condition);

    IConditionalExecutor
    setCondition(C condition);

    IConditionalExecutor
    clearCondition(C condition);

    IConditionalExecutor
    setAction(Runnable action);

    Set<C>
    getConditions();

    boolean
    isConditionTrue(C condition);

    boolean
    hasCondition(C condition);

    boolean
    hasExecuted();
}

//////////////////////////////////////////////////////////////////////////////