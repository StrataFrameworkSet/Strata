//////////////////////////////////////////////////////////////////////////////
// AwaiterTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static strata.foundation.core.utility.Awaiter.await;

public
class AwaiterTest
{
    Executor itsExecutor = Executors.newSingleThreadExecutor();

    @Test
    public void
    testAwaitWithExceptions()
    {
        String result =
            await(
                CompletableFuture
                    .supplyAsync(
                        () -> getResult(),
                        Executors.newSingleThreadExecutor()));

        Assert.assertEquals("Test",result);
    }

    private String
    getResult()
    {
        return
            await(
                CompletableFuture
                    .supplyAsync(
                        () ->
                        {
                            if (false)
                                throw new RuntimeException();

                            return "Test";
                        },
                        itsExecutor));
    }
}

//////////////////////////////////////////////////////////////////////////////
