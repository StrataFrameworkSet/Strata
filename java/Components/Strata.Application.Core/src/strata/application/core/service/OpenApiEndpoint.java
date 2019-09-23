//////////////////////////////////////////////////////////////////////////////
// SwaggerEndpoint.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Scanner;

@Path("")
@Tag(name = "OpenApi",description = "Provides openapi documentation.")
public
class OpenApiEndpoint
{
    private final String itsOpenApiJsonUrl;
    private final static String TEMPLATE_PARAMETER = "{openapi.json.url}";

    public OpenApiEndpoint(String openapiJsonUrl)
    {
        itsOpenApiJsonUrl = openapiJsonUrl;
    }

    @Path("openapi")
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Operation(summary = "provide open api documentation")
    public String
    getOpenApi()
    {
        return
            getFile("index.html")
                .replace(TEMPLATE_PARAMETER,itsOpenApiJsonUrl);
    }

    @Path("element/{elementName}")
    @GET
    @Produces(MediaType.WILDCARD)
    @Operation(hidden = true)
    public String
    getElement(@PathParam("elementName") String elementName)
    {
        return getFile(elementName);
    }

    private String
    getFile(String fileName)
    {
        StringBuilder result = new StringBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName);

        if (resource == null)
            return "file not found";

        try (Scanner scanner = new Scanner(resource))
        {

            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                result.append(line);
            }

        }

        return result.toString();

    }
}

//////////////////////////////////////////////////////////////////////////////
