//////////////////////////////////////////////////////////////////////////////
// AbstractUnitOfWorkProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import strata.foundation.core.pool.ICheckOutCheckIn;
import strata.foundation.core.utility.ISynchronizer;
import strata.foundation.core.utility.ReadWriteLockSynchronizer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract
class AbstractUnitOfWorkProvider
    implements IUnitOfWorkProvider, ICheckOutCheckIn
{
    private IUnitOfWorkProviderPool itsPool;
    private ExecutorService         itsExecutor;
    private boolean                 itsAvailability;
    private final ISynchronizer     itsSynchronizer;
    private Logger                  itsLogger;

    protected
    AbstractUnitOfWorkProvider()
    {
        itsExecutor = Executors.newSingleThreadExecutor();
        itsAvailability = true;
        itsSynchronizer = new ReadWriteLockSynchronizer();
        itsLogger = LogManager.getLogger(AbstractUnitOfWorkProvider.class);
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

    public void
    checkOut()
    {
        if (isCheckedIn())
        {
            itsLogger.debug("checkOut");
            itsAvailability = false;
        }
    }

    public void
    checkIn()
    {
        if (isCheckedOut())
        {
            itsLogger.debug("checkIn");
            itsAvailability = true;
        }
    }

    protected ISynchronizer
    getSynchronizer() { return itsSynchronizer; }

    @Override
    public void
    onComplete(IUnitOfWork subject) {}
}

//////////////////////////////////////////////////////////////////////////////
