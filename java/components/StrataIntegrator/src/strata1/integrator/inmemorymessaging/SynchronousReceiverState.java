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

package strata1.integrator.inmemorymessaging;

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */ 
class SynchronousReceiverState
    implements IReceiverState
{

    /************************************************************************
     * Creates a new {@code SynchronousReceiverState}. 
     *
     */ 
    SynchronousReceiverState() {}

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
    receive(InMemoryMessageQueue queue,ISelector selector)
        throws MixedModeException
    {
        IMessage message = queue.take( selector );
        
        while ( message == null )
        {
            LockSupport.parkNanos( 100 );
            message = queue.take( selector );
        }
        
        return message;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(
        InMemoryMessageQueue queue,
        ISelector            selector, 
        long                 timeOutMs)
        throws MixedModeException,NoMessageReceivedException
    {
        IMessage message = queue.take( selector );
        long timeout = timeOutMs;
        
        while ( message == null )
        {
            LockSupport.parkNanos( 1000 );
            timeout -= 1000;
            
            if ( timeout <= 0 )
                throw new NoMessageReceivedException();
            
            message = queue.take( selector );
        }
        
        return message;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receiveNoWait(InMemoryMessageQueue queue,ISelector selector)
        throws MixedModeException,NoMessageReceivedException
    {
        IMessage message = queue.take( selector );
        
        if ( message == null )
            throw new NoMessageReceivedException();
        
        return message;
    }

}

// ##########################################################################
