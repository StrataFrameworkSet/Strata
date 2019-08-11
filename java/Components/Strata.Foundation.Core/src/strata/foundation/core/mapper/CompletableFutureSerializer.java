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

public
class CompletableFutureSerializer
    extends JsonSerializer<CompletableFuture<?>>
{

    public
    CompletableFutureSerializer() {}

    @Override
    public Class<CompletableFuture<?>>
    handledType()
    {
        return
            (Class<CompletableFuture<?>>)
                (Type)CompletableFuture.class;
    }

    @Override
    public void
    serialize(
        CompletableFuture<?> value,
        JsonGenerator        generator,
        SerializerProvider   provider)
        throws IOException
    {
        Object subject = value.join();

        generator.writeStartObject();
        generator.writeObjectField("subject",subject);
        generator.writeEndObject();
    }
}

//////////////////////////////////////////////////////////////////////////////
