//////////////////////////////////////////////////////////////////////////////
// AbstractBootstrapModule.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.inject;

import com.google.inject.Scopes;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.core.unitofwork.IUnitOfWorkProviderPool;
import strata.domain.core.unitofwork.ProxyUnitOfWorkProvider;
import strata.domain.core.unitofwork.UnitOfWorkProviderPool;
import strata.foundation.core.inject.AbstractModule;

import java.util.function.Supplier;

public
class AbstractBootstrapModule
    extends AbstractModule
{
    private final Supplier<IUnitOfWorkProvider> itsSupplier;
    private final int                           itsPoolSize;

    protected
    AbstractBootstrapModule(Supplier<IUnitOfWorkProvider> supplier)
    {
        this(supplier,16);
    }

    protected
    AbstractBootstrapModule(
        Supplier<IUnitOfWorkProvider> supplier,
        int                           poolSize)
    {
        itsSupplier = supplier;
        itsPoolSize = poolSize;
    }

    @Override
    protected void
    configure()
    {
        bind(IUnitOfWorkProviderPool.class)
            .toProvider(
                () ->
                    new UnitOfWorkProviderPool(
                        itsPoolSize,
                        itsSupplier))
            .in(Scopes.SINGLETON);

        bind(IUnitOfWorkProvider.class)
            .to(ProxyUnitOfWorkProvider.class)
            .in(getDefaultScope());
    }
}

//////////////////////////////////////////////////////////////////////////////
