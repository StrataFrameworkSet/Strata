//////////////////////////////////////////////////////////////////////////////
// Interceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public abstract
class BaseInterceptor
    implements MethodInterceptor
{
    private final IConsumer<MethodInvocation>             itsBefore;
    private final IFunction<MethodInvocation,?>           itsTarget;
    private final IConsumer<MethodInvocation>             itsAfter;
    private final IBiConsumer<MethodInvocation,Throwable> itsException;

    protected
    BaseInterceptor(
        IConsumer<MethodInvocation>             before,
        IFunction<MethodInvocation,?>           target,
        IConsumer<MethodInvocation>             after,
        IBiConsumer<MethodInvocation,Throwable> exception)
    {
        itsBefore    = before;
        itsTarget    = target;
        itsAfter     = after;
        itsException = exception;
    }

    protected void
    doBefore(MethodInvocation invocation)
        throws Throwable
    {
        itsBefore.accept(invocation);
    }

    protected Object
    doProceed(MethodInvocation invocation)
        throws Throwable
    {
        return itsTarget.apply(invocation);
    }

    protected  void
    doAfter(MethodInvocation invocation)
        throws Throwable
    {
        itsAfter.accept(invocation);
    }

    protected void
    onException(MethodInvocation invocation,Throwable e)
        throws Throwable
    {
        itsException.accept(invocation,e);
    }
}

//////////////////////////////////////////////////////////////////////////////
