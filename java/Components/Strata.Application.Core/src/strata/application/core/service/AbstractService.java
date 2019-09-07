//////////////////////////////////////////////////////////////////////////////
// AbstractService.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.service;

import strata.application.core.interception.IUnitOfWorkPropertySupplier;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.foundation.core.action.IActionQueue;

import static strata.foundation.core.utility.Awaiter.await;

public abstract
class AbstractService
    implements IUnitOfWorkPropertySupplier
{
    private final IUnitOfWorkProvider provider;
    private final IActionQueue        queue;

    protected
    AbstractService(IUnitOfWorkProvider p)
    {
        this(p,null);
    }

    protected
    AbstractService(
        IUnitOfWorkProvider p,
        IActionQueue        q)
    {
        provider = p;
        queue    = q;
    }

    @Override
    public IUnitOfWorkProvider
    getProvider()
    {
        return provider;
    }

    @Override
    public IActionQueue
    getQueue()
    {
        return queue;
    }

    protected void
    pushRollbackAction(Runnable action)
    {
        await(provider.getUnitOfWork()).pushRollbackAction(action);
    }
}

//////////////////////////////////////////////////////////////////////////////
