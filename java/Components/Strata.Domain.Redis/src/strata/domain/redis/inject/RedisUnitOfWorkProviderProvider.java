//////////////////////////////////////////////////////////////////////////////
// RedisUnitOfWorkProviderProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.inject;

import strata.domain.core.inject.IUnitOfWorkProviderProvider;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.redis.unitofwork.RedisUnitOfWorkProvider;

import javax.inject.Inject;

public
class RedisUnitOfWorkProviderProvider
    implements IUnitOfWorkProviderProvider
{
    private final IRedisClientProvider itsProvider;

    @Inject
    RedisUnitOfWorkProviderProvider(IRedisClientProvider provider)
    {
        itsProvider = provider;
    }

    @Override
    public IUnitOfWorkProvider
    get()
    {
        return new RedisUnitOfWorkProvider(itsProvider.get());
    }
}

//////////////////////////////////////////////////////////////////////////////
