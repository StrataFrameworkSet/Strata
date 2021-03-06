//////////////////////////////////////////////////////////////////////////////
// IEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.event;

public
interface IEventSender<E>
{
    IEventSender<E>
    open() throws Exception;

    IEventSender<E>
    close() throws Exception;

    IEventSender<E>
    send(E event);

    boolean
    isOpen();
}

//////////////////////////////////////////////////////////////////////////////