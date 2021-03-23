//////////////////////////////////////////////////////////////////////////////
// AbstractApplication.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.main;

import io.swagger.v3.jaxrs2.SwaggerSerializers;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import strata.application.core.filter.CorsRequestAndReplyFilter;
import strata.application.core.filter.ServiceReplyFilter;
import strata.application.core.mapper.ObjectMapperProvider;
import strata.application.core.mapper.StrataObjectMapperProcessor;

import javax.ws.rs.core.Application;
import java.util.*;

public abstract
class AbstractApplication
    extends Application
{
    protected final List<Class<?>> itsEndpointClasses;
    protected final Info           itsInfo;

    protected
    AbstractApplication(Info info)
    {
        this(new ArrayList<>(),info);
    }

    protected
    AbstractApplication(
        List<Class<?>> endpointClasses,
        Info           info)
    {
        itsEndpointClasses = endpointClasses;
        itsInfo = info;
        configureSwagger();
    }

    @Override
    public Set<Class<?>>
    getClasses()
    {
        Set<Class<?>> classes =
            new HashSet<>(
                Arrays.asList(
                    OpenApiResource.class,
                    SwaggerSerializers.class,
                    ServiceReplyFilter.class,
                    CorsRequestAndReplyFilter.class,
                    getObjectMapperProviderType()));

        classes.addAll(itsEndpointClasses);

        return classes;
    }

    /*
    @Override
    public Set<Object>
    getSingletons()
    {
        Set<Object> singletons = new HashSet<>(super.getSingletons());
        CorsFilter corsFilter = new CorsFilter();

        corsFilter.getAllowedOrigins().add("*");
        singletons.add(corsFilter);

        return singletons;
    }
     */

    protected void
    configureSwagger()
    {
        SwaggerConfiguration swagger =
            new SwaggerConfiguration()
                .openAPI(new OpenAPI().info(itsInfo))
                .prettyPrint(true)
                .readAllResources(false);

        swagger.setObjectMapperProcessorClass(
            StrataObjectMapperProcessor
                .class
                .getCanonicalName());

        try
        {
            new JaxrsOpenApiContextBuilder<>()
                .application(this)
                .openApiConfiguration(swagger)
                .buildContext(true);
        }
        catch (OpenApiConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    protected Class<?>
    getObjectMapperProviderType()
    {
        return ObjectMapperProvider.class;
    }

}

//////////////////////////////////////////////////////////////////////////////
