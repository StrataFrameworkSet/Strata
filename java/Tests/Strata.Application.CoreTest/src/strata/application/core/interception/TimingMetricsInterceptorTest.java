//////////////////////////////////////////////////////////////////////////////
// TimingMetricsInterceptorTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;
import static strata.foundation.core.utility.Awaiter.await;

public
class TimingMetricsInterceptorTest
{
    private Injector itsInjector;
    private IFoo     itsTarget;

    @Before
    public void
    setUp()
        throws Exception
    {
        itsInjector = Guice.createInjector(new TestModule());
        itsTarget = itsInjector.getInstance(IFoo.class);
    }

    @After
    public void
    tearDown()
    {
        itsTarget = null;
        itsInjector = null;
    }

    @Test
    public void
    testSynchronousMethod()
        throws Exception
    {
        itsTarget.synchronousMethod();
    }

    @Test
    public void
    testAsynchronousMethod()
    {
        CompletionStage<String> future = itsTarget.asynchronousMethod("FOO");

        assertEquals("FOO",await(future));
    }
}

//////////////////////////////////////////////////////////////////////////////
