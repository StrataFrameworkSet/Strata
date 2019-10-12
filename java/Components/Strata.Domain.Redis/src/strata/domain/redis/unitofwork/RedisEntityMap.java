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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        try
        {
            return
                itsMapper.toObject(
                    key.getType(),itsReader.get(key.toString()));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public <E,K> E
    get(Class<E> entityType,K key)
    {
        try
        {
            return
                itsMapper.toObject(
                    entityType,
                    itsReader.get(
                        new EntityIdentifier(entityType,key).toString()));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public <E> Collection<E>
    getEntitiesByType(Class<E> entityType)
    {
        Collection<E> entities = new ArrayList<>();
        List<String>  keys = itsReader.keys(entityType.getCanonicalName() + ":");

        for (String key:keys)
        {
            E entity =
                itsMapper.toObject(entityType,itsReader.get(key));

            if (entity != null)
                entities.add(entity);
        }
        return entities;
    }

    public <E,K> Collection<E>
    getEntitiesIn(Class<E> entityType,Set<K> keys)
    {
        Collection<E> entities = new ArrayList<>();

        for (K key:keys)
        {
            E entity = get(entityType,key);

            if (entity != null)
                entities.add(entity);
        }
        return entities;
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