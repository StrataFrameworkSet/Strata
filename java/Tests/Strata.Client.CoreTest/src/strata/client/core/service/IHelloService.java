//////////////////////////////////////////////////////////////////////////////
// IHelloService.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.service;

import strata.application.core.platform.SayHelloReply;
import strata.application.core.platform.SayHelloRequest;

import java.util.concurrent.CompletionStage;

public
interface IHelloService
{
    SayHelloReply
    sayHelloSync(SayHelloRequest request);

    CompletionStage<SayHelloReply>
    sayHelloAsync(SayHelloRequest request);
}

//////////////////////////////////////////////////////////////////////////////
