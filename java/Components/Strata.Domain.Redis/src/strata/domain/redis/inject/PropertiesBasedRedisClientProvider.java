//////////////////////////////////////////////////////////////////////////////
// PropertiesBasedRedisClientProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.inject;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;

import javax.inject.Inject;
import java.util.Properties;

public
class PropertiesBasedRedisClientProvider
    implements IRedisClientProvider
{
    private final Properties   itsProperties;
    private final String       itsRedisHostUriKey;
    private static RedisClient theirClient = null;

    @Inject
    public
    PropertiesBasedRedisClientProvider(Properties properties)
    {
        this(properties,"redis.host.uri");
    }

    public
    PropertiesBasedRedisClientProvider(
        Properties properties,
        String     redisHostUriKey)
    {
        itsProperties = properties;
        itsRedisHostUriKey = redisHostUriKey;

        if (!itsProperties.containsKey(itsRedisHostUriKey))
            throw
                new IllegalArgumentException(
                    "properties does not contain: " +
                        itsRedisHostUriKey);
    }

    @Override
    public synchronized RedisClient
    get()
    {
        if (theirClient == null)
        {
            theirClient =
                RedisClient.create(
                    itsProperties.getProperty(itsRedisHostUriKey));
            theirClient.setOptions(
                ClientOptions
                    .builder()
                    .autoReconnect(true)
                    .build());
        }

        return theirClient;
    }
}

//////////////////////////////////////////////////////////////////////////////
