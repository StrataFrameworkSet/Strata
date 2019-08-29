//////////////////////////////////////////////////////////////////////////////
// TimingMetricsInterceptorTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.domain.core.testdomain.PersonAge;
import strata.domain.core.testdomain.PersonName;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static strata.foundation.core.utility.Awaiter.await;

public
class UnitOfWorkInterceptorTest
{
    private Injector        itsInjector;
    private IPersonService  itsTarget;

    @Before
    public void
    setUp()
        throws Exception
    {
        itsInjector = Guice.createInjector(new TestModule());
        itsTarget = itsInjector.getInstance(IPersonService.class);
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
    testCreatePerson()
    {
        CreatePersonRequest request =
            (CreatePersonRequest)
                new CreatePersonRequest()
                    .setName(new PersonName("John","Doe"))
                    .setAge(new PersonAge(LocalDate.of(1950,2,5)))
                    .setCorrelationId(UUID.randomUUID());
        CompletionStage<CreatePersonReply> future =
            itsTarget.createPerson(request);
        CreatePersonReply reply = await(future);

        assertNotNull(reply);
        assertEquals(request.getCorrelationId(),reply.getCorrelationId());
        assertTrue(reply.getFailureMessage(),reply.isSuccess());
    }
}

//////////////////////////////////////////////////////////////////////////////
