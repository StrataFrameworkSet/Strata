//////////////////////////////////////////////////////////////////////////////
// MultiCauseException.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.exception;

import java.util.ArrayList;
import java.util.List;

public
class MultiCauseException
    extends Exception
{
    private List<Exception> itsCauses;

    public
    MultiCauseException()
    {
        this(
            "This exception is an aggregate of multiple exceptions",
            new ArrayList<>());
    }

    public
    MultiCauseException(List<Exception> causes)
    {
        this(
            "This exception is an aggregate of multiple exceptions",
            causes);
    }

    public
    MultiCauseException(String message,List<Exception> causes)
    {
        super(message);
        itsCauses = causes;
    }
}

//////////////////////////////////////////////////////////////////////////////
