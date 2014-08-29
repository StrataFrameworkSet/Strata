// ##########################################################################
// # File Name:	AsynchronousReceiverState.java
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

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import com.amazonaws.services.sqs.AmazonSQS;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
class AsynchronousReceiverState
    implements IReceiverState
{
    private final AmazonSQS       itsService;
    private final ExecutorService itsExecutor;
    
    /************************************************************************
     * Creates a new {@code AsynchronousReceiverState}. 
     *
     */ 
    AsynchronousReceiverState(AmazonSQS service) 
    {
        itsService = service;
        itsExecutor = Executors.newFixedThreadPool( 1 );
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
        if ( !isListening( listeningFlag ) )
        {
            if ( listener == null )
                throw new IllegalStateException( "listener == null" );
            
            listeningFlag.compareAndSet( false,true );
            itsExecutor.execute( 
                new SqsMessageProcessor(
                    itsService,
                    queueUrl,
                    selector,
                    listener,
                    listeningFlag ) );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopListening(AtomicBoolean listeningFlag)
        throws MixedModeException
    {
        if ( isListening( listeningFlag ) )
            listeningFlag.compareAndSet( true,false );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isListening(AtomicBoolean listeningFlag)
    {
        return listeningFlag.get();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(String queueUrl,ISelector selector)
        throws MixedModeException
    {
        throw 
            new MixedModeException( 
                "Can not call receive in asynchronous mode." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(String queueUrl,ISelector selector,
        long timeOutMs) throws MixedModeException,NoMessageReceivedException
    {
        throw 
            new MixedModeException( 
                "Can not call receive in asynchronous mode." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receiveNoWait(String queueUrl,ISelector selector)
        throws MixedModeException,NoMessageReceivedException
    {
        throw 
            new MixedModeException( 
                "Can not call receiveNoWait in asynchronous mode." );
    }

}

// ##########################################################################
