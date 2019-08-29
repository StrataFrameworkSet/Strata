//////////////////////////////////////////////////////////////////////////////
// UnitOfWorkInterceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInvocation;
import strata.application.core.action.IActionQueue;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.core.unitofwork.OptimisticLockException;
import strata.foundation.core.transfer.ServiceRequest;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

import static strata.foundation.core.utility.Awaiter.await;

/*****************************************************************************
 * Intercepts method calls and wraps them in a retry-able unit of work that
 * begins a new unit-of-work before the method call and commits the unit-of-
 * work after the method call. If the commit succeeds, any events or messages
 * that are queued up to be published are sent.
 */
public
class AsynchronousUnitOfWorkInterceptor
    extends AsynchronousInterceptor
{
    private final int maxRetries;

    /*************************************************************************
     * Creates a new instance of {@code UnitOfWorkInterceptor}.
     */
    public
    AsynchronousUnitOfWorkInterceptor(
        IConsumer<MethodInvocation>             before,
        IFunction<MethodInvocation,?>           target,
        IConsumer<MethodInvocation>             after,
        IBiConsumer<MethodInvocation,Throwable> exception)
    {
        this(before,target,after,exception,1);
    }

    /*************************************************************************
     * Creates a new instance of {@code UnitOfWorkInterceptor} with the
     * specified number of retries.
     */
    public
    AsynchronousUnitOfWorkInterceptor(
        IConsumer<MethodInvocation>             before,
        IFunction<MethodInvocation,?>           target,
        IConsumer<MethodInvocation>             after,
        IBiConsumer<MethodInvocation,Throwable> exception,
        int                                     max)
    {
        super(before,target,after,exception);
        maxRetries = max;
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    public Object
    invoke(MethodInvocation invocation)
    {
        return
            getInitialStage(invocation)
                .thenCompose(context -> getBeforeStage(context))
                .thenCompose(context -> getProceedStage(context))
                .thenCompose(context -> getAfterStage(context));
    }

    protected CompletionStage<UnitOfWorkCompletionContext>
    getInitialStage(MethodInvocation invocation)
    {
        return
            getProvider(invocation)
                .thenCompose(
                    provider ->
                        CompletableFuture.supplyAsync(
                            () ->
                                new UnitOfWorkCompletionContext(
                                    invocation,
                                    provider,
                                    maxRetries)));
    }

    protected CompletionStage<UnitOfWorkCompletionContext>
    getBeforeStage(final UnitOfWorkCompletionContext context)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    doBefore(context.getInvocation());
                    return context;
                });
    }

    protected CompletionStage<UnitOfWorkCompletionContext>
    getProceedStage(final UnitOfWorkCompletionContext context)
    {
        return
            getAttemptStage(context)
               .thenApply(
                    result ->
                    {
                        System.out.println("initializing reply");
                        context.initializeReply();
                        executeActions(result.getInvocation());
                        return context;
                    })
                .exceptionally(
                    e ->
                    {
                        System.out.println("processing exception 2");
                        onException(
                            context
                                .setException(e)
                                .getInvocation(),
                            e);

                        return context;
                    });
    }

    protected CompletionStage<Object>
    getAfterStage(final UnitOfWorkCompletionContext context)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    doAfter(context.getInvocation());
                    return context.getResultIfPresent();
                });
    }

    protected CompletionStage<UnitOfWorkCompletionContext>
    getAttemptStage(final UnitOfWorkCompletionContext context)
    {
        if (context.hasMoreAttempts())
        {
            System.out.println("Attempt=" + (context.getAttempt()+1));
            context.setRequest(getRequest(context.getInvocation()));

            return
                CompletableFuture
                    .completedFuture(context.getProviderIfPresent())
                    .thenCompose(p -> p.getUnitOfWork())
                    .thenCompose(unitOfWork -> CompletableFuture.completedFuture(context.setUnitOfWork(unitOfWork)))
                    .thenCompose(c ->
                        doProceed(context.getInvocation())
                            .thenCompose(
                                result ->
                                    context
                                        .setResult(result)
                                        .getProviderIfPresent()
                                        .getUnitOfWork())
                            .thenCompose(
                                unitOfWork ->
                                {
                                    System.out.println("commit unit-of-work");
                                    return unitOfWork.commit();
                                })
                            .thenCompose(
                                ignore ->
                                {
                                    System.out.println("returning context");
                                    return CompletableFuture.completedFuture(context);
                                })
                            .exceptionally(
                                exception ->
                                {
                                    System.out.println("processing exception 1");
                                    return
                                        await(
                                            context
                                                .setException(exception.getCause())
                                                .getUnitOfWorkIfPresent()
                                                .rollback()
                                                .thenCompose(
                                                    ignore ->
                                                    {
                                                        System.out.println("debug 1: " + context.getExceptionIfPresent().getClass() );
                                                        if (
                                                            context.getExceptionIfPresent()
                                                                instanceof OptimisticLockException)
                                                        {
                                                            System.out.println("debug 2");
                                                            if (context.isLastAttempt())
                                                            {
                                                                System.out.println("debug 3");
                                                                onException(
                                                                    context.getInvocation(),
                                                                    context.getExceptionIfPresent());

                                                                return
                                                                    CompletableFuture
                                                                        .completedFuture(context);
                                                            }

                                                            System.out.println("retrying operation");
                                                            return
                                                                getAttemptStage(
                                                                    context.incrementAttempt());
                                                        }

                                                        onException(
                                                            context.getInvocation(),
                                                            context.getExceptionIfPresent());

                                                        return
                                                            CompletableFuture
                                                                .completedFuture(context);
                                                    }));
                                }));
        }

        return CompletableFuture.completedFuture(context);
    }

    /*************************************************************************
     * Executes the queued up actions if the target object provides a queue.
     *
     * @param  invocation the method invocation on the target object
     */
    protected  void
    executeActions(MethodInvocation invocation)
        throws CompletionException
    {
        try
        {
            IActionQueue queue = getQueue(invocation);

            if (queue != null)
                queue.execute();
        }
        catch (Throwable cause)
        {
            throw new CompletionException(cause);
        }
    }

    /*************************************************************************
     * Gets the {@code IUnitOfWorkProvider} associated with
     * the target object.
     *
     * @param  invocation the method invocation on the target object
     * @return associated {@code IUnitOfWorkProvider}
     */
    protected CompletionStage<IUnitOfWorkProvider>
    getProvider(MethodInvocation invocation)
    {
        if (invocation.getThis() instanceof IUnitOfWorkPropertySupplier)
        {
            IUnitOfWorkProvider provider =
                ((IUnitOfWorkPropertySupplier)
                     invocation.getThis()).getProvider();

            return
                CompletableFuture.supplyAsync(
                    () -> provider,provider.getExecutor());
        }

        return
            CompletableFuture.failedFuture(
                new IllegalStateException(
                    "Must implement IUnitOfWorkPropertySupplier"));
    }

    /*************************************************************************
     * Gets the {@code IStringMessageQueue} associated with
     * the target object.
     *
     * @param  invocation the method invocation on the target object
     * @return associated {@code IStringMessageQueue}
     */
    protected IActionQueue
    getQueue(MethodInvocation invocation)
    {
        if (invocation.getThis() instanceof IUnitOfWorkPropertySupplier)
            return
                ((IUnitOfWorkPropertySupplier)
                     invocation.getThis()).getQueue();

        throw
            new IllegalStateException(
                "Must implement IUnitOfWorkPropertySupplier");
    }

    /*************************************************************************
     * Gets the producer {@code Properties} associated with
     * the target object.
     *
     * @param  invocation the method invocation on the target object
     * @return associated producer {@code Properties}
     */
    protected Properties
    getConfiguration(MethodInvocation invocation)
    {
        if (invocation.getThis() instanceof IUnitOfWorkPropertySupplier)
            return
                ((IUnitOfWorkPropertySupplier)
                     invocation.getThis()).getConfiguration();

        throw
            new IllegalStateException(
                "Must implement IUnitOfWorkPropertySupplier");
    }

    /*************************************************************************
     * Gets the {@code IUnitOfWorkProvider} associated with
     * the target object.
     *
     * @param  invocation the method invocation on the target object
     * @return associated {@code IUnitOfWorkProvider}
     */
    protected ServiceRequest
    getRequest(MethodInvocation invocation)
    {
        for (Object arg:invocation.getArguments())
            if (arg instanceof ServiceRequest)
                return (ServiceRequest)arg;

        return null;
    }

    @Override
    protected void
    doBefore(MethodInvocation invocation)
    {
        try
        {
            super.doBefore(invocation);
        }
        catch (Throwable cause)
        {
            throw new CompletionException(cause);
        }
    }

    @Override
    protected CompletionStage<Object>
    doProceed(MethodInvocation invocation)
    {
        try
        {
            return (CompletionStage<Object>)super.doProceed(invocation);
        }
        catch (Throwable cause)
        {
            throw new CompletionException(cause);
        }
    }

    @Override
    protected void
    onException(MethodInvocation invocation,Throwable e)
    {
        try
        {
            super.onException(invocation,e);
        }
        catch (Throwable cause)
        {
            throw new CompletionException(cause);
        }
    }

    @Override
    protected void
    doAfter(MethodInvocation invocation)
    {
        try
        {
            super.doAfter(invocation);
        }
        catch (Throwable cause)
        {
            throw new CompletionException(cause);
        }
    }
}

//////////////////////////////////////////////////////////////////////////////
