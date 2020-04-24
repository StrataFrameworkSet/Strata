//////////////////////////////////////////////////////////////////////////////
// SimpleEmailMessage.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.EmailAddress;

import java.util.List;

public
class SimpleEmailMessage
    extends AbstractEmailMessage
{
    private final String itsContent;

    public
    SimpleEmailMessage(
        EmailAddress sender,
        List<EmailAddress> recipients,
        String subject,
        String content)
    {
        super(sender,recipients,subject);
        itsContent = content;
    }

    @Override
    public String
    getContent()
    {
        return itsContent;
    }
}

//////////////////////////////////////////////////////////////////////////////
