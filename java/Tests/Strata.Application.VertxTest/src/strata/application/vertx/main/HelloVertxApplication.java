//////////////////////////////////////////////////////////////////////////////
// HelloVertxApplication.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.vertx.main;

import io.swagger.v3.oas.models.info.Info;
import strata.application.core.platform.HelloModule;
import strata.application.core.platform.HelloOpenApiEndpoint;
import strata.application.core.platform.HelloServiceEndpoint;

import java.util.Arrays;

public
class HelloVertxApplication
    extends VertxApplication
{

    public
    HelloVertxApplication()
    {
        super(
            Arrays.asList(
                HelloOpenApiEndpoint.class,
                HelloServiceEndpoint.class),
            new Info()
                .description("Strata Hello World")
                .title("Strata Hello World")
                .version("1.0"),
            Arrays.asList(new HelloModule()),
            "localhost",
            8092);
    }

    public static void
    main(String[] args)
    {
        new HelloVertxApplication().run();
    }
}

//////////////////////////////////////////////////////////////////////////////
