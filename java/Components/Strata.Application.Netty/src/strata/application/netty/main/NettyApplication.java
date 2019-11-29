//////////////////////////////////////////////////////////////////////////////
// VertxApplication.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.netty.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.swagger.v3.oas.models.info.Info;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import strata.application.core.main.AbstractApplication;

import java.util.List;
import java.util.Properties;

public abstract
class NettyApplication
    extends AbstractApplication
{
    private final Injector           itsInjector;
    private final ResteasyDeployment itsDeployment;
    private final NettyJaxrsServer   itsServer;

    protected
    NettyApplication(
        List<Class<?>> endpointClasses,
        Info           info,
        List<Module>   modules,
        String         hostNameKey,
        String         portKey)
    {
        super(endpointClasses,info);

        Properties properties = null;

        itsInjector = Guice.createInjector(modules);
        properties = itsInjector.getInstance(Properties.class);

        if (properties == null)
            throw new NullPointerException("no properties provided.");

        itsDeployment = new ResteasyDeploymentImpl();
        itsServer = new NettyJaxrsServer();

        itsDeployment.start();
        itsDeployment.setApplication(this);

        itsServer.setDeployment(itsDeployment);
        itsServer.setHostname(properties.getProperty(hostNameKey));
        itsServer.setPort(Integer.parseInt(properties.getProperty(portKey)));

        ModuleProcessor processor =
            new ModuleProcessor(
                itsDeployment.getRegistry(),
                itsDeployment.getProviderFactory());

        processor.processInjector(itsInjector);
    }

    protected
    NettyApplication(
        List<Class<?>> endpointClasses,
        Info           info,
        List<Module>   modules,
        String         hostName,
        int            port)
    {
        super(endpointClasses,info);

        itsInjector = Guice.createInjector(modules);
        itsDeployment = new ResteasyDeploymentImpl();
        itsServer = new NettyJaxrsServer();

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
