//////////////////////////////////////////////////////////////////////////////
// IRedisClientProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.inject;

import io.lettuce.core.RedisClient;

import javax.inject.Provider;

public
interface IRedisClientProvider
    extends Provider<RedisClient> {}

//////////////////////////////////////////////////////////////////////////////