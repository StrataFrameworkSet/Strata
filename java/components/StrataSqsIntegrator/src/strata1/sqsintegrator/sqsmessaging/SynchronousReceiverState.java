// ##########################################################################
// # File Name:	SynchronousReceiverState.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegrator Framework.
// #
// #   			The StrataIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.sqsintegrator.sqsmessaging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import com.amazonaws.auth.AWSCredentials;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */ 
class SynchronousReceiverState
    extends    AbstractMessageRetriever
    implements IReceiverState
{
    private final Queue<IMessage> itsBuffer;

    /************************************************************************
     * Creates a new {@code SynchronousReceiverState}. 
     *
     */ 
    SynchronousReceiverState(AWSCredentials credentials) 
    {
        super( credentials );
        itsBuffer = new ConcurrentLinkedQueue<IMessage>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startListening(
        String           queueUrl, 
        ISelector        selector, 
        IMessageListener listener, 
        AtomicBoolean    listeningFlag)
        throws MixedModeException
    {
        throw 
            new MixedModeException( 
                "Can not call startListening() in synchronous mode." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopListening(AtomicBoolean listeningFlag)
        throws MixedModeException
    {
        throw 
            new MixedModeException( 
                "Can not call stopListening() in synchronous mode." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isListening(AtomicBoolean listeningFlag)
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(String queueUrl,ISelector selector)
        throws MixedModeException
    {
        IMessage message = itsBuffer.poll();
        
        while ( message == null )
        {
            itsBuffer.addAll(getMessagesFromQueue(queueUrl,selector,2));
            message = itsBuffer.poll();
        }
          
        return message;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(
        String    queueUrl,
        ISelector selector, 
        long      timeOutMs)
        throws MixedModeException,NoMessageReceivedException
    {
        int      secs = (int)(timeOutMs/1000);
        int      waitTimeSecs = secs > 0 ? secs : 2;
        int      remaining = waitTimeSecs;
        IMessage message = itsBuffer.poll();
        
        while ( remaining > 0 )
        {
            if ( message != null )
                return message;
            
            itsBuffer.addAll(
                getMessagesFromQueue(queueUrl,selector,2));
            message = itsBuffer.poll();
            remaining -= 2; 
        }
        
        if ( message != null )
            return message;
        
        throw new NoMessageReceivedException();

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receiveNoWait(String queueUrl,ISelector selector)
        throws MixedModeException,NoMessageReceivedException
    {
        IMessage message = itsBuffer.poll();
        
        for (int i=0;i<5;i++)
        {
            if ( message != null )
                return message;
            
            itsBuffer.addAll(getMessagesFromQueue(queueUrl,selector,2));
            message = itsBuffer.poll();
        }
        
        throw new NoMessageReceivedException();
    }
}

// ##########################################################################
