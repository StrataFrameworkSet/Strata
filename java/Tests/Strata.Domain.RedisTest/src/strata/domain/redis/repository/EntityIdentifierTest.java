//////////////////////////////////////////////////////////////////////////////
// EntityIdentifierTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.repository;

import org.junit.Test;
import strata.domain.redis.unitofwork.EntityIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public
class EntityIdentifierTest
{
    @Test
    public void
    testHashCode()
    {
        Long key = new Random().nextLong();
        EntityIdentifier id1 = new EntityIdentifier(FooBar.class,key);
        EntityIdentifier id2 = new EntityIdentifier(FooBar.class,key);

        assertEquals(id1.hashCode(),id2.hashCode());
    }

    @Test
    public void
    testMap()
    {
        Map<EntityIdentifier,Object> entities = new HashMap<>();
        Long key = new Random().nextLong();
        EntityIdentifier id1 = new EntityIdentifier(FooBar.class,key);
        EntityIdentifier id2 = new EntityIdentifier(FooBar.class,key);

        entities.put(id1,new FooBar());
        assertTrue(entities.containsKey(id2));
    }
}

//////////////////////////////////////////////////////////////////////////////
