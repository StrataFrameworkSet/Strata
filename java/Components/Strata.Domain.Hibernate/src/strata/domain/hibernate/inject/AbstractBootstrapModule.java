//////////////////////////////////////////////////////////////////////////////
// AbstractBootstrapModule.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.hibernate.inject;

import com.google.inject.Scopes;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.core.unitofwork.IUnitOfWorkProviderPool;
import strata.domain.core.unitofwork.ProxyUnitOfWorkProvider;
import strata.domain.core.unitofwork.UnitOfWorkProviderPool;
import strata.domain.hibernate.unitofwork.HibernateUnitOfWorkProvider;
import strata.foundation.core.inject.AbstractModule;
import strata.foundation.core.inject.ApplicationPropertiesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public
class AbstractBootstrapModule
    extends AbstractModule
{
    private final List<String> itsMappingResources;
    private final int          itsPoolSize;

    protected
    AbstractBootstrapModule(String... mappingResources)
    {
        this(Arrays.asList(mappingResources),16);
    }

    protected
    AbstractBootstrapModule(List<String> mappingResources)
    {
        this(mappingResources,16);
    }

    protected
    AbstractBootstrapModule(List<String> mappingResources,int poolSize)
    {
        itsMappingResources = new ArrayList<>(mappingResources);
        itsPoolSize = poolSize;
    }

    @Override
    protected void
    configure()
    {
        ISessionFactoryProvider provider =
            new PropertiesBasedSessionFactoryProvider(
                new ApplicationPropertiesProvider(),
                itsMappingResources);

        bind(IUnitOfWorkProviderPool.class)
            .toProvider(
                () ->
                    new UnitOfWorkProviderPool(
                        itsPoolSize,
                        () -> new HibernateUnitOfWorkProvider(provider)))
            .in(Scopes.SINGLETON);

        bind(IUnitOfWorkProvider.class)
            .to(ProxyUnitOfWorkProvider.class)
            .in(getDefaultScope());
    }
}

//////////////////////////////////////////////////////////////////////////////
