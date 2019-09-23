//////////////////////////////////////////////////////////////////////////////
// SayHelloReply.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.platform;

import strata.foundation.core.transfer.ServiceReply;

public
class SayHelloReply
    extends ServiceReply
{
    private String greeting;

    public
    SayHelloReply()
    {
        greeting = null;
    }

    public SayHelloReply
    setGreeting(String g)
    {
        greeting = g;
        return this;
    }

    public String
    getGreeting()
    {
        return greeting;
    }

}

//////////////////////////////////////////////////////////////////////////////
