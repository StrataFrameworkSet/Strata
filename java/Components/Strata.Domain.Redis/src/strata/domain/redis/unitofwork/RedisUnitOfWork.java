//////////////////////////////////////////////////////////////////////////////
// RedisUnitOfWork.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.unitofwork;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.unitofwork.AbstractUnitOfWork;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public
class RedisUnitOfWork
    extends AbstractUnitOfWork
{
    private StatefulRedisConnection<String,String> itsConnection;
    private RedisAsyncCommands<String,String>      itsCommands;
    private RedisFuture<String>                    itsTransaction;

    public
    RedisUnitOfWork(RedisUnitOfWorkProvider provider,RedisClient client)
    {
        super(provider);
        itsConnection = client.connect();
        itsCommands = itsConnection.async();
        itsTransaction = itsCommands.multi();
    }

    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doInsertNew(Class<K> keyType,Class<E> entityType,E newEntity)
    {
        return null;
    }

    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return null;
    }

    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doRemoveExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return null;
    }

    @Override
    protected <K extends Serializable,E> CompletionStage<Optional<E>>
    doGetExisting(Class<E> type,K key)
    {
        return null;
    }

    @Override
    protected <E> CompletionStage<INamedQuery<E>>
    doGetNamedQuery(Class<E> type,String queryName)
    {
        return null;
    }

    @Override
    protected <K extends Serializable,E> CompletionStage<Boolean>
    doHasExisting(Class<E> type,K key)
    {
        return null;
    }

    @Override
    protected <E> CompletionStage<Boolean>
    doHasNamedQuery(Class<E> type,String queryName)
    {
        return null;
    }

    @Override
    protected CompletionStage<Void>
    doCommit()
    {
        return
            itsCommands
                .exec()
                .thenAccept(
                    result ->
                    {

                    });
    }

    @Override
    public void
    close()
    {
        itsConnection.close();
    }
}

//////////////////////////////////////////////////////////////////////////////
