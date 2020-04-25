//////////////////////////////////////////////////////////////////////////////
// TeleSignMessageSenderTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import org.junit.Before;
import org.junit.Test;
import strata.application.core.inject.SecureTextingConfigurationProvider;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.action.StandardActionQueue;
import strata.foundation.core.configuration.SecureConfiguration;
import strata.foundation.core.value.PhoneNumber;

import java.io.InputStream;

public
class TeleSignMessageSenderTest
{
    private IActionQueue        itsQueue;
    private ITextMessageSender  itsTarget;
    private ITextMessageBuilder itsBuilder;

    @Before
    public void
    setUp()
        throws Exception
    {
        itsQueue = new StandardActionQueue();
        itsTarget =
            new TeleSignMessageSender(
                itsQueue,
                new SecureTextingConfigurationProvider(
                    new SecureConfiguration(getPropertiesFile())));
        itsBuilder =
            new TextMessageBuilder(
                new StandardTemplateRepository()
                    .insert(
                        "HelloWorld",
                        "Test Message: Hello {{name}} - {{greeting}}"));
    }

    @Test
    public void
    testSend() throws Exception
    {
        ITextMessage message =
            itsBuilder
                .addRecipient(new PhoneNumber("1-562-480-3895"))
                .addRecipient(new PhoneNumber("1-562-889-0909"))
                .setTemplateKey("HelloWorld")
                .addParameter("{{name}}","John and Turath")
                .addParameter("{{greeting}}","what's happening?")
                .build();

        itsTarget.send(message);

        itsQueue.execute();
    }

    protected InputStream
    getPropertiesFile()
    {
        return
            ClassLoader.getSystemResourceAsStream("test.properties");
    }

}

//////////////////////////////////////////////////////////////////////////////
