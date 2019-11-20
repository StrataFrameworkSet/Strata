//////////////////////////////////////////////////////////////////////////////
// PooledExecutorService.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public
class PooledExecutorService
    implements IExecutorService
{
    private final ExecutorService itsImplementation;
    private IExecutorServicePool  itsPool;
    private boolean               itsAvailability;

    public
    PooledExecutorService(IExecutorServicePool pool)
    {
        itsImplementation = Executors.newSingleThreadExecutor();
        itsPool = pool;
        itsAvailability = true;
    }

    @Override
    public void
    shutdown()
    {
        try
        {
            itsImplementation.shutdown();
        }
        finally
        {
            checkIn();
        }
    }

    @Override
    public List<Runnable>
    shutdownNow()
    {
        try
        {
            return itsImplementation.shutdownNow();
        }
        finally
        {
            checkIn();
        }
    }

    @Override
    public boolean
    isShutdown()
    {
        return itsImplementation.isShutdown();
    }

    @Override
    public boolean
    isTerminated()
    {
        return itsImplementation.isTerminated();
    }

    @Override
    public boolean
    awaitTermination(long timeout,TimeUnit unit) throws InterruptedException
    {
        return itsImplementation.awaitTermination(timeout,unit);
    }

    @Override
    public <T> Future<T>
    submit(Callable<T> task)
    {
        return itsImplementation.submit(task);
    }

    @Override
    public <T> Future<T>
    submit(Runnable task,T result)
    {
        return itsImplementation.submit(task,result);
    }

    @Override
    public Future<?>
    submit(Runnable task)
    {
        return itsImplementation.submit(task);
    }

    @Override
    public <T> List<Future<T>>
    invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException
    {
        return itsImplementation.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>>
    invokeAll(Collection<? extends Callable<T>> tasks,long timeout,TimeUnit unit) throws InterruptedException
    {
        return itsImplementation.invokeAll(tasks,timeout,unit);
    }

    @Override
    public <T> T
    invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException
    {
        return itsImplementation.invokeAny(tasks);
    }

    @Override
    public <T> T
    invokeAny(Collection<? extends Callable<T>> tasks,long timeout,TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
    {
        return itsImplementation.invokeAny(tasks,timeout,unit);
    }

    @Override
    public void
    execute(Runnable command)
    {
        itsImplementation.execute(command);
    }

    @Override
    public IExecutorService
    checkOut()
    {
        if (isCheckedIn())
            itsAvailability = false;

        return this;
    }

    @Override
    public IExecutorService
    checkIn()
    {
        itsAvailability = true;
        return this;
    }

    @Override
    public boolean
    isCheckedOut()
    {
        return itsAvailability == false;
    }

    @Override
    public boolean
    isCheckedIn()
    {
        return itsAvailability == true;
    }
}

//////////////////////////////////////////////////////////////////////////////
