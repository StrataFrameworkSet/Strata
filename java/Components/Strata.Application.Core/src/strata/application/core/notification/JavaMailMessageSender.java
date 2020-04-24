//////////////////////////////////////////////////////////////////////////////
// JavaMailMessageSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.application.core.inject.IConfigurationProvider;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.value.EmailAddress;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public
class JavaMailMessageSender
    implements IEmailMessageSender
{
    private final IActionQueue           itsQueue;
    private final IConfigurationProvider itsConfiguration;
    private Session                      itsSession;

    @Inject
    JavaMailMessageSender(
        IActionQueue           queue,
        IConfigurationProvider configuration)
    {
        itsQueue = queue;
        itsConfiguration = configuration;
        itsSession = null;

        itsQueue.register(() -> open(),() -> close());
    }

    @Override
    public IEmailMessageSender
    open()
    {
        Properties properties = new Properties();

        properties.putAll(itsConfiguration.get());
        itsSession =
            Session.getInstance(
                properties,
                new Authenticator()
        {
            @Override
            protected PasswordAuthentication
            getPasswordAuthentication()
            {
                Map<String,String> configuration =
                    itsConfiguration.get();
                String user = configuration.get("mail.smtp.user");
                String password = configuration.get("mail.smtp.password");

                return new PasswordAuthentication(user,password);
            }
        });

        return this;
    }

    @Override
    public IEmailMessageSender
    close()
    {
        if (isOpen())
            itsSession = null;

        return this;
    }

    @Override
    public IEmailMessageSender
    send(IEmailMessage message)
    {
        itsQueue.insert(
            () ->
            {
                Session     session = getSession();
                MimeMessage msg = new MimeMessage(session);
                Transport   transport = session.getTransport("smtps");

                msg.setFrom(message.getSender().toString());
                msg.setRecipients(
                    Message.RecipientType.TO,
                    toString(message.getRecipients()));
                msg.setSubject(message.getSubject());
                msg.setText(message.getContent(),"utf-8", "html");

                Transport.send(msg);
            });
        return this;
    }

    @Override
    public boolean
    isOpen()
    {
        return itsSession != null;
    }

    protected Session
    getSession()
    {
        if (!isOpen())
            throw new IllegalStateException("session not open");

        return itsSession;
    }

    protected String
    toString(List<EmailAddress> recipients)
    {
        return
            recipients
                .stream()
                .map( email -> email.toString())
                .collect(Collectors.joining(","));
    }
}

//////////////////////////////////////////////////////////////////////////////
