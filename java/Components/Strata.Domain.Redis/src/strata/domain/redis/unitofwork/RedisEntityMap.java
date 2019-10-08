//////////////////////////////////////////////////////////////////////////////
// RedisEntityMap.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.unitofwork;

import io.lettuce.core.RedisClient;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import strata.domain.core.unitofwork.CommitFailedException;
import strata.domain.core.unitofwork.OptimisticLockException;
import strata.foundation.core.utility.IObjectMapper;

import java.util.Set;

public
class RedisEntityMap
{
    private RedisClient                  itsClient;
    private StatefulRedisConnection      itsWriteConnection;
    private StatefulRedisConnection      itsReadConnection;
    private RedisCommands<String,String> itsWriter;
    private RedisCommands<String,String> itsReader;
    private IObjectMapper<Object,String> itsMapper;

    public
    RedisEntityMap(RedisClient client,IObjectMapper<Object,String> mapper)
    {
        itsClient = client;
        itsWriteConnection = client.connect();
        itsReadConnection = client.connect();
        itsWriter = itsWriteConnection.sync();
        itsReader = itsReadConnection.sync();
        itsMapper = mapper;
    }

    public void
    beginTransaction(Set<EntityIdentifier> ids)
    {
        itsWriter.watch(toStringArray(ids));
        itsWriter.multi();
    }

    public void
    commitTransaction()
        throws CommitFailedException
    {
        TransactionResult result = itsWriter.exec();

        if (result.wasDiscarded())
            throw new OptimisticLockException("transaction was discarded");
    }

    public Object
    insert(EntityIdentifier key,Object value)
    {
        itsWriter.set(key.toString(),itsMapper.toPayload(value));
        return value;
    }

    public Object
    remove(EntityIdentifier key)
    {
        itsWriter.del(key.toString());
        return key;
    }

    public Object
    get(EntityIdentifier key)
    {
        return itsMapper.toObject(key.getType(),itsReader.get(key.toString()));
    }

    public boolean
    containsKey(EntityIdentifier key)
    {
        return
            !itsReader
                .keys(key.toString())
                .isEmpty();
    }

    private static String[]
    toStringArray(Set<EntityIdentifier> ids)
    {
        String[] keys = new String[ids.size()];
        int i = 0;

        for (EntityIdentifier id:ids)
            keys[i++] = id.toString();

        return keys;
    }
}

//////////////////////////////////////////////////////////////////////////////