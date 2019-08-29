//////////////////////////////////////////////////////////////////////////////
// Interceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public abstract
class OldInterceptor
    implements MethodInterceptor
{
    protected OldInterceptor() {}

    @Override
    public Object
    invoke(MethodInvocation invocation)
        throws Throwable
    {
        try
        {
            doBefore(invocation);
            return doProceed(invocation);
        }
        catch (Throwable e)
        {
            onException(invocation,e);
            throw e;
        }
        finally
        {
            doAfter(invocation);
        }
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
