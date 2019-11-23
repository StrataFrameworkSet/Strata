//////////////////////////////////////////////////////////////////////////////
// ProxyExecutorService.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public
class ProxyExecutorService
    implements IExecutorService
{
    private IExecutorServicePool   itsPool;
    private final IExecutorService itsDelegate;

    public
    ProxyExecutorService(IExecutorServicePool pool)
    {
        itsPool = pool;
        itsDelegate = pool.checkOut();
    }

    @Override
    public void
    close()
    {
        itsPool.checkIn(itsDelegate);
    }

    @Override
    public void
    shutdown()
    {
        itsDelegate.shutdown();
    }

    @Override
    public List<Runnable>
    shutdownNow()
    {
        return itsDelegate.shutdownNow();
    }

    @Override
    public boolean
    isShutdown()
    {
        return itsDelegate.isShutdown();
    }

    @Override
    public boolean
    isTerminated()
    {
        return itsDelegate.isTerminated();
    }

    @Override
    public boolean
    awaitTermination(long timeout,TimeUnit unit) throws InterruptedException
    {
        return itsDelegate.awaitTermination(timeout,unit);
    }

    @Override
    public <T> Future<T>
    submit(Callable<T> task)
    {
        return itsDelegate.submit(task);
    }

    @Override
    public <T> Future<T>
    submit(Runnable task,T result)
    {
        return itsDelegate.submit(task,result);
    }

    @Override
    public Future<?>
    submit(Runnable task)
    {
        return itsDelegate.submit(task);
    }

    @Override
    public <T> List<Future<T>>
    invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException
    {
        return itsDelegate.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>>
    invokeAll(Collection<? extends Callable<T>> tasks,long timeout,TimeUnit unit)
        throws InterruptedException
    {
        return itsDelegate.invokeAll(tasks,timeout,unit);
    }

    @Override
    public <T> T
    invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException
    {
        return itsDelegate.invokeAny(tasks);
    }

    @Override
    public <T> T
    invokeAny(Collection<? extends Callable<T>> tasks,long timeout,TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException
    {
        return itsDelegate.invokeAny(tasks,timeout,unit);
    }

    @Override
    public void
    execute(Runnable command)
    {
        itsDelegate.execute(command);
    }

    @Override
    public IExecutorService
    setPool(IExecutorServicePool pool)
    {
        itsDelegate.setPool(pool);
        return this;
    }

    @Override
    public IExecutorServicePool
    getPool()
    {
        return itsDelegate.getPool();
    }
}

//////////////////////////////////////////////////////////////////////////////
