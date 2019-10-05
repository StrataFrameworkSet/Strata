//////////////////////////////////////////////////////////////////////////////
// ObjectMapperContextResolver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public
class ObjectMapperContextResolver
    implements ContextResolver<ObjectMapper>
{
    private final ObjectMapper mapper;

    public
    ObjectMapperContextResolver()
    {
        mapper = new ObjectMapper();
        mapper
            .registerModule(new SimpleModule())
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        /*
            .setPropertyNamingStrategy(
                new PropertyNamingStrategy.UpperCamelCaseStrategy());
         */
    }

    @Override
    public ObjectMapper
    getContext(Class<?> type) {
        return mapper;
    }
}

//////////////////////////////////////////////////////////////////////////////
