
package strata.integration.inmemorymessaging;

import java.util.concurrent.atomic.AtomicBoolean;
import strata.integration.messaging.IMessage;
import strata.integration.messaging.IMessageListener;
import strata.integration.messaging.ISelector;
import strata.integration.messaging.MixedModeException;
import strata.integration.messaging.NoMessageReceivedException;

interface IReceiverState
{
    void
    startListening(
        InMemoryMessageQueue queue, 
        ISelector            selector, 
        IMessageListener     listener, 
        AtomicBoolean        listeningFlag)
        throws MixedModeException;

    void 
    stopListening(AtomicBoolean listeningFlag)
        throws MixedModeException;

    boolean 
    isListening(AtomicBoolean listeningFlag);

    IMessage 
    receive(InMemoryMessageQueue queue,ISelector selector) 
        throws MixedModeException;

    IMessage 
    receive(
        InMemoryMessageQueue queue,
        ISelector            selector, 
        long                 timeOutMs)
        throws MixedModeException,NoMessageReceivedException;

    IMessage 
    receiveNoWait(InMemoryMessageQueue queue,ISelector selector)
        throws MixedModeException,NoMessageReceivedException;
}
