//////////////////////////////////////////////////////////////////////////////
// ProxyUnitOfWorkProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

public
class ProxyUnitOfWorkProvider
    implements IUnitOfWorkProvider
{
    private IUnitOfWorkProviderPool   itsPool;
    private final IUnitOfWorkProvider itsDelegate;

    @Inject
    public
    ProxyUnitOfWorkProvider(IUnitOfWorkProviderPool pool)
    {
        itsPool     = pool;
        itsDelegate = itsPool.checkOut();
    }

    @Override
    public CompletionStage<IUnitOfWork>
    getUnitOfWork()
    {
        return itsDelegate.getUnitOfWork();
    }

    @Override
    public CompletionStage<Void>
    clearUnitOfWork()
    {
        return itsDelegate.clearUnitOfWork();
    }

    @Override
    public ExecutorService
    getExecutor()
    {
        return itsDelegate.getExecutor();
    }

    @Override
    public boolean
    hasActiveUnitOfWork()
    {
        return itsDelegate.hasActiveUnitOfWork();
    }

    @Override
    public boolean
    isClosed()
    {
        return itsDelegate.isClosed();
    }

    @Override
    public void
    close()
    {
        getPool().checkIn(itsDelegate);
    }

    @Override
    public ProxyUnitOfWorkProvider
    setPool(IUnitOfWorkProviderPool pool)
    {
        itsDelegate.setPool(pool);
        return this;
    }

    @Override
    public IUnitOfWorkProviderPool
    getPool()
    {
        return itsDelegate.getPool();
    }

    @Override
    public boolean
    isCheckedOut()
    {
        return itsDelegate.isCheckedOut();
    }

    @Override
    public boolean
    isCheckedIn()
    {
        return itsDelegate.isCheckedIn();
    }

    public IUnitOfWorkProvider
    getDelegate() { return itsDelegate; }
}

//////////////////////////////////////////////////////////////////////////////
