//////////////////////////////////////////////////////////////////////////////
// HelloServiceClient.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.service;

import strata.application.core.platform.SayHelloReply;
import strata.application.core.platform.SayHelloRequest;

import javax.ws.rs.client.ClientBuilder;
import java.util.concurrent.CompletionStage;

public
class HelloServiceClient
    extends    AbstractRestClient
    implements IHelloService
{

    public
    HelloServiceClient(ClientBuilder builder,String baseUrl)
    {
        super(builder,baseUrl,"hello",new StandardResponseProcessor());
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
