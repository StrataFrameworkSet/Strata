//////////////////////////////////////////////////////////////////////////////
// HelloModule.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.platform;

import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import strata.application.core.interception.Timed;
import strata.application.core.interception.TimingMetricsInterceptor;
import strata.foundation.core.inject.AbstractModule;

public
class HelloModule
    extends AbstractModule
{
    public
    HelloModule()
    {
        setDefaultScope(Scopes.SINGLETON);
    }

    @Override
    protected void
    configure()
    {
        bind(IGreeterService.class)
            .to(GreeterService.class)
            .in(getDefaultScope());

        bind(HelloOpenApiEndpoint.class)
            .in(getDefaultScope());

        bind(HelloServiceEndpoint.class)
            .in(getDefaultScope());

        bindInterceptor(
            Matchers.any(),
            Matchers.annotatedWith(Timed.class),
            new TimingMetricsInterceptor());

    }
}

//////////////////////////////////////////////////////////////////////////////
