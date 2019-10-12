//////////////////////////////////////////////////////////////////////////////
// PropertiesBasedOpenApiUrlProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import javax.inject.Inject;
import java.util.Properties;

public
class PropertiesBasedOpenApiUrlProvider
    implements IOpenApiUrlProvider
{
    private final Properties itsProperties;
    private final String     itsOpenApiUrlKey;

    @Inject
    public
    PropertiesBasedOpenApiUrlProvider(Properties properties)
    {
        this(properties,"openapi.json.url");
    }

    public
    PropertiesBasedOpenApiUrlProvider(
        Properties properties,
        String     openApiUrlKey)
    {
        itsProperties = properties;
        itsOpenApiUrlKey = openApiUrlKey;

        if (!itsProperties.containsKey(itsOpenApiUrlKey))
            throw
                new IllegalArgumentException(
                    "properties does not contain: " + itsOpenApiUrlKey);
    }

    @Override
    public String
    get()
    {
        return itsProperties.getProperty(itsOpenApiUrlKey);
    }
}

//////////////////////////////////////////////////////////////////////////////
