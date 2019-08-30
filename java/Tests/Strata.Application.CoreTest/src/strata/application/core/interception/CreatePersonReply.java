//////////////////////////////////////////////////////////////////////////////
// CreatePersonReply.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import strata.domain.core.testdomain.IPerson;
import strata.foundation.core.transfer.ServiceReply;

public
class CreatePersonReply
    extends ServiceReply
{
    private IPerson itsNewPerson;
    public
    CreatePersonReply()
    {
        itsNewPerson = null;
    }

    public CreatePersonReply
    setPerson(IPerson newPerson)
    {
        itsNewPerson = newPerson;
        return this;
    }

    public IPerson
    getPerson() { return itsNewPerson; }
}

//////////////////////////////////////////////////////////////////////////////
