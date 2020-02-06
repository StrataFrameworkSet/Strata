//////////////////////////////////////////////////////////////////////////////
// PropertiesBasedSessionFactoryProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.hibernate.inject;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Properties;

public
class PropertiesBasedSessionFactoryProvider
    implements ISessionFactoryProvider
{
    private final Properties      itsProperties;
    private final List<String>    itsResources;
    private static SessionFactory theirFactory = null;

    @Inject
    public
    PropertiesBasedSessionFactoryProvider(
        Properties            properties,
        IResourceListProvider resources)
    {
        this(properties,resources.get());
    }

    public
    PropertiesBasedSessionFactoryProvider(
        Provider<Properties> properties,
        List<String>         resources)
    {
        this(properties.get(),resources);
    }

    public
    PropertiesBasedSessionFactoryProvider(
        Properties   properties,
        List<String> resources)
    {
        itsProperties = properties;
        itsResources  = resources;
    }

    @Override
    public synchronized SessionFactory
    get()
    {
        if (theirFactory == null)
            theirFactory = createConfiguration().buildSessionFactory();

        return theirFactory;
    }

    protected Configuration
    createConfiguration()
    {
        Configuration configuration =
            new Configuration()
                .setProperties(itsProperties);

        itsResources
            .stream()
            .forEach(resource -> configuration.addResource(resource));

        return configuration;
    }
}

//////////////////////////////////////////////////////////////////////////////
