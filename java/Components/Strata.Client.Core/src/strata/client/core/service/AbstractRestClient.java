//////////////////////////////////////////////////////////////////////////////
// AbstractRestClient.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public abstract
class AbstractRestClient
{
    private Client         itsClient;
    private WebTarget      itsBaseTarget;
    private ObjectMapper   itsMapper;

    protected
    AbstractRestClient(ClientBuilder builder,String baseUrl)
    {
        itsClient =
                builder
                    .register(new ObjectMapperProvider())
                    .build();

        itsBaseTarget = itsClient.target(baseUrl);
    }

    protected <Request,Reply> Reply
    doPost(String path,Class<Reply> replyType,Request request)
    {
        Response response =
            buildRequest(path)
                .post(Entity.entity(request,MediaType.APPLICATION_JSON));

        switch (response.getStatusInfo().toEnum())
        {
            case OK:
            case INTERNAL_SERVER_ERROR:
                return
                    response
                        .readEntity(replyType);
            default:
                throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
        }
    }

    protected <Request,Reply> CompletionStage<Reply>
    doPostAsync(String path,Class<Reply> replyType,Request request)
    {
        return
            buildRequest(path)
                .rx()
                .post(
                    Entity.entity(
                        request,
                        MediaType.APPLICATION_JSON))
                    .thenApply(
                        response ->
                            response.readEntity(replyType));
    }

    protected <Request,Reply> Reply
    doPut(String path,Class<Reply> replyType,Request request)
    {
        return
            buildRequest(path)
                .put(Entity.entity(request,MediaType.APPLICATION_JSON))
                .readEntity(replyType);
    }

    protected <Request,Reply> Reply
    doDelete(String path,Class<Reply> replyType,Request request)
    {
        return
            buildRequest(path)
                .build(
                    "DELETE",
                    Entity.entity(request,MediaType.APPLICATION_JSON))
                .invoke()
                .readEntity(replyType);
    }

    protected <Request,Reply> Reply
    doGet(String path,Class<Reply> replyType,Map<String,Object> params)
    {
        WebTarget target = itsBaseTarget.path(path);

        for (Map.Entry<String,Object> param:params.entrySet())
            target.queryParam(param.getKey(),param.getValue());

        return
            target
                .request(MediaType.APPLICATION_JSON)
                .get(replyType);
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

}

//////////////////////////////////////////////////////////////////////////////
