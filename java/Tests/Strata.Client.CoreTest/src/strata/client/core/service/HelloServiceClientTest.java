//////////////////////////////////////////////////////////////////////////////
// HelloServiceClientTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.application.core.platform.SayHelloReply;
import strata.application.core.platform.SayHelloRequest;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static strata.foundation.core.utility.Awaiter.await;

public abstract
class HelloServiceClientTest
{
    private IHelloService itsTarget;

    @Before
    public void
    setUp()
    {
        itsTarget = createTarget();
    }

    @After
    public void
    tearDown()
    {
        itsTarget = null;
    }

    @Test
    public void
    testSayHelloSync()
    {
        SayHelloRequest request =
            (SayHelloRequest)
                new SayHelloRequest()
                    .setUser("John")
                    .setGreeting("Hello")
                    .setTimestamp(Instant.now())
                    .setCorrelationId(UUID.randomUUID());
        SayHelloReply reply =
            itsTarget.sayHelloSync(request);

        assertTrue(reply.isSuccess());
        assertEquals("Hello John",reply.getGreeting());
    }

    @Test
    public void
    testSayHelloAsync()
    {
        SayHelloRequest request =
            (SayHelloRequest)
                new SayHelloRequest()
                    .setUser("John")
                    .setGreeting("Hello")
                    .setTimestamp(Instant.now())
                    .setCorrelationId(UUID.randomUUID());
        SayHelloReply reply =
            await(itsTarget.sayHelloAsync(request));

        assertTrue(reply.isSuccess());
        assertEquals("Hello John",reply.getGreeting());
    }

    protected abstract IHelloService
    createTarget();
}

//////////////////////////////////////////////////////////////////////////////
