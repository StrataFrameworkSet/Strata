//////////////////////////////////////////////////////////////////////////////
// ObjectMapperProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.resteasy.service;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import strata.foundation.core.mapper.ObjectMapperContextResolver;

public
class ObjectMapperProvider
    extends ResteasyJackson2Provider
{

    public
    ObjectMapperProvider()
    {
        setMapper(new ObjectMapperContextResolver().getContext(Object.class));
    }

}

//////////////////////////////////////////////////////////////////////////////
