//////////////////////////////////////////////////////////////////////////////
// EmailMessageBuilder.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.EmailAddress;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public
class EmailMessageBuilder
    implements IEmailMessageBuilder
{
    private final ITemplateRepository itsRepository;
    private EmailAddress              itsSender;
    private List<EmailAddress>        itsRecipients;
    private String                    itsSubject;
    private String                    itsContent;
    private String                    itsTemplateKey;
    private Map<String,String>        itsParameters;

    @Inject
    public
    EmailMessageBuilder(ITemplateRepository repository)
    {
        itsRepository = repository;
        itsSender = null;
        itsRecipients = new ArrayList<>();
        itsSubject = null;
        itsContent = null;
        itsTemplateKey = null;
        itsParameters = new HashMap<>();
    }

    @Override
    public IEmailMessageBuilder
    setSender(EmailAddress sender)
    {
        itsSender = sender;
        return this;
    }

    @Override
    public IEmailMessageBuilder
    setRecipients(List<EmailAddress> recipients)
    {
        itsRecipients.clear();
        itsRecipients.addAll(recipients);
        return this;
    }

    @Override
    public IEmailMessageBuilder
    addRecipient(EmailAddress recipient)
    {
        itsRecipients.add(recipient);
        return this;
    }

    @Override
    public IEmailMessageBuilder
    setSubject(String subject)
    {
        itsSubject = subject;
        return this;
    }

    @Override
    public IEmailMessageBuilder
    setContent(String content)
    {
        if (isTemplated())
        {
            itsTemplateKey = null;
            itsParameters.clear();
        }

        itsContent = content;
        return this;
    }

    @Override
    public IEmailMessageBuilder
    setTemplateKey(String templateKey)
    {
        if (isSimple())
            itsContent = null;

        itsTemplateKey = templateKey;
        return this;
    }

    @Override
    public IEmailMessageBuilder
    setParameters(Map<String,String> parameters)
    {
        if (isSimple())
            itsContent = null;

        itsParameters.clear();
        itsParameters.putAll(parameters);
        return this;
    }

    @Override
    public IEmailMessageBuilder
    addParameter(String parameterKey,String parameterValue)
    {
        if (isSimple())
            itsContent = null;

        itsParameters.put(parameterKey,parameterValue);
        return this;
    }

    @Override
    public IEmailMessage
    build()
    {
        if (isSimple())
            return buildSimple();
        else if (isTemplated())
            return buildTemplated();

        throw
            new IllegalStateException(
                "Insufficient information to build email message.");

    }

    @Override
    public IEmailMessageBuilder
    clear()
    {
        itsSender = null;
        itsRecipients.clear();
        itsSubject = null;
        itsContent = null;
        itsTemplateKey = null;
        itsParameters.clear();
        return this;
    }

    private boolean
    isSimple()
    {
        return itsContent != null;
    }

    private boolean
    isTemplated()
    {
        return itsTemplateKey != null;
    }

    private IEmailMessage
    buildSimple()
    {
        try
        {
            return
                new SimpleEmailMessage(
                    itsSender,
                    itsRecipients,
                    itsSubject != null ? itsSubject : "",
                    itsContent);
        }
        finally
        {
            clear();
        }
    }

    private IEmailMessage
    buildTemplated()
    {
        try
        {
            return
                new TemplatedEmailMessage(
                    itsSender,
                    itsRecipients,
                    itsSubject,
                    itsRepository,
                    itsTemplateKey,
                    itsParameters);
        }
        finally
        {
            clear();
        }
    }
}

//////////////////////////////////////////////////////////////////////////////
