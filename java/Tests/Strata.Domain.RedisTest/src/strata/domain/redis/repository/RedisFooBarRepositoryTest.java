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
import strata.domain.redis.namedquery.RedisGetListNamedQuery;
import strata.domain.redis.unitofwork.RedisUnitOfWorkProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static strata.foundation.core.utility.Awaiter.await;

public
class RedisFooBarRepositoryTest
{
    private RedisClient             itsClient;
    private RedisUnitOfWorkProvider itsProvider;
    private IFooBarRepository       itsTarget;
    private Set<Long>               itsIds;

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
            .insertNamedQuery(
                FooBar.class,
                new RedisGetListNamedQuery<>(
                    itsProvider,
                    "getAllIn",
                    Long.class,
                    FooBar.class,
                    "ids",
                    (k,e) -> k.contains(e.getId())))
            .insertRetriever(
                FooBar.class,
                entity -> entity.getId())
            .insertReplicator(
                FooBar.class,
                (original) -> new FooBar(original));

        itsTarget = new FooBarRepository(itsProvider);
        itsIds = new HashSet<>();
    }

    @After
    public void
    tearDown()
    {
        IUnitOfWork unitOfWork = await(itsTarget.getProvider().getUnitOfWork());

        for (FooBar f : await(itsTarget.getAllIn(itsIds)))
            await(itsTarget.remove(f));

        await(unitOfWork.commit());

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

    @Test
    public void
    testGetAllIn()
    {
        IUnitOfWork unitOfWork = await(itsTarget.getProvider().getUnitOfWork());
        FooBar expected1 =
            new FooBar()
                .setId(new Random().nextLong())
                .setFoo("FOO_VALUE")
                .setBar(new Random().nextInt());
        FooBar expected2 =
            new FooBar()
                .setId(new Random().nextLong())
                .setFoo("FOO_VALUE")
                .setBar(new Random().nextInt());
        FooBar actual1 = await(itsTarget.insert(expected1));
        FooBar actual2 = await(itsTarget.insert(expected2));
        Collection<FooBar> actual = await(itsTarget.getAllIn(getIds(expected1,expected2)));

        unitOfWork.commit();

        assertEquals(2,actual.size());
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));

        actual = await(itsTarget.getAllIn(getIds(expected1,expected2)));

        assertEquals(2,actual.size());
        assertTrue(actual.contains(expected1));
        assertTrue(actual.contains(expected2));
    }

    Set<Long>
    getIds(FooBar... entities)
    {
        Set<Long> ids = new HashSet<>();

        for (FooBar entity:entities)
            ids.add(entity.getId());

        return ids;
    }
}

//////////////////////////////////////////////////////////////////////////////
