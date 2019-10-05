//////////////////////////////////////////////////////////////////////////////
// StrataObjectMapperProcessor.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.integration.api.ObjectMapperProcessor;
import org.apache.avro.specific.SpecificRecordBase;
import strata.foundation.core.mapper.IExcludeAvroFieldsMixin;

public
class StrataObjectMapperProcessor
    implements ObjectMapperProcessor
{
    @Override
    public void
    processJsonObjectMapper(ObjectMapper mapper)
    {
        mapper
            .registerModule(new SimpleModule())
            .registerModule(new JavaTimeModule())
            .addMixIn(SpecificRecordBase.class,IExcludeAvroFieldsMixin.class)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public void
    processYamlObjectMapper(ObjectMapper mapper) {}
}

//////////////////////////////////////////////////////////////////////////////
