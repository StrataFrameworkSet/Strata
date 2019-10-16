//////////////////////////////////////////////////////////////////////////////
// UnitOfWorkInterceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.core.unitofwork.OptimisticLockException;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.transfer.ServiceRequest;

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
    private final int    maxRetries;
    private final Logger logger =
        LogManager.getLogger(AsynchronousUnitOfWorkInterceptor.class);

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
        logger.debug("getInitialStage");
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
        logger.debug("getBeforeStage");
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    doBefore(
                        context
                            .start()
                            .getInvocation());
                    return context;
                });
    }

    protected CompletionStage<UnitOfWorkCompletionContext>
    getProceedStage(final UnitOfWorkCompletionContext context)
    {
        logger.debug("getProceedStage");
        return
            getAttemptStage(context)
               .thenApply(
                    result ->
                    {
                        logger.debug("initializing reply");
                        context.initializeReply();
                        logger.debug("execution actions");
                        executeActions(result.getInvocation());
                        logger.debug("returning context from proceed");
                        return context;
                    })
                .exceptionally(
                    e ->
                    {
                        logger.debug("processing exception in proceed stage");
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
        logger.debug("getAfterStage");
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    doAfter(context.getInvocation());
                    return
                        context
                            .stop()
                            .getResultIfPresent();
                });
    }

    protected CompletionStage<UnitOfWorkCompletionContext>
    getAttemptStage(final UnitOfWorkCompletionContext context)
    {
        logger.debug("getAttemptStage");
        if (context.hasMoreAttempts())
        {
            logger.debug("Attempt=" + (context.getAttempt()+1));
            context
                .setRequest(getRequest(context.getInvocation()))
                .clearException();

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
                                    logger.debug("commit unit-of-work");
                                    return unitOfWork.commit();
                                })
                            .thenCompose(
                                ignore ->
                                {
                                    logger.debug("returning context from attempt stage");
                                    return CompletableFuture.completedFuture(context);
                                })
                            .exceptionally(
                                exception ->
                                {
                                    logger.debug("processing exception in attempt stage");
                                    return
                                        await(
                                            context
                                                .setException(exception.getCause())
                                                .getUnitOfWorkIfPresent()
                                                .rollback()
                                                .thenCompose(
                                                    ignore ->
                                                    {
                                                        logger.debug(
                                                            "exception: {}",
                                                            context.getExceptionIfPresent().getClass() );

                                                        if (
                                                            context.getExceptionIfPresent()
                                                                instanceof OptimisticLockException)
                                                        {
                                                            logger.debug("handling optimistic lock exception");

                                                            if (context.isLastAttempt())
                                                            {
                                                                logger.debug("last attempt - completing exceptionally");
                                                                onException(
                                                                    context.getInvocation(),
                                                                    context.getExceptionIfPresent());

                                                                return
                                                                    CompletableFuture
                                                                        .completedFuture(context);
                                                            }

                                                            logger.debug("retrying operation");
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
        logger.debug("executeActions");
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
        logger.debug("getting unit of work provider");

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
            CompletableFuture.supplyAsync(
                () ->
                {
                    throw
                        new IllegalStateException(
                            "Must implement IUnitOfWorkPropertySupplier");
                });
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
        logger.debug("getting action queue");

        if (invocation.getThis() instanceof IUnitOfWorkPropertySupplier)
            return
                ((IUnitOfWorkPropertySupplier)
                     invocation.getThis()).getQueue();

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
            {
                logger.debug("getting service request");
                return (ServiceRequest)arg;
            }

        return null;
    }

    @Override
    protected void
    doBefore(MethodInvocation invocation)
    {
        logger.debug("doBefore");

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
        logger.debug("doProceed");

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
        logger.debug("onException");

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
        logger.debug("doAfter");

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
