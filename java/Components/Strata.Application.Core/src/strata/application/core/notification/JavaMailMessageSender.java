//////////////////////////////////////////////////////////////////////////////
// JavaMailMessageSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.application.core.inject.IEmailConfigurationProvider;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.value.EmailAddress;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public
class JavaMailMessageSender
    implements IEmailMessageSender
{
    private final IActionQueue                itsQueue;
    private final IEmailConfigurationProvider itsConfiguration;
    private Session                           itsSession;

    @Inject
    JavaMailMessageSender(
        IActionQueue                queue,
        IEmailConfigurationProvider configuration)
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
                Session       session = getSession();
                MimeMessage   msg = new MimeMessage(session);
                MimeMultipart multi = new MimeMultipart();
                MimeBodyPart  content = new MimeBodyPart();

                msg.setFrom(message.getSender().toString());
                msg.setRecipients(
                    Message.RecipientType.TO,
                    toString(message.getRecipients()));
                msg.setSubject(message.getSubject());

                content.setText(message.getContent(),"utf-8", "html");
                multi.addBodyPart(content);

                message
                    .getAttachments()
                    .stream()
                    .forEach(attachment -> addAttachmentPart(multi,attachment));

                msg.setContent(multi);
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
    toString(Collection<EmailAddress> recipients)
    {
        return
            recipients
                .stream()
                .map( email -> email.toString())
                .collect(Collectors.joining(","));
    }

    protected void
    addAttachmentPart(MimeMultipart multi,IAttachment attachment)
    {
        MimeBodyPart    part = null;
        InternetHeaders headers = new InternetHeaders();

        headers.addHeader("Content-Type", attachment.getContentType());
        headers.addHeader("Content-Transfer-Encoding", "base64");

        try
        {
            part = new MimeBodyPart(headers,attachment.getBytes());
            part.setDisposition(MimeBodyPart.INLINE);
            part.setContentID('<' + attachment.getContentId() + '>');
            part.setFileName(attachment.getFileName());

            multi.addBodyPart(part);
        }
        catch (MessagingException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

}

//////////////////////////////////////////////////////////////////////////////
