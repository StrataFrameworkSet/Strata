// ##########################################################################
// # File Name:	SqsMessageReceiver.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegrator Framework.
// #
// #   			The StrataSqsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.sqsintegrator.sqsmessaging;

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsMessageReceiver
    implements IMessageReceiver
{
    private final ISqsMessagingSession itsSession;
    private final String               itsQueueUrl;
    private final AWSCredentials       itsCredentials;
    private IMessageListener           itsListener;
    private String                     itsSelector;
    private ISelector                  itsSelectorImp;
    private final AtomicBoolean        itsListeningFlag;
    private IReceiverState             itsState;

    /************************************************************************
     * Creates a new SqsMessageReceiver. 
     *
     */
    public 
    SqsMessageReceiver(
        ISqsMessagingSession session,
        String               queueUrl,
        AWSCredentials       credentials)
    {
        itsSession       = session;
        itsQueueUrl      = queueUrl;
        itsCredentials   = credentials;
        itsListener      = null;
        itsSelector      = null;
        itsSelectorImp   = new DefaultSelector();
        itsListeningFlag = new AtomicBoolean(false);
    }

    public 
    SqsMessageReceiver(
        ISqsMessagingSession session,
        String               queueUrl,
        AWSCredentials       credentials,
        String               selector)
    {
        itsSession       = session;
        itsQueueUrl      = queueUrl;
        itsCredentials   = credentials;
        itsListener      = null;
        itsSelector      = selector;
        itsSelectorImp   = session.getSelector( selector );
        itsListeningFlag = new AtomicBoolean(false);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    setListener(IMessageListener listener)
        throws MixedModeException
    {
        itsListener = listener;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessagingSession 
    getSession()
    {
        return itsSession;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageListener 
    getListener()
    {
        return itsListener;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getSelector()
    {
        return itsSelector;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startListening() 
        throws MixedModeException
    {
        if ( itsState == null )
            itsState = new AsynchronousReceiverState(itsCredentials);
        
        itsState.startListening( 
            itsQueueUrl,
            itsSelectorImp,
            itsListener,
            itsListeningFlag );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopListening() 
        throws MixedModeException
    {
        if ( itsState == null )
            itsState = new AsynchronousReceiverState(itsCredentials);
        
        itsState.stopListening( itsListeningFlag );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isListening()
    {
        if ( itsState == null )
            itsState = new AsynchronousReceiverState(itsCredentials);
        
        return itsState.isListening( itsListeningFlag );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive() 
        throws MixedModeException
    {        
        if ( itsState == null )
            itsState = new SynchronousReceiverState(itsCredentials);
        
        return itsState.receive( itsQueueUrl,itsSelectorImp );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(long timeOutInMs) 
        throws MixedModeException,NoMessageReceivedException
    {
        if ( itsState == null )
            itsState = new SynchronousReceiverState(itsCredentials);
        
        return itsState.receive( itsQueueUrl,itsSelectorImp,timeOutInMs );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receiveNoWait() 
        throws MixedModeException,NoMessageReceivedException
    {
        if ( itsState == null )
            itsState = new SynchronousReceiverState(itsCredentials);
        
        return itsState.receiveNoWait( itsQueueUrl,itsSelectorImp );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close()
    {
        if ( itsState != null )
            itsState.close();
    }

}

// ##########################################################################
