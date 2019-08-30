//////////////////////////////////////////////////////////////////////////////
// CreatePersonRequest.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import strata.domain.core.testdomain.PersonAge;
import strata.domain.core.testdomain.PersonName;
import strata.foundation.core.transfer.ServiceRequest;

public
class CreatePersonRequest
    extends ServiceRequest
{
    private PersonName itsName;
    private PersonAge  itsAge;

    public
    CreatePersonRequest()
    {
        itsName = null;
        itsAge  = null;
    }

    public CreatePersonRequest
    setName(PersonName name)
    {
        itsName = name;
        return this;
    }

    public CreatePersonRequest
    setAge(PersonAge age)
    {
        itsAge = age;
        return this;
    }

    public PersonName
    getName() { return itsName; }

    public PersonAge
    getAge() { return itsAge; }
}


//////////////////////////////////////////////////////////////////////////////
