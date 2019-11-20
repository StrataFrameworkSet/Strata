//////////////////////////////////////////////////////////////////////////////
// AbstractUnitOfWorkProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

import strata.foundation.core.pool.ICheckOutCheckIn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static strata.foundation.core.utility.Awaiter.await;

public abstract
class AbstractUnitOfWorkProvider
    implements IUnitOfWorkProvider, ICheckOutCheckIn
{
    private IUnitOfWorkProviderPool itsPool;
    private ExecutorService         itsExecutor;
    private boolean                 itsAvailability;

    protected
    AbstractUnitOfWorkProvider()
    {
        itsExecutor = Executors.newSingleThreadExecutor();
        itsAvailability = true;
    }

    @Override
    public ExecutorService
    getExecutor()
    {
        return itsExecutor;
    }

    @Override
    public IUnitOfWorkProvider
    setPool(IUnitOfWorkProviderPool pool)
    {
        itsPool = pool;
        return this;
    }

    @Override
    public IUnitOfWorkProviderPool
    getPool()
    {
        return itsPool;
    }

    @Override
    public synchronized boolean
    isCheckedOut()
    {
        return itsAvailability == false;
    }

    @Override
    public synchronized boolean
    isCheckedIn()
    {
        return itsAvailability == true;
    }

    public synchronized void
    checkOut()
    {
        if (isCheckedIn())
            itsAvailability = false;
    }

    public synchronized void
    checkIn()
    {
        if (isCheckedOut())
        {
            await(clearUnitOfWork());
            itsAvailability = true;
        }
    }

}

//////////////////////////////////////////////////////////////////////////////
