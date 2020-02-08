//////////////////////////////////////////////////////////////////////////////
// ObjectMapperTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public
class ObjectMapperTest
{
    private IObjectMapper<Object,String> itsTarget;

    @Before
    public void
    setUp()
    {
        itsTarget = new JsonObjectMapper<>();
    }

    @After
    public void
    tearDown()
    {
        itsTarget = null;
    }

    @Test
    public void
    testRoundTripWithListProperty()
    {
        FooBaz expected =
            (FooBaz)
                new FooBaz()
                    .setBaz(Arrays.asList("XXXX","YYYY","ZZZZ"))
                    .setBar(23)
                    .setFoo("XYZ");
        FooBaz actual =
            itsTarget.toObject(FooBaz.class,itsTarget.toPayload(expected));

        System.out.println(itsTarget.toPayload(expected));
    }
}

//////////////////////////////////////////////////////////////////////////////
