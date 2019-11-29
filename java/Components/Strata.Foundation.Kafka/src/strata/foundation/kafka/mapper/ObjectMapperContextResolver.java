//////////////////////////////////////////////////////////////////////////////
// ObjectMapperContextResolver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import strata.foundation.core.mapper.CompletableFutureDeserializer;
import strata.foundation.core.mapper.CompletableFutureSerializer;
import strata.foundation.core.mapper.CompletionStageDeserializer;
import strata.foundation.core.mapper.CompletionStageSerializer;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Provider
public
class ObjectMapperContextResolver
    implements ContextResolver<ObjectMapper>
{
    private final ObjectMapper mapper;

    public ObjectMapperContextResolver()
    {
        mapper = new ObjectMapper();
        mapper
            .registerModule(
                new SimpleModule()
                {
                    @Override
                    public void
                    setupModule(SetupContext context)
                    {
                        super.setupModule(context);
                        context.addBeanSerializerModifier(new AvroSerializerModifier());
                    }
                }
                .addSerializer(
                    new CompletionStageSerializer())
                .addSerializer(
                    new CompletableFutureSerializer())
                .addDeserializer(
                    CompletionStage.class,
                    new CompletionStageDeserializer())
                .addDeserializer(
                    CompletableFuture.class,
                    new CompletableFutureDeserializer()))
            .registerModule(new Jdk8Module())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setVisibility(
                PropertyAccessor.FIELD,
                JsonAutoDetect.Visibility.ANY);
    }

    @Override
    public ObjectMapper
    getContext(Class<?> type) {
        return mapper;
    }
}

//////////////////////////////////////////////////////////////////////////////
