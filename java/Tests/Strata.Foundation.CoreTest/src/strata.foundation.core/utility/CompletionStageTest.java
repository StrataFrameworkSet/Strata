//////////////////////////////////////////////////////////////////////////////
// CompletionStageTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static strata.foundation.core.utility.Awaiter.await;

public
class CompletionStageTest
{
    @Before
    public void
    setUp() {}

    @After
    public void
    tearDown() {}

    @Test
    public void
    testConditionalBranchingOnException()
    {
        await(
            CompletableFuture
                .supplyAsync(
                    () ->
                    {
                        System.out.println("Stage 1");

                        if (true)
                            throw new CompletionException(new IllegalStateException());

                        return CompletionContext.of("Stage1Result");
                    })
                .exceptionally(
                    e ->
                    {
                        System.out.println("Exception 1");

                        if (e.getCause() instanceof IllegalStateException)
                            System.out.println("\tprocessing IllegalStateException");

                        return CompletionContext.of(e.getCause());
                    })
                .thenApply(
                    context ->
                    {
                        System.out.println("Stage 2");

                        return
                            context.combine(
                                CompletionContext.of("->Stage2Result"),
                                (x,y) -> x + y);
                    }))
            .apply(
                context ->
                {
                    if (context.hasException())
                        context
                            .getException()
                            .ifPresent(exception -> System.out.println(exception.toString()));
                            /*
                            .ifPresentOrElse(
                                exception -> System.out.println(exception.toString()),
                                () -> System.out.println("error 1"));

                             */
                    else
                        context
                            .getResult()
                            .ifPresent(result -> System.out.println(result));
                            /*
                            .ifPresentOrElse(
                                result -> System.out.println(result),
                                () -> System.out.println("error 2"));

                             */
                });

    }
}

//////////////////////////////////////////////////////////////////////////////
