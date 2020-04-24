//////////////////////////////////////////////////////////////////////////////
// IEmailMessageBuilder.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.EmailAddress;

import java.util.List;
import java.util.Map;

public
interface IEmailMessageBuilder
{
    IEmailMessageBuilder
    setSender(EmailAddress sender);

    IEmailMessageBuilder
    setRecipients(List<EmailAddress> recipients);

    IEmailMessageBuilder
    addRecipient(EmailAddress recipient);

    IEmailMessageBuilder
    setSubject(String subject);

    IEmailMessageBuilder
    setContent(String content);

    IEmailMessageBuilder
    setTemplateKey(String templateKey);

    IEmailMessageBuilder
    setParameters(Map<String,String> parameters);

    IEmailMessageBuilder
    addParameter(String parameterKey,String parameterValue);

    IEmailMessage
    build();

    IEmailMessageBuilder
    clear();
}

//////////////////////////////////////////////////////////////////////////////