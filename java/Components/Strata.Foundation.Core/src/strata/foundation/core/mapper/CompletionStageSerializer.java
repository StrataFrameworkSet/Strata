//////////////////////////////////////////////////////////////////////////////
// CompletableFutureSerializer.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public
class CompletionStageSerializer
    extends JsonSerializer<CompletionStage<?>>
{

    public CompletionStageSerializer() {}

    @Override
    public Class<CompletionStage<?>>
    handledType()
    {
        return
            (Class<CompletionStage<?>>)
                (Type)CompletableFuture.class;
    }

    @Override
    public void
    serialize(
        CompletionStage<?> value,
        JsonGenerator      generator,
        SerializerProvider provider)
        throws IOException
    {
        Object subject = value.toCompletableFuture().join();

        generator.writeStartObject();
        generator.writeObjectField("subject",subject);
        generator.writeEndObject();
    }
}

//////////////////////////////////////////////////////////////////////////////
