//////////////////////////////////////////////////////////////////////////////
// CorsReplyFilter.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.filter;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.container.PreMatching;

@PreMatching
public
class CorsRequestAndReplyFilter
    extends CorsFilter
{
    public CorsRequestAndReplyFilter()
    {
        getAllowedOrigins().add("*");
        setAllowedMethods("GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}

//////////////////////////////////////////////////////////////////////////////
