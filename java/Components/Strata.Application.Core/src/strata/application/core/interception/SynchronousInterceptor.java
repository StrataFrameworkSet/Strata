//////////////////////////////////////////////////////////////////////////////
// Interceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public
class SynchronousInterceptor
    extends    BaseInterceptor
    implements MethodInterceptor
{
    protected
    SynchronousInterceptor(
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
}

//////////////////////////////////////////////////////////////////////////////
