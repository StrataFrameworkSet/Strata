//////////////////////////////////////////////////////////////////////////////
// ProxyUnitOfWorkProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

import strata.foundation.core.concurrent.ITrigger;
import strata.foundation.core.concurrent.Trigger;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

public
class ProxyUnitOfWorkProvider
    implements IUnitOfWorkProvider,Comparable<ProxyUnitOfWorkProvider>
{
    private final UUID                itsId;
    private IUnitOfWorkProviderPool   itsPool;
    private final IUnitOfWorkProvider itsDelegate;
    private final ITrigger<String>    itsTrigger;

    @Inject
    public
    ProxyUnitOfWorkProvider(IUnitOfWorkProviderPool pool)
    {
        itsId       = UUID.randomUUID();
        itsPool     = pool;
        itsDelegate = itsPool.checkOut();
        itsTrigger  = new Trigger<>();

        itsTrigger
            .insertCondition("Closed")
            .insertCondition("Completed")
            .setAction(() -> getPool().checkIn(itsDelegate));
    }

    @Override
    public CompletionStage<IUnitOfWork>
    getUnitOfWork()
    {
        return
            itsDelegate
                .getUnitOfWork()
                .thenApply(
                    uow ->
                    {
                        itsTrigger.clearCondition("Completed");
                        return uow.attach(this);
                    });
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
        itsTrigger.setCondition("Closed");
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

    @Override
    public void
    onComplete(IUnitOfWork subject)
    {
        itsTrigger.setCondition("Completed");

        if (itsTrigger.hasTriggered())
            subject.detach(this);
    }

    @Override
    public int
    compareTo(ProxyUnitOfWorkProvider other)
    {
        return itsId.compareTo(other.itsId);
    }
}

//////////////////////////////////////////////////////////////////////////////
