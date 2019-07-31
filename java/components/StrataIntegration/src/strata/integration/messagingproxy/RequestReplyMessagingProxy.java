// ##########################################################################
// # File Name:	RequestReplyMessagingProxy.java
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

package strata.integration.messagingproxy;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import strata.integration.messaging.IBytesMessage;
import strata.integration.messaging.IMapMessage;
import strata.integration.messaging.IMessageListener;
import strata.integration.messaging.IMessageReceiver;
import strata.integration.messaging.IMessageSender;
import strata.integration.messaging.IMessagingSession;
import strata.integration.messaging.IObjectMessage;
import strata.integration.messaging.IStringMessage;
import strata.integration.messaging.MixedModeException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class RequestReplyMessagingProxy<K,R>
    implements IMessagingProxy,IMessageListener
{
    private final static Random theirGenerator = new SecureRandom();
    
    private final String                       itsReturnAddress;
    private final String                       itsRequestChannelId;
    private final String                       itsReplyChannelId;
    private final IMessagingSession            itsCommandSession;
    private final Map<K,R>                     itsReplyReceivers;  
    private IMessageReceiver                   itsCommandReceiver;
    
    /************************************************************************
     * Creates a new RequestReplyMessagingProxy. 
     *
     * @param requestChannelId
     * @param replyChannelId
     * @param commandSession
     */
    protected 
    RequestReplyMessagingProxy(
        String            requestChannelId,
        String            replyChannelId,
        IMessagingSession commandSession)
    {
        this(
            "",
            requestChannelId,
            replyChannelId,
            commandSession);
    }

    /************************************************************************
     * Creates a new RequestReplyMessagingProxy. 
     *
     * @param returnAddressPrefix
     * @param requestChannelId
     * @param replyChannelId
     * @param commandSession
     */
    protected 
    RequestReplyMessagingProxy(
        String            returnAddressPrefix,
        String            requestChannelId,
        String            replyChannelId,
        IMessagingSession commandSession)
    {
        itsReturnAddress    = createReturnAddress(returnAddressPrefix);
        itsRequestChannelId = requestChannelId;
        itsReplyChannelId   = replyChannelId;
        itsCommandSession   = commandSession;
        itsReplyReceivers   = new ConcurrentHashMap<K,R>();
        
        try
        {
            itsCommandReceiver = 
                itsCommandSession
                    .createMessageReceiver( 
                        itsReplyChannelId,
                        "ReturnAddress='" + itsReturnAddress + "'" )
                    .setListener(  this );
        }
        catch(MixedModeException e)
        {
            throw new IllegalStateException( e );
        }
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
        return itsCommandReceiver.isListening();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasReplyReceivers()
    {
        return !itsReplyReceivers.isEmpty();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasEventListeners()
    {
        return false;
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
    insertReplyReceiver(K key,R receiver)
    {
        if ( hasReplyReceiver( key ) )
            throw new IllegalStateException( "duplicate key" );
        
        itsReplyReceivers.put( key,receiver );
    }

    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    protected R
    removeReplyReceiver(K key)
    {
        if ( !hasReplyReceiver( key ) )
            throw new IllegalStateException( "Unknown reply: " + key );
        
        return itsReplyReceivers.remove( key );        
    }
    
    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    protected boolean
    hasReplyReceiver(K key)
    {
        return itsReplyReceivers.containsKey( key );
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
