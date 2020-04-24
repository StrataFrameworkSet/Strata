//////////////////////////////////////////////////////////////////////////////
// TeleSignMessageSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import com.telesign.MessagingClient;
import com.telesign.RestClient;
import strata.application.core.inject.IConfigurationProvider;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.value.PhoneNumber;

import javax.inject.Inject;
import java.util.Map;

public
class TeleSignMessageSender
    implements ITextMessageSender
{
    private final IActionQueue           itsQueue;
    private final IConfigurationProvider itsConfiguration;
    private MessagingClient              itsClient;

    @Inject
    TeleSignMessageSender(
        IActionQueue           queue,
        IConfigurationProvider configuration)
    {
        itsQueue = queue;
        itsConfiguration = configuration;
        itsClient = null;

        itsQueue.register(() -> open(),() -> close());
    }

    @Override
    public ITextMessageSender
    open()
    {
        Map<String,String> configuration = itsConfiguration.get();
        String             customerId = configuration.get("telesign.customerId");
        String             apiKey = configuration.get("telesign.apiKey");

        itsClient = new MessagingClient(customerId,apiKey);
        return this;
    }

    @Override
    public ITextMessageSender
    close()
    {
        if (isOpen())
            itsClient = null;

        return this;
    }

    @Override
    public ITextMessageSender
    send(ITextMessage message)
    {
        itsQueue.insert(
            () ->
            {
                for (PhoneNumber recipient: message.getRecipients())
                {
                    try
                    {
                        RestClient.TelesignResponse response =
                            getClient().message(
                                recipient.getDigitsOnly(),
                                message.getContent(),
                                "OTP",
                                null);

                        if (!response.ok)
                            throw
                                new RuntimeException(
                                    "send failed: status=" +
                                        response.statusCode +
                                    " body=" + response.body);
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                    finally
                    {
                        Thread.currentThread().sleep(1100);
                    }
                }
            });

        return this;
    }

    @Override
    public boolean
    isOpen()
    {
        return itsClient != null;
    }

    private MessagingClient
    getClient()
    {
        if (!isOpen())
            throw new IllegalStateException("client not open");

        return itsClient;
    }
}

//////////////////////////////////////////////////////////////////////////////
