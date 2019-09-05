//////////////////////////////////////////////////////////////////////////////
// RestEasyRestClient.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.resteasy.service;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import strata.client.core.service.AbstractRestClient;

public abstract
class RestEasyRestClient
    extends AbstractRestClient
{
    protected
    RestEasyRestClient(String baseUrl)
    {
        super(ResteasyClientBuilder.newBuilder(),baseUrl);
    }
}

//////////////////////////////////////////////////////////////////////////////
