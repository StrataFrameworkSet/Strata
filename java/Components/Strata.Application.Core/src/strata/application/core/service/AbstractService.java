//////////////////////////////////////////////////////////////////////////////
// AbstractService.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.service;

import strata.application.core.action.IActionQueue;
import strata.application.core.interception.IUnitOfWorkPropertySupplier;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import java.util.Properties;

import static strata.foundation.core.utility.Awaiter.await;

public abstract
class AbstractService
    implements IUnitOfWorkPropertySupplier
{
    private final IUnitOfWorkProvider provider;
    private final IActionQueue        queue;
    private final Properties configuration;

    protected
    AbstractService(IUnitOfWorkProvider p)
    {
        this(p,null,null);
    }

    protected
    AbstractService(IUnitOfWorkProvider p,IActionQueue q)
    {
        this(p,q,null);
    }

    protected
    AbstractService(
        IUnitOfWorkProvider p,
        IActionQueue        q,
        Properties c)
    {
        provider      = p;
        queue         = q;
        configuration = c;
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

    @Override
    public Properties
    getConfiguration()
    {
        return configuration;
    }

    protected void
    pushRollbackAction(Runnable action)
    {
        await(provider.getUnitOfWork()).pushRollbackAction(action);
    }
}

//////////////////////////////////////////////////////////////////////////////
