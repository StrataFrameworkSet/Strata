
package strata1.sqsintegrator.sqsmessaging;

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
        String               queueUrl, 
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
    receive(String queueUrl,ISelector selector) 
        throws MixedModeException;

    IMessage 
    receive(
        String    queueUrl,
        ISelector selector, 
        long      timeOutMs)
        throws MixedModeException,NoMessageReceivedException;

    IMessage 
    receiveNoWait(String queueUrl,ISelector selector)
        throws MixedModeException,NoMessageReceivedException;
}
