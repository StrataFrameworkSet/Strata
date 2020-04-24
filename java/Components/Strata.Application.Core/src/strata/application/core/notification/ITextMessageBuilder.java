//////////////////////////////////////////////////////////////////////////////
// ITextMessageBuilder.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.PhoneNumber;

import java.util.List;
import java.util.Map;

public
interface ITextMessageBuilder
{
    ITextMessageBuilder
    setRecipients(List<PhoneNumber> recipients);

    ITextMessageBuilder
    addRecipient(PhoneNumber recipient);

    ITextMessageBuilder
    setContent(String content);

    ITextMessageBuilder
    setTemplateKey(String templateKey);

    ITextMessageBuilder
    setParameters(Map<String,String> parameters);

    ITextMessageBuilder
    addParameter(String parameterKey,String parameterValue);

    ITextMessage
    build();

    ITextMessageBuilder
    clear();
}

//////////////////////////////////////////////////////////////////////////////