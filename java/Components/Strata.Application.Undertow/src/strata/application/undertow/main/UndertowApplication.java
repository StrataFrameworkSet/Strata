//////////////////////////////////////////////////////////////////////////////
// VertxApplication.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.undertow.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.swagger.v3.oas.models.info.Info;
import io.undertow.Undertow;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import strata.application.core.main.AbstractApplication;

import java.util.List;

public abstract
class UndertowApplication
    extends AbstractApplication
{
    private final Injector            itsInjector;
    private final ResteasyDeployment  itsDeployment;
    private final Undertow.Builder    itsBuilder;
    private final UndertowJaxrsServer itsServer;

    protected
    UndertowApplication(
        List<Class<?>> endpointClasses,
        Info           info,
        List<Module>   modules,
        String         hostName,
        int            port)
    {
        super(endpointClasses,info);

        itsInjector = Guice.createInjector(modules);
        itsDeployment = new ResteasyDeploymentImpl();
        itsServer = new UndertowJaxrsServer();

        itsDeployment.start();
        itsDeployment.setApplication(this);

        itsBuilder =
            Undertow
                .builder()
                .addHttpListener(port,hostName);

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
        itsServer
            .start(itsBuilder)
            .deploy(itsDeployment);
    }

    public void
    stop()
    {
        itsServer.stop();
    }

}

//////////////////////////////////////////////////////////////////////////////
