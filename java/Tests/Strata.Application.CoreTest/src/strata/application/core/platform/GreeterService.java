//////////////////////////////////////////////////////////////////////////////
// GreeterService.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.platform;

import strata.application.core.interception.Timed;
import strata.foundation.core.transfer.ExceptionData;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public
class GreeterService
    implements IGreeterService
{
    @Override
    @Timed
    public SayHelloReply
    sayHelloSync(SayHelloRequest request)
    {
        if (!request.getGreeting().isEmpty())
            return
                (SayHelloReply)
                    new SayHelloReply()
                        .setGreeting(
                            request.getGreeting() + " " +
                                request.getUser())
                        .setSuccess(true);

        return
            (SayHelloReply)
                new SayHelloReply()
                    .setSuccess(false)
                    .setFailureMessage("no greeting provided")
                    .setException(
                        new ExceptionData()
                            .setExceptionType(
                                IllegalStateException.class.getCanonicalName())
                            .setExceptionCode(-1)
                            .setExceptionMessage("ino greeting provided")
                            .setCause(
                                new ExceptionData()
                                    .setExceptionType(
                                        NoSuchElementException.class.getCanonicalName())
                                    .setExceptionCode(-2)
                                    .setExceptionMessage("no such field: greeting")));
    }

    @Override
    @Timed
    public CompletionStage<SayHelloReply>
    sayHelloAsync(SayHelloRequest request)
    {
        if (!request.getGreeting().isEmpty())
            return
                CompletableFuture
                    .supplyAsync(
                        () ->
                            (SayHelloReply)
                                new SayHelloReply()
                                    .setGreeting(
                                        request.getGreeting() + " " +
                                            request.getUser())
                                    .setSuccess(true));

        return
            CompletableFuture
                .supplyAsync(
                    () ->
                        (SayHelloReply)
                            new SayHelloReply()
                                .setSuccess(false)
                                .setFailureMessage("No greeting provided"));

    }
}

//////////////////////////////////////////////////////////////////////////////
