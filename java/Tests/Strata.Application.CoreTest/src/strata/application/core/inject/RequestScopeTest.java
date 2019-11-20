//////////////////////////////////////////////////////////////////////////////
// RequestScopeTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public
class RequestScopeTest
{
    private IRequestProvider itsProvider;

    @Before
    public void
    setUp()
    {
        Injector injector = Guice.createInjector(new TestModule());

        itsProvider = injector.getInstance(IRequestProvider.class);
    }

    @After
    public void
    tearDown()
    {
        itsProvider = null;
    }

    @Test
    public void
    testRequestScope()
        throws Exception
    {
        Map<Integer,List<Integer>> lists = new TreeMap<>();

        for (int i=0;i < 5;i++)
        {
            try (Request request = itsProvider.get())
            {
                List<Integer> list =
                    request.getInstance(
                        new TypeLiteral<List<Integer>>(){});

                list.addAll(Arrays.asList(i,i,i,i,i));
                lists.put(i,list);
            }
        }

        assertEquals(5,lists.size());

        assertTrue(lists.containsKey(0));
        assertTrue(lists.containsKey(1));
        assertTrue(lists.containsKey(2));
        assertTrue(lists.containsKey(3));
        assertTrue(lists.containsKey(4));

        for (Integer i: lists.keySet())
        {
            List<Integer> list = lists.get(i);

            assertEquals(5,list.size());

            for (Integer j: list)
                assertEquals(i,j);
        }
    }
}

//////////////////////////////////////////////////////////////////////////////
