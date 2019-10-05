//////////////////////////////////////////////////////////////////////////////
// HelloOpenApiEndpoint.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.platform;

import io.swagger.v3.oas.annotations.tags.Tag;
import strata.application.core.service.OpenApiEndpoint;

@Tag(name = "OpenApi",description = "Provides access to openapi documentation.")
public
class HelloOpenApiEndpoint
    extends OpenApiEndpoint
{
    public
    HelloOpenApiEndpoint()
    {
        super("http://localhost:8092/openapi.json");
    }
}

//////////////////////////////////////////////////////////////////////////////
