//////////////////////////////////////////////////////////////////////////////
// HelloServiceRestEasyClientTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.resteasy.service;

import strata.client.core.service.HelloServiceClientTest;
import strata.client.core.service.IHelloService;

public
class HelloServiceRestEasyClientTest
    extends HelloServiceClientTest
{
    @Override
    protected IHelloService
    createTarget()
    {
        return new HelloServiceRestEasyClient("http://localhost:8092");
    }
}

//////////////////////////////////////////////////////////////////////////////
