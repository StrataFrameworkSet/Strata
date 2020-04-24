//////////////////////////////////////////////////////////////////////////////
// AbstractTextMessage.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.PhoneNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract
class AbstractTextMessage
    implements ITextMessage
{
    private final List<PhoneNumber> itsRecipients;

    protected
    AbstractTextMessage(List<PhoneNumber> recipients)
    {
        if (recipients == null)
            throw
                new NullPointerException("recipients must be provided");

        if (recipients.isEmpty())
            throw
                new IllegalArgumentException("recipients must be non-empty");

        itsRecipients = new ArrayList<>(recipients);
    }

    @Override
    public List<PhoneNumber>
    getRecipients()
    {
        return Collections.unmodifiableList(itsRecipients);
    }
}

//////////////////////////////////////////////////////////////////////////////
