//////////////////////////////////////////////////////////////////////////////
// AvroObjectMapperTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.foundation.core.event.FooData;
import strata.foundation.core.utility.IObjectMapper;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public
class AvroObjectMapperTest
{
    private IObjectMapper<Object,String> itsTarget;

    @Before
    public void
    setUp()
    {
        itsTarget = new AvroObjectMapper<>();
    }

    @After
    public void
    tearDown()
    {
        itsTarget = null;
    }

    @Test
    public void
    testToPayloadToObject()
    {
        FooData expected =
            FooData
                .newBuilder()
                .setId("1234567")
                .setX("XXXXXXXXXXXXXXXXXXX")
                .setY(new Random().nextInt())
                .build();
        FooData actual =
            itsTarget.toObject(
                FooData.class,
                itsTarget.toPayload(expected));

        assertEquals(expected,actual);
    }
}

//////////////////////////////////////////////////////////////////////////////
