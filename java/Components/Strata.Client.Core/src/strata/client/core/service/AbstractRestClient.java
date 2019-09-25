//////////////////////////////////////////////////////////////////////////////
// AbstractRestClient.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.service;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public abstract
class AbstractRestClient
{
    private Client             itsClient;
    private WebTarget          itsBaseTarget;
    private IResponseProcessor itsResponseProcessor;

    protected
    AbstractRestClient(
        ClientBuilder      builder,
        String             baseUrl,
        String             endpointPath,
        IResponseProcessor processor)
    {
        itsClient =
                builder
                    .register(new ObjectMapperProvider())
                    .build();

        itsBaseTarget = itsClient.target(initialize(baseUrl,endpointPath));
        itsResponseProcessor = processor;
    }

    protected
    AbstractRestClient(
        Client             client,
        String             baseUrl,
        IResponseProcessor processor)
    {
        itsClient = client;
        itsBaseTarget = itsClient.target(baseUrl);
        itsResponseProcessor = processor;
    }

    protected <Request,Reply> Reply
    doPost(String methodPath,Class<Reply> replyType,Request request)
    {
        return
            itsResponseProcessor.process(
                replyType,
                toResponse(
                    methodPath,
                    buildRequest(methodPath)
                        .post(
                            Entity.entity(
                                request,
                                MediaType.APPLICATION_JSON))));
    }

    protected <Request,Reply> CompletionStage<Reply>
    doPostAsync(String methodPath,Class<Reply> replyType,Request request)
    {
        return
            buildRequest(methodPath)
                .rx()
                .post(
                    Entity.entity(
                        request,
                        MediaType.APPLICATION_JSON))
                    .thenApply(
                        response ->
                            itsResponseProcessor.process(
                                replyType,
                                toResponse(methodPath,response)));
    }

    protected <Request,Reply> Reply
    doPut(String methodPath,Class<Reply> replyType,Request request)
    {
        return
            itsResponseProcessor.process(
                replyType,
                toResponse(
                    methodPath,
                    buildRequest(methodPath)
                        .put(
                            Entity.entity(
                                request,
                                MediaType.APPLICATION_JSON))));
    }

    protected <Request,Reply> Reply
    doDelete(String methodPath,Class<Reply> replyType,Request request)
    {
        return
            itsResponseProcessor.process(
                replyType,
                toResponse(
                    methodPath,
                    buildRequest(methodPath)
                        .build(
                        "DELETE",
                            Entity.entity(request,MediaType.APPLICATION_JSON))
                        .invoke()));
    }

    protected <Request,Reply> Reply
    doGet(String methodPath,Class<Reply> replyType,Map<String,Object> params)
    {
        WebTarget target = itsBaseTarget.path(methodPath);

        for (Map.Entry<String,Object> param:params.entrySet())
            target.queryParam(param.getKey(),param.getValue());

        return
            itsResponseProcessor.process(
                replyType,
                toResponse(
                    methodPath,
                    target
                        .request(MediaType.APPLICATION_JSON)
                        .buildGet()
                        .invoke()));
    }

    private Invocation.Builder
    buildRequest(String path)
    {
        return
            itsBaseTarget
                .path(path)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    private static String
    initialize(String baseUrl,String endpointPath)
    {
        return
            baseUrl.endsWith("/" +endpointPath)
                ? baseUrl
                : baseUrl + "/" + endpointPath;
    }

    private IResponse
    toResponse(String methodPath,Response response)
    {
        return
            new StandardResponse(
                itsBaseTarget.getUri().getPath(),
                methodPath,
                response);
    }
}

//////////////////////////////////////////////////////////////////////////////
