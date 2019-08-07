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

package strata.integration.inmemorymessaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import strata.integration.messaging.IMessage;
import strata.integration.messaging.IMessageListener;
import strata.integration.messaging.ISelector;
import strata.integration.messaging.MixedModeException;
import strata.integration.messaging.NoMessageReceivedException;

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
    private final ExecutorService itsExecutor;
    
    /************************************************************************
     * Creates a new {@code AsynchronousReceiverState}. 
     *
     */ 
    AsynchronousReceiverState() 
    {
        itsExecutor = Executors.newFixedThreadPool( 1 );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startListening(
        InMemoryMessageQueue queue,
        ISelector            selector,
        IMessageListener     listener,
        AtomicBoolean        listeningFlag)
        throws MixedModeException
    {
        if ( !isListening( listeningFlag ) )
        {
            if ( listener == null )
                throw new IllegalStateException( "listener == null" );
            
            listeningFlag.compareAndSet( false,true );
            itsExecutor.execute( 
                new InMemoryMessageProcessor(
                    queue,
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
    receive(InMemoryMessageQueue queue,ISelector selector)
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
    receive(InMemoryMessageQueue queue,ISelector selector,
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
    receiveNoWait(InMemoryMessageQueue queue,ISelector selector)
        throws MixedModeException,NoMessageReceivedException
    {
        throw 
            new MixedModeException( 
                "Can not call receiveNoWait in asynchronous mode." );
    }

}

// ##########################################################################
