//////////////////////////////////////////////////////////////////////////////
// FixedSizeExecutorServicePool.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import strata.foundation.core.pool.FixedSizePool;

import java.util.function.Supplier;

public
class FixedSizeExecutorServicePool
    extends FixedSizePool<IExecutorService,IExecutorServicePool>
    implements IExecutorServicePool
{

    public
    FixedSizeExecutorServicePool(
        int                        poolSize,
        Supplier<IExecutorService> supplier)
    {
        super(poolSize,supplier);
    }
}

//////////////////////////////////////////////////////////////////////////////
