// ##########################################################################
// # File Name:	InMemoryMessageReceiver.java
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
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import java.util.concurrent.atomic.AtomicBoolean;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryMessageReceiver
    implements IMessageReceiver
{
    private final IMessagingSession    itsSession;
    private final InMemoryMessageQueue itsQueue;
    private IMessageListener           itsListener;
    private ISelector                  itsSelector;
    private final AtomicBoolean        itsListeningFlag;
    private IReceiverState             itsState;
    
    /************************************************************************
     * Creates a new {@code InMemoryMessageReceiver}. 
     *
     * @param session
     * @param queue
     */
    public 
    InMemoryMessageReceiver(
        IMessagingSession    session,
        InMemoryMessageQueue queue)
    {
        itsSession       = session;
        itsQueue         = queue;
        itsListener      = null;
        itsSelector      = new DefaultSelector();
        itsListeningFlag = new AtomicBoolean(false);
        itsState         = null;
    }
    
    /************************************************************************
     * Creates a new {@code InMemoryMessageReceiver}. 
     *
     * @param session
     * @param queue
     */
    public 
    InMemoryMessageReceiver(
        IMessagingSession    session,
        InMemoryMessageQueue queue,
        ISelector            selector)
    {
        itsSession       = session;
        itsQueue         = queue;
        itsListener      = null;
        itsSelector      = selector;
        itsListeningFlag = new AtomicBoolean(false);
        itsState         = null;
    }
 
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    setListener(IMessageListener listener)
    {
        itsListener = listener;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    setSelector(ISelector selector)
    {
        itsSelector = selector;
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
    public ISelector 
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
            itsState = new AsynchronousReceiverState();
        
        itsState.startListening( 
            itsQueue,
            itsSelector,
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
            itsState = new AsynchronousReceiverState();
        
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
            itsState = new AsynchronousReceiverState();
        
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
            itsState = new SynchronousReceiverState();
        
        return itsState.receive( itsQueue,itsSelector );
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
            itsState = new SynchronousReceiverState();
        
        return itsState.receive( itsQueue,itsSelector,timeOutInMs );
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
            itsState = new SynchronousReceiverState();
        
        return itsState.receiveNoWait( itsQueue,itsSelector );
    }

}

// ##########################################################################
