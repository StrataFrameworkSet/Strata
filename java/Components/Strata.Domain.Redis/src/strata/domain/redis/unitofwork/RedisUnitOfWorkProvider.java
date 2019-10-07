//////////////////////////////////////////////////////////////////////////////
// RedisUnitOfWorkProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.unitofwork;

import io.lettuce.core.RedisClient;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public
class RedisUnitOfWorkProvider
    implements IUnitOfWorkProvider
{
    private RedisClient itsClient;
    private ExecutorService itsExecutor;

    public
    RedisUnitOfWorkProvider(RedisClient client)
    {
        itsClient = client;
        itsExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public CompletionStage<IUnitOfWork>
    getUnitOfWork()
    {
        return null;
    }

    @Override
    public CompletionStage<Void>
    clearUnitOfWork()
    {
        return null;
    }

    @Override
    public ExecutorService
    getExecutor()
    {
        return itsExecutor;
    }
}

//////////////////////////////////////////////////////////////////////////////
