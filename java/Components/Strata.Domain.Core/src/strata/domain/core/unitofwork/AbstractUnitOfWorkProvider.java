//////////////////////////////////////////////////////////////////////////////
// AbstractUnitOfWorkProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import strata.foundation.core.utility.ISynchronizer;
import strata.foundation.core.utility.ReadWriteLockSynchronizer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract
class AbstractUnitOfWorkProvider
    implements IUnitOfWorkProvider
{
    private IUnitOfWorkProviderPool itsPool;
    private ExecutorService         itsExecutor;
    private final ISynchronizer     itsSynchronizer;
    private Logger                  itsLogger;

    protected
    AbstractUnitOfWorkProvider()
    {
        itsExecutor = Executors.newSingleThreadExecutor();
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

    protected ISynchronizer
    getSynchronizer() { return itsSynchronizer; }

    @Override
    public void
    onComplete(IUnitOfWork subject) {}
}

//////////////////////////////////////////////////////////////////////////////
