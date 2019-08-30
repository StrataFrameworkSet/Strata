//////////////////////////////////////////////////////////////////////////////
// Foo.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

public
class Foo
    implements IFoo
{
    @Override
    @Timed
    public void
    synchronousMethod()
        throws Exception
    {
        Thread.sleep(2500);
    }

    @Override
    @Timed
    public CompletionStage<String>
    asynchronousMethod(String input)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    try
                    {
                        Thread.sleep(2500);
                        return input;
                    }
                    catch (Exception e)
                    {
                        throw new CompletionException(e);
                    }
                });
    }
}

//////////////////////////////////////////////////////////////////////////////
