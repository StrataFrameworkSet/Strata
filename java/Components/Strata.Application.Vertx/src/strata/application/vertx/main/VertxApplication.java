//////////////////////////////////////////////////////////////////////////////
// VertxApplication.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.vertx.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.swagger.v3.oas.models.info.Info;
import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.vertx.VertxJaxrsServer;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;
import strata.application.core.main.AbstractApplication;

import java.util.List;

public abstract
class VertxApplication
    extends AbstractApplication
{
    private final Injector                itsInjector;
    private final VertxResteasyDeployment itsDeployment;
    private final VertxJaxrsServer        itsServer;

    protected
    VertxApplication(
        List<Class<?>> endpointClasses,
        Info           info,
        List<Module>   modules,
        String         hostName,
        int            port)
    {
        super(endpointClasses,info);

        itsInjector = Guice.createInjector(modules);
        itsDeployment = new VertxResteasyDeployment();
        itsServer = new VertxJaxrsServer();

        itsDeployment.start();
        itsDeployment.setApplication(this);

        itsServer.setDeployment(itsDeployment);
        itsServer.setHostname(hostName);
        itsServer.setPort(port);

        ModuleProcessor processor =
            new ModuleProcessor(
                itsDeployment.getRegistry(),
                itsDeployment.getProviderFactory());

        processor.processInjector(itsInjector);
    }

    protected Injector
    getInjector() { return itsInjector; }

    public void
    start()
    {
        itsServer.start();
    }

    public void
    stop()
    {
        itsServer.stop();
    }

}

//////////////////////////////////////////////////////////////////////////////
