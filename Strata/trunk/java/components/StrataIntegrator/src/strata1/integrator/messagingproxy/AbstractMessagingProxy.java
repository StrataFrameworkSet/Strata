// ##########################################################################
// # File Name:	AbstractMessagingProxy.java
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

package strata1.integrator.messagingproxy;

import strata1.integrator.messaging.IBytesMessage;
import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.IObjectMessage;
import strata1.integrator.messaging.IStringMessage;
import strata1.integrator.messaging.MixedModeException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractMessagingProxy<K,R>
    implements IMessagingProxy,IMessageListener
{
    private final static Random     theirGenerator = new SecureRandom();
    
    private final String            itsReturnAddress;
    private final String            itsRequestChannelId;
    private final String            itsReplyChannelId;
    private final String            itsEventChannelId;
    private final IMessagingSession itsCommandSession;
    private final IMessagingSession itsEventSession;
    private final Map<K,R>          itsPendingReceivers;          
    private IMessageReceiver        itsCommandReceiver;
    private IMessageReceiver        itsEventReceiver;
    
    /************************************************************************
     * Creates a new AbstractMessagingProxy. 
     *
     * @param requestChannelId
     * @param replyChannelId
     * @param eventChannelId
     * @param commandSession
     * @param eventSession
     */
    protected 
    AbstractMessagingProxy(
        String            requestChannelId,
        String            replyChannelId,
        String            eventChannelId,
        IMessagingSession commandSession,
        IMessagingSession eventSession)
    {
        this(
            "",
            requestChannelId,
            replyChannelId,
            eventChannelId,
            commandSession,
            eventSession);
    }

    /************************************************************************
     * Creates a new AbstractMessagingProxy. 
     *
     * @param returnAddressPrefix
     * @param requestChannelId
     * @param replyChannelId
     * @param eventChannelId
     * @param commandSession
     * @param eventSession
     */
    protected 
    AbstractMessagingProxy(
        String            returnAddressPrefix,
        String            requestChannelId,
        String            replyChannelId,
        String            eventChannelId,
        IMessagingSession commandSession,
        IMessagingSession eventSession)
    {
        itsReturnAddress    = createReturnAddress(returnAddressPrefix);
        itsRequestChannelId = requestChannelId;
        itsReplyChannelId   = replyChannelId;
        itsEventChannelId   = eventChannelId;
        itsCommandSession   = commandSession;
        itsEventSession     = eventSession;
        itsPendingReceivers = new ConcurrentHashMap<K,R>();
        
        itsCommandReceiver = 
            itsCommandSession.createMessageReceiver( 
                itsReplyChannelId,
                "ReturnAddress='" + itsReturnAddress + "'" );
        itsEventReceiver = 
            itsEventSession.createMessageReceiver( itsEventChannelId );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String
    getReturnAddress()
    {
        return itsReturnAddress;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    activate()
    {
        try
        {
            itsCommandReceiver.startListening();
            itsEventReceiver.startListening();
        }
        catch(MixedModeException e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    deactivate()
    {
        try
        {
            itsCommandReceiver.stopListening();
            itsEventReceiver.stopListening();
        }
        catch(MixedModeException e)
        {
            throw new IllegalStateException(e);
        }
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean
    isActivated()
    {
        return 
            itsCommandReceiver.isListening() && 
            itsEventReceiver.isListening();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasPendingReceivers()
    {
        return !itsPendingReceivers.isEmpty();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IStringMessage message)
    {
        throw new UnsupportedOperationException("must override in subclass");
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IMapMessage message)
    {
        throw new UnsupportedOperationException("must override in subclass");
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IObjectMessage message)
    {
        throw new UnsupportedOperationException("must override in subclass");
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(IBytesMessage message)
    {
        throw new UnsupportedOperationException("must override in subclass");
    }

    /************************************************************************
     *  
     *
     * @param key
     * @param receiver
     */
    protected void
    insertPendingReceiver(K key,R receiver)
    {
        if ( hasPendingReceiver( key ) )
            throw new IllegalStateException( "duplicate key" );
        
        itsPendingReceivers.put( key,receiver );
    }
    
    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    protected R
    removePendingReceiver(K key)
    {
        R receiver = null;
        
        if ( !hasPendingReceiver( key ) )
            throw new IllegalStateException( "Unknown reply: " + key );
        
        receiver = itsPendingReceivers.get( key );
        return receiver;
        
    }
    
    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    protected boolean
    hasPendingReceiver(K key)
    {
        return itsPendingReceivers.containsKey( key );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected IMessageSender
    createMessageSender()
    {
        return itsCommandSession.createMessageSender( itsRequestChannelId );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected IStringMessage
    createStringMessage()
    {
        return 
            itsCommandSession
                .createStringMessage()
                .setReturnAddress( itsReturnAddress );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected IMapMessage
    createMapMessage()
    {
        return 
            itsCommandSession
                .createMapMessage()
                .setReturnAddress( itsReturnAddress );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected IObjectMessage
    createObjectMessage()
    {
        return 
            itsCommandSession
                .createObjectMessage()
                .setReturnAddress( itsReturnAddress );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected IBytesMessage
    createBytesMessage()
    {
        return 
            itsCommandSession
                .createBytesMessage()
                .setReturnAddress( itsReturnAddress );
    }
    
    /************************************************************************
     *  
     *
     * @param prefix
     * @return
     */
    private String
    createReturnAddress(String prefix)
    {
        return 
            prefix +
            new Long(theirGenerator.nextLong()).toString();
    }
}

// ##########################################################################
