//////////////////////////////////////////////////////////////////////////////
// IExecutorServicePool.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.concurrent.ExecutorService;

public
interface IExecutorServicePool
{
    IExecutorServicePool
    setPoolSize(int poolSize);

    int
    getPoolSize();

    ExecutorService
    getExecutorService();
}

//////////////////////////////////////////////////////////////////////////////