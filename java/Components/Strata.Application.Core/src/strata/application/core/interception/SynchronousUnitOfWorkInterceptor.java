//////////////////////////////////////////////////////////////////////////////
// UnitOfWorkInterceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInvocation;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.core.unitofwork.OptimisticLockException;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.transfer.ServiceReply;
import strata.foundation.core.transfer.ServiceRequest;

import java.time.Instant;

import static strata.foundation.core.utility.Awaiter.await;

/*****************************************************************************
 * Intercepts method calls and wraps them in a retry-able unit of work that
 * begins a new unit-of-work before the method call and commits the unit-of-
 * work after the method call. If the commit succeeds, any events or messages
 * that are queued up to be published are sent.
 */
public
class SynchronousUnitOfWorkInterceptor
    extends SynchronousInterceptor
{
    private final int maxRetries;

    /*************************************************************************
     * Creates a new instance of {@code UnitOfWorkInterceptor}.
     */
    public
    SynchronousUnitOfWorkInterceptor(
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
    SynchronousUnitOfWorkInterceptor(
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
        throws Throwable
    {
        try
        {
            IUnitOfWorkProvider provider = getProvider(invocation);

            doBefore(invocation);

            for (int attempt = 1;attempt <= maxRetries;++attempt)
            {
                IUnitOfWork  unitOfWork = await(provider.getUnitOfWork());
                Object result = null;
                try
                {
                    ServiceRequest request = getRequest(invocation);
                    ServiceReply   reply = null;

                    result = doProceed(invocation);
                    commit(unitOfWork);

                    if (request != null && result instanceof ServiceReply)
                    {
                        reply = (ServiceReply)result;

                        reply
                            .setCorrelationId(request.getCorrelationId())
                            .setTimestamp(Instant.now());
                    }
                }
                catch (OptimisticLockException e)
                {
                    rollback(unitOfWork);

                    if (attempt == maxRetries)
                    {
                        onException(invocation,e);
                        throw e;
                    }

                }
                catch (Exception e)
                {
                    rollback(unitOfWork);
                    onException(invocation,e);
                    throw e;
                }

                executeActions(invocation);
                return result;

            }

            throw
                new IllegalStateException("Should not reach this point.");
        }
        catch (Exception e)
        {
            onException(invocation,e);
            throw e;
        }
        finally
        {
            doAfter(invocation);
        }

    }

    /*************************************************************************
     * Executes the queued up actions if the target object provides a queue.
     *
     * @param  invocation the method invocation on the target object
     */
    protected  void
    executeActions(MethodInvocation invocation)
        throws Exception
    {
        IActionQueue queue = getQueue(invocation);

        if (queue != null)
            queue.execute();
    }

    /*************************************************************************
     * Gets the {@code IUnitOfWorkProvider} associated with
     * the target object.
     *
     * @param  invocation the method invocation on the target object
     * @return associated {@code IUnitOfWorkProvider}
     */
    protected IUnitOfWorkProvider
    getProvider(MethodInvocation invocation)
    {
        if (invocation.getThis() instanceof IUnitOfWorkPropertySupplier)
            return
                ((IUnitOfWorkPropertySupplier)
                     invocation.getThis()).getProvider();

        throw
            new IllegalStateException(
                "Must implement IUnitOfWorkPropertySupplier");
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

    /*************************************************************************
     * Commits the unit-of-work.
     *
     * @param  unitOfWork unit-of-work being committed
     * @throws Throwable
     */
    protected void
    commit(IUnitOfWork unitOfWork)
        throws Throwable
    {
        unitOfWork.commit();
    }

    /*************************************************************************
     * Rolls back the unit-of-work.
     *
     * @param  unitOfWork unit-of-work being rolled back
     * @throws Throwable
     */
    protected void
    rollback(IUnitOfWork unitOfWork)
        throws Throwable
    {
        unitOfWork.rollback();
    }

}

//////////////////////////////////////////////////////////////////////////////
