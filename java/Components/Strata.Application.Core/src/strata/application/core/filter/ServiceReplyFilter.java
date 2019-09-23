//////////////////////////////////////////////////////////////////////////////
// ServiceReplyFilter.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.filter;

import strata.foundation.core.transfer.ServiceReply;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Provider
public
class ServiceReplyFilter
    implements ContainerResponseFilter
{
    @Override
    public void
    filter(
        ContainerRequestContext  requestContext,
        ContainerResponseContext responseContext)
    {
        Object entity = responseContext.getEntity();

        if (entity instanceof ServiceReply)
        {
            ServiceReply reply = (ServiceReply)entity;

            if (reply.isSuccess() == false)
                responseContext.setStatus(
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        else if (entity instanceof CompletionStage<?>)
        {
            CompletableFuture<?> future =
                ((CompletionStage)entity).toCompletableFuture();

            if (future.isDone())
            {
                Object target = future.join();

                if (target instanceof ServiceReply)
                {
                    ServiceReply reply = (ServiceReply)target;

                    if (reply.isSuccess() == false)
                        responseContext.setStatus(
                            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
                }
            }
        }
    }
}

//////////////////////////////////////////////////////////////////////////////
