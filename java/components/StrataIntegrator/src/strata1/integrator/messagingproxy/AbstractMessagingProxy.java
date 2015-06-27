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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractMessagingProxy<K,R,L>
    implements IMessagingProxy,IMessageListener
{
    private final static Random theirGenerator = new SecureRandom();
    
    private final String                       itsReturnAddress;
    private final String                       itsRequestChannelId;
    private final String                       itsReplyChannelId;
    private final String                       itsEventChannelId;
    private final IMessagingSession            itsCommandSession;
    private final IMessagingSession            itsEventSession;
    private final Map<K,R>                     itsReplyReceivers;  
    private final Map<K,L>                     itsEventListeners;
    private IMessageReceiver                   itsCommandReceiver;
    private final Map<String,IMessageReceiver> itsEventReceivers;
    
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
        IMessagingSession eventSession,
        String            eventSelector)
    {
        this(
            "",
            requestChannelId,
            replyChannelId,
            eventChannelId,
            commandSession,
            eventSession,
            eventSelector);
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
        IMessagingSession eventSession,
        String            eventSelector)
    {
        itsReturnAddress    = createReturnAddress(returnAddressPrefix);
        itsRequestChannelId = requestChannelId;
        itsReplyChannelId   = replyChannelId;
        itsEventChannelId   = eventChannelId;
        itsCommandSession   = commandSession;
        itsEventSession     = eventSession;
        itsReplyReceivers   = new ConcurrentHashMap<K,R>();
        itsEventListeners   = new ConcurrentHashMap<K,L>();
        itsEventReceivers = 
            new ConcurrentHashMap<String,IMessageReceiver>();
        
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
            
            for (IMessageReceiver receiver : itsEventReceivers.values() )
                receiver.startListening();
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
            
            for (IMessageReceiver receiver : itsEventReceivers.values() )
                receiver.stopListening();
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
        boolean result = itsCommandReceiver.isListening();
        
        for (IMessageReceiver receiver : itsEventReceivers.values() )
            result = result &&  receiver.isListening();
        
        return result;
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
     * @param receiver
     */
    protected void
    insertEventListener(K key,L listener)
    {
        if ( hasEventListener( key ) )
            throw new IllegalStateException( "duplicate key" );
        
        itsEventListeners.put( key,listener );
    }

    /************************************************************************
     *  
     *
     * @param key
     * @param selector
     */
    protected void
    insertMessageReceiverForEventListeners(String selector)
    {
        String key = normalize( selector );
        
        if ( itsEventReceivers.containsKey( key ))
            throw 
                new IllegalStateException( 
                    "Receiver: " + key + " already exists." );
        
        try
        {
            itsEventReceivers.put( 
                key,
                itsEventSession
                    .createMessageReceiver( itsEventChannelId,selector )
                    .setListener( this ) );
        }
        catch(MixedModeException e)
        {
            throw new IllegalStateException( e );
        }
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
    protected L
    removeEventListener(K key)
    {        
        if ( !hasEventListener( key ) )
            throw new IllegalStateException( "Unknown reply: " + key );
        
        return itsEventListeners.remove( key );
        
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
     * @param key
     * @return
     */
    protected boolean
    hasEventListener(K key)
    {
        return itsEventListeners.containsKey( key );
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
    
    /************************************************************************
     *  
     *
     * @param expression
     * @return
     */
    private String 
    normalize(String expression)
    {
        StringBuilder builder = new StringBuilder();
        Matcher       matcher = 
            Pattern
                .compile("([^\"]\\S*|\".+?\")\\s*")
                .matcher(expression);
        
        while ( matcher.find() )
            builder.append(matcher.group(1)); 
        
        return builder.toString();        
    }

}

// ##########################################################################
