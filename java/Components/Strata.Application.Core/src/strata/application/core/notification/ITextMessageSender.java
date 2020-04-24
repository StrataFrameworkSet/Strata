//////////////////////////////////////////////////////////////////////////////
// ITextMessageSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

public
interface ITextMessageSender
{
    ITextMessageSender
    open();

    ITextMessageSender
    close();

    ITextMessageSender
    send(ITextMessage message);

    boolean
    isOpen();
}

//////////////////////////////////////////////////////////////////////////////