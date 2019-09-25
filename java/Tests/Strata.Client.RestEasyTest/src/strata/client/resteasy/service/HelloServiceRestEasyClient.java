//////////////////////////////////////////////////////////////////////////////
// HelloServiceClient.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.resteasy.service;

import strata.application.core.platform.SayHelloReply;
import strata.application.core.platform.SayHelloRequest;
import strata.client.core.service.IHelloService;

import java.util.concurrent.CompletionStage;

public
class HelloServiceRestEasyClient
    extends    RestEasyRestClient
    implements IHelloService
{

    public
    HelloServiceRestEasyClient(String baseUrl)
    {
        super(baseUrl,"hello");
    }

    @Override
    public SayHelloReply
    sayHelloSync(SayHelloRequest request)
    {
        return
            doPost("say-hello-sync",SayHelloReply.class,request);
    }

    @Override
    public CompletionStage<SayHelloReply>
    sayHelloAsync(SayHelloRequest request)
    {
        return
            doPostAsync("say-hello-async",SayHelloReply.class,request);
    }
}

//////////////////////////////////////////////////////////////////////////////
