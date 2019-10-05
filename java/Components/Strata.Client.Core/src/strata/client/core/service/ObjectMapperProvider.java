//////////////////////////////////////////////////////////////////////////////
// ObjectMapperProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public
class ObjectMapperProvider
    extends JacksonJsonProvider
{
    public
    ObjectMapperProvider()
    {
        super(
            new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .registerModule(new SimpleModule()));
    }
}

//////////////////////////////////////////////////////////////////////////////
