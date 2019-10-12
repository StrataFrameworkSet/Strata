//////////////////////////////////////////////////////////////////////////////
// PropertiesModule.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.inject;

import com.google.inject.Scopes;

import java.util.Properties;

public
class PropertiesModule
    extends AbstractModule
{
    @Override
    protected void
    configure()
    {
        bind(Properties.class)
            .toProvider(() -> getProperties())
            .in(Scopes.SINGLETON);
    }

    private static Properties
    getProperties()
    {
        Properties properties = new Properties();

        try
        {
            properties.load(
                ClassLoader.getSystemResourceAsStream(
                    getEnvironment() + ".properties"));

            return properties;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String
    getEnvironment()
    {
        return
            System.getProperty("DEPLOY_ENV") != null
                ? System.getProperty("DEPLOY_ENV")
                : "development";
    }

}

//////////////////////////////////////////////////////////////////////////////
