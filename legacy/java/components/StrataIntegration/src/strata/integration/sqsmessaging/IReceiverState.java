
package strata.integration.sqsmessaging;

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
    
    void
    close();
}
