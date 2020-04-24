//////////////////////////////////////////////////////////////////////////////
// AbstractEmailMessage.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.EmailAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract
class AbstractEmailMessage
    implements IEmailMessage
{
    private final EmailAddress       itsSender;
    private final List<EmailAddress> itsRecipients;
    private final String             itsSubject;

    protected
    AbstractEmailMessage(
        EmailAddress       sender,
        List<EmailAddress> recipients,
        String             subject)
    {
        if (sender == null)
            throw new NullPointerException("sender must be provided");

        if (recipients == null)
            throw
                new NullPointerException("recipients must be provided");

        if (recipients.isEmpty())
            throw
                new IllegalArgumentException("recipients must be non-empty");

        itsSender     = sender;
        itsRecipients = new ArrayList<>(recipients);
        itsSubject    = subject;
    }

    @Override
    public EmailAddress
    getSender()
    {
        return itsSender;
    }

    @Override
    public List<EmailAddress>
    getRecipients()
    {
        return Collections.unmodifiableList(itsRecipients);
    }

    @Override
    public String
    getSubject()
    {
        return itsSubject;
    }
}

//////////////////////////////////////////////////////////////////////////////
