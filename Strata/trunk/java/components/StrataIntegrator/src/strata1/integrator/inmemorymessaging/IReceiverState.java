
package strata1.integrator.inmemorymessaging;

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import java.util.concurrent.atomic.AtomicBoolean;

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
