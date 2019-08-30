//////////////////////////////////////////////////////////////////////////////
// Interceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.CompletionStage;

public abstract
class Interceptor
    implements MethodInterceptor
{
    protected
    Interceptor() {}

    @Override
    public Object
    invoke(MethodInvocation invocation)
        throws Throwable
    {
        if (isAsynchronous(invocation))
            return
                new AsynchronousInterceptor(
                    this::doBefore,
                    this::doProceed,
                    this::doAfter,
                    this::onException).invoke(invocation);
        else
            return
                new SynchronousInterceptor(
                    this::doBefore,
                    this::doProceed,
                    this::doAfter,
                    this::onException).invoke(invocation);
    }

    protected boolean
    isAsynchronous(MethodInvocation invocation)
    {
        return
            CompletionStage
                .class
                .isAssignableFrom(
                    invocation
                        .getMethod()
                        .getReturnType());
    }

    protected void
    doBefore(MethodInvocation invocation)
        throws Throwable
    {}

    protected Object
    doProceed(MethodInvocation invocation)
        throws Throwable
    {
        return invocation.proceed();
    }

    protected  void
    doAfter(MethodInvocation invocation)
        throws Throwable
    {}

    protected void
    onException(MethodInvocation invocation,Throwable e)
        throws Throwable
    {}


}

//////////////////////////////////////////////////////////////////////////////
