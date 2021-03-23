//////////////////////////////////////////////////////////////////////////////
// HelloVertxApplication.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.netty.main;

import io.swagger.v3.oas.models.info.Info;
import strata.application.core.platform.HelloModule;

import java.util.ArrayList;
import java.util.Arrays;

public
class HelloNettyApplication
    extends NettyApplication
{

    public HelloNettyApplication()
    {
        super(
            new ArrayList<>(),
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
        new HelloNettyApplication().start();
    }
}

//////////////////////////////////////////////////////////////////////////////
