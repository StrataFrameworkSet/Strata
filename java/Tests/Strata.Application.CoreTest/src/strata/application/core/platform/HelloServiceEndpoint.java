//////////////////////////////////////////////////////////////////////////////
// HelloServiceEndpoint.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.platform;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@Path("hello")
@Tag(name = "HelloService",description = "Service that provides 'hello' greetings.")
public
class HelloServiceEndpoint
{
    private IGreeterService itsImpl;

    public
    HelloServiceEndpoint()
    {
        itsImpl = null;
    }

    @Inject
    public void
    setImplementation(IGreeterService impl) { itsImpl = impl;}

    @Path("say-hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Say hello synchronously")
    @ApiResponse(responseCode = "200", description = "string-based greeting")
    public String
    sayHello()
    {
        return "Hello World!";
    }

    @Path("say-hello-sync")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Say hello synchronously")
    @ApiResponse(
        responseCode = "200",
        description = "json-based greeting",
        content =
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = SayHelloReply.class)))
    @ApiResponse(
        responseCode = "500",
        description = "exception",
        content =
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = SayHelloReply.class)))
    public SayHelloReply
    sayHelloSync(
        @RequestBody(
            description = "request",
            content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = SayHelloRequest.class)))
            SayHelloRequest request)
    {
        return itsImpl.sayHelloSync(request);
    }

    @Path("say-hello-async")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Say hello asynchronously")
    @ApiResponse(
        responseCode = "200",
        description = "json-based greeting",
        content =
        @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = SayHelloReply.class)))
    @ApiResponse(
        responseCode = "500",
        description = "exception",
        content =
            @Content(
                mediaType = MediaType.APPLICATION_JSON,
                schema = @Schema(implementation = SayHelloReply.class)))
    public CompletionStage<SayHelloReply>
    sayHelloAsync(
        @RequestBody(
            description = "request",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = SayHelloRequest.class)))
        SayHelloRequest request)
    {
        return itsImpl.sayHelloAsync(request);
    }
}

//////////////////////////////////////////////////////////////////////////////
