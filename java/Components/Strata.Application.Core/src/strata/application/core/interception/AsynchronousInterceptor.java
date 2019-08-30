//////////////////////////////////////////////////////////////////////////////
// Interceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

public
class AsynchronousInterceptor
    extends    BaseInterceptor
    implements MethodInterceptor
{
    protected
    AsynchronousInterceptor(
        IConsumer<MethodInvocation>             before,
        IFunction<MethodInvocation,?>           target,
        IConsumer<MethodInvocation>             after,
        IBiConsumer<MethodInvocation,Throwable> exception)
    {
        super(before,target,after,exception);
    }

    @Override
    public Object
    invoke(MethodInvocation invocation)
    {
        return
            CompletableFuture
                .supplyAsync(
                    () ->
                    {
                        try
                        {
                            doBefore(invocation);
                            return
                                ((CompletionStage<?>)
                                     doProceed(invocation))
                                        .toCompletableFuture()
                                        .join();
                        }
                        catch (Throwable t1)
                        {
                            try
                            {
                                onException(invocation,t1);
                                throw new CompletionException(t1);
                            }
                            catch (Throwable t2)
                            {
                                throw new CompletionException(t2);
                            }
                        }
                        finally
                        {
                            try
                            {
                                doAfter(invocation);
                            }
                            catch (Throwable t)
                            {
                                throw new CompletionException(t);
                            }
                        }
                    });
    }

}

//////////////////////////////////////////////////////////////////////////////
