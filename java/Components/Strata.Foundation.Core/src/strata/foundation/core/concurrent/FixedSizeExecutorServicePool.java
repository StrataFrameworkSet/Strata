//////////////////////////////////////////////////////////////////////////////
// FixedSizeExecutorServicePool.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import strata.foundation.core.utility.OptionalExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public
class FixedSizeExecutorServicePool
    implements IExecutorServicePool
{
    private IExecutorService[] itsPool;
    private int                itsCurrentIndex;

    public
    FixedSizeExecutorServicePool(int poolSize)
    {
        itsPool = new PooledExecutorService[poolSize];

        for (int i=0;i<poolSize;i++)
            itsPool[i] = new PooledExecutorService(this);
    }

    @Override
    public synchronized IExecutorServicePool
    setPoolSize(int poolSize)
    {
        if (poolSize != getPoolSize())
        {
            if (poolSize < getPoolSize())
                itsPool = Arrays.copyOf(itsPool,poolSize);
            else
            {
                IExecutorService[] itsTemp = itsPool;

                itsPool = new PooledExecutorService[poolSize];

                for (int i=0;i<itsTemp.length;i++)
                    itsPool[i] = itsTemp[i];

                for (int i=itsTemp.length;i<poolSize;i++)
                    itsPool[i] = new PooledExecutorService(this);
            }
        }

        return this;
    }

    @Override
    public synchronized int
    getPoolSize()
    {
        return itsPool.length;
    }

    @Override
    public synchronized ExecutorService
    getExecutorService()
    {
        return findAvailable();
    }

    private IExecutorService
    findAvailable()
    {
        Optional<IExecutorService> target = Optional.empty();

        while (!target.isPresent())
        {
            target =
                Arrays
                    .stream(itsPool)
                    .filter(service -> service.isCheckedIn())
                    .findFirst();

            OptionalExtension
                .ifNotPresent(target,() -> sleep(10));
        }

        return target.get();
    }

    private void
    sleep(int millis)
    {
        try
        {
            Thread.sleep(10);
        }
        catch (InterruptedException e) {}
    }
}

//////////////////////////////////////////////////////////////////////////////
