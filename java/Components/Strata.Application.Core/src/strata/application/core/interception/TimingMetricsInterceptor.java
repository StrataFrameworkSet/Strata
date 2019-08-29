//////////////////////////////////////////////////////////////////////////////
// TimingMetricsInterceptor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInvocation;

import java.time.Duration;
import java.time.Instant;

/*****************************************************************************
 * Intercepts method calls and records the start and finish times to
 * capture metrics on code performance.
 */
public
class TimingMetricsInterceptor
    extends Interceptor
{
    private Instant start;
    private Instant finish;

    /*************************************************************************
     *
     */
    public
    TimingMetricsInterceptor() {}

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    protected void
    doBefore(MethodInvocation invocation) throws Throwable
    {
        start = Instant.now();
        super.doBefore(invocation);
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    protected void
    doAfter(MethodInvocation invocation) throws Throwable
    {
        super.doAfter(invocation);
        finish = Instant.now();

        System.out.println(
            invocation.getMethod().getName() +
                " total execution time = " +
                Duration.between(start,finish).toMillis() +
                " ms");
        System.out.flush();
    }

    /*************************************************************************
     * {@inheritDoc}
     */
    @Override
    protected void
    onException(MethodInvocation invocation,Throwable e) throws Throwable
    {
        super.onException(invocation,e);
    }
}

//////////////////////////////////////////////////////////////////////////////
