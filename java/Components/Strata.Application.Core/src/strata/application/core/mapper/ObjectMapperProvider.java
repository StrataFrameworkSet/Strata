//////////////////////////////////////////////////////////////////////////////
// ObjectMapperProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import strata.foundation.core.mapper.ObjectMapperContextResolver;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public
class ObjectMapperProvider
    extends ResteasyJackson2Provider
{
    private final ObjectMapper itsMapper;

    public
    ObjectMapperProvider()
    {
        itsMapper =
            new ObjectMapperContextResolver()
                .getContext(null);
    }

    @Override
    protected ObjectMapper
    _locateMapperViaProvider(Class<?> type,MediaType mediaType)
    {
        return itsMapper;
    }
}

//////////////////////////////////////////////////////////////////////////////
