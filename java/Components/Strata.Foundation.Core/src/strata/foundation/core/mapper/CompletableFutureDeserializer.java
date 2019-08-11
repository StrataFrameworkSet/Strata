//////////////////////////////////////////////////////////////////////////////
// CompletableFutureDeserializer.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public
class CompletableFutureDeserializer
    extends JsonDeserializer<CompletableFuture<?>>
    implements ContextualDeserializer
{
    private JavaType itsValueType;

    public
    CompletableFutureDeserializer()
    {
        itsValueType = null;
    }

    public
    CompletableFutureDeserializer(JavaType valueType)
    {
        itsValueType = valueType;
    }

    @Override
    public JsonDeserializer<?>
    createContextual(DeserializationContext context,BeanProperty property)
    {
        JavaType valueType = null;

        if (property != null)
            valueType = property.getType().containedType(0);
        else
            valueType = context.getContextualType().containedType(0);

        return new CompletableFutureDeserializer(valueType);
    }

    @Override
    public CompletableFuture<?>
    deserialize(
        JsonParser             parser,
        DeserializationContext context)
            throws IOException
    {
        CompletableFuture<Object> future = new CompletableFuture<>();
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode subject = node.get("subject");

        future.complete(
            parser
                .getCodec()
                .treeToValue(subject,itsValueType.getRawClass()));

        return future;
    }
}

//////////////////////////////////////////////////////////////////////////////
