//////////////////////////////////////////////////////////////////////////////
// RestEasyRestClient.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.resteasy.service;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import strata.client.core.service.AbstractRestClient;
import strata.client.core.service.IResponseProcessor;

public abstract
class RestEasyRestClient
    extends AbstractRestClient
{
    protected
    RestEasyRestClient(
        String             baseUrl,
        String             endpointPath)
    {
        super(
            ResteasyClientBuilder.newBuilder(),
            baseUrl,
            endpointPath);
    }

    protected
    RestEasyRestClient(
        String             baseUrl,
        String             endpointPath,
        IResponseProcessor processor)
    {
        super(
            ResteasyClientBuilder.newBuilder(),
            baseUrl,
            endpointPath,
            processor);
    }
}

//////////////////////////////////////////////////////////////////////////////
