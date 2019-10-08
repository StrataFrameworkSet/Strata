//////////////////////////////////////////////////////////////////////////////
// RedisFooBarRepositoryTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.repository;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.redis.unitofwork.RedisUnitOfWorkProvider;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static strata.foundation.core.utility.Awaiter.await;

public
class RedisFooBarRepositoryTest
{
    private RedisClient             itsClient;
    private RedisUnitOfWorkProvider itsProvider;
    private IFooBarRepository       itsTarget;

    @Before
    public void
    setUp()
    {
        itsClient = RedisClient.create("redis://localhost:6379");

        itsClient.setOptions(
            ClientOptions
                .builder()
                .autoReconnect(true)
                .build());
        itsProvider = new RedisUnitOfWorkProvider(itsClient);

        itsProvider
            .insertRetriever(
                FooBar.class,
                entity -> entity.getId())
            .insertReplicator(
                FooBar.class,
                (original) -> new FooBar(original));

        itsTarget = new FooBarRepository(itsProvider);
    }

    @After
    public void
    tearDown()
    {
        itsTarget = null;
        itsProvider.clearUnitOfWork();
        itsProvider = null;
        itsClient.shutdown();
        itsClient = null;
    }

    @Test
    public void
    testInsert()
    {
        IUnitOfWork unitOfWork = await(itsTarget.getProvider().getUnitOfWork());
        FooBar expected =
            new FooBar()
                .setId(new Random().nextLong())
                .setFoo("FOO_VALUE")
                .setBar(new Random().nextInt());
        FooBar actual = await(itsTarget.insert(expected));

        assertEquals(expected,actual);
        actual = await(itsTarget.getUnique(expected.getId())).get();
        assertEquals(expected,actual);
        unitOfWork.commit();
        actual = await(itsTarget.getUnique(expected.getId())).get();
        assertEquals(expected,actual);
    }
}

//////////////////////////////////////////////////////////////////////////////
