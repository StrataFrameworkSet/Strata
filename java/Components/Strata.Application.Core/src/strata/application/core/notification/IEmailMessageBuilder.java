//////////////////////////////////////////////////////////////////////////////
// IEmailMessageBuilder.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.EmailAddress;

import java.util.Map;
import java.util.Set;

public
interface IEmailMessageBuilder
{
    IEmailMessageBuilder
    setSender(EmailAddress sender);

    IEmailMessageBuilder
    setRecipients(Set<EmailAddress> recipients);

    IEmailMessageBuilder
    addRecipient(EmailAddress recipient);

    IEmailMessageBuilder
    setSubject(String subject);

    IEmailMessageBuilder
    setContent(String content);

    IEmailMessageBuilder
    setAttachments(Set<IAttachment> attachments);

    IEmailMessageBuilder
    addAttachment(IAttachment attachment);

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