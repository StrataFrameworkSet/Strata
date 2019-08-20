//////////////////////////////////////////////////////////////////////////////
// Awaiter.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import java.util.concurrent.CompletionStage;

public
class Awaiter
{
    public static <T> T
    await(CompletionStage<T> stage)
    {
        T output = stage.toCompletableFuture().join();

        if (stage.toCompletableFuture().isCompletedExceptionally())
        {
            throw
                stage
                    .toCompletableFuture()
                    .handle((x,e) -> new RuntimeException(e))
                    .join();
        }

        return output;
    }
}

//////////////////////////////////////////////////////////////////////////////
