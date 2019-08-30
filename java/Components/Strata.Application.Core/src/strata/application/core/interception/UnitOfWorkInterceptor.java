//////////////////////////////////////////////////////////////////////////////
// UnitOfWorkInterceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInvocation;

/*****************************************************************************
 * Intercepts method calls and wraps them in a retry-able unit of work that
 * begins a new unit-of-work before the method call and commits the unit-of-
 * work after the method call. If the commit succeeds, any events or messages
 * that are queued up to be published are sent.
 */
public
class UnitOfWorkInterceptor
    extends Interceptor
{
    private final int maxRetries;

    /*************************************************************************
     * Creates a new instance of {@code UnitOfWorkInterceptor}.
     */
    public
    UnitOfWorkInterceptor()
    {
        this(1);
    }

    /*************************************************************************
     * Creates a new instance of {@code UnitOfWorkInterceptor} with the
     * specified number of retries.
     */
    public
    UnitOfWorkInterceptor(int max)
    {
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
        if (isAsynchronous(invocation))
            return
                new AsynchronousUnitOfWorkInterceptor(
                    this::doBefore,
                    this::doProceed,
                    this::doAfter,
                    this::onException,
                    maxRetries).invoke(invocation);
        else
            return
                new SynchronousUnitOfWorkInterceptor(
                    this::doBefore,
                    this::doProceed,
                    this::doAfter,
                    this::onException,
                    maxRetries).invoke(invocation);
    }

}

//////////////////////////////////////////////////////////////////////////////
