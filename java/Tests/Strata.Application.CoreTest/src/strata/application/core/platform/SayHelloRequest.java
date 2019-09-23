//////////////////////////////////////////////////////////////////////////////
// SayHelloRequest.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.platform;

import strata.foundation.core.transfer.ServiceRequest;

public
class SayHelloRequest
    extends ServiceRequest
{
    private String user;
    private String greeting;

    public
    SayHelloRequest()
    {
        user = null;
        greeting = null;
    }

    public SayHelloRequest
    setUser(String u)
    {
        user = u;
        return this;
    }

    public SayHelloRequest
    setGreeting(String g)
    {
        greeting = g;
        return this;
    }

    public String
    getUser()
    {
        return user;
    }

    public String
    getGreeting()
    {
        return greeting;
    }
}

//////////////////////////////////////////////////////////////////////////////
