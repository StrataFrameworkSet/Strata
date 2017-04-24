// ##########################################################################
// # File Name:	RequestReplyAndEventMessagingProxy.java
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

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import strata.integration.messaging.IMessageReceiver;
import strata.integration.messaging.IMessagingSession;
import strata.integration.messaging.MixedModeException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class RequestReplyAndEventMessagingProxy<K,R,L>
    extends RequestReplyMessagingProxy<K,R>
{
    private final String                       itsEventChannelId;
    private final IMessagingSession            itsEventSession;
    private final Map<K,L>                     itsEventListeners;
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
    RequestReplyAndEventMessagingProxy(
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
    RequestReplyAndEventMessagingProxy(
        String            returnAddressPrefix,
        String            requestChannelId,
        String            replyChannelId,
        String            eventChannelId,
        IMessagingSession commandSession,
        IMessagingSession eventSession)
    {
        super(
            returnAddressPrefix,
            requestChannelId,
            replyChannelId,
            commandSession);
        
        itsEventChannelId   = eventChannelId;
        itsEventSession     = eventSession;
        itsEventListeners   = new ConcurrentHashMap<K,L>();
        itsEventReceivers = 
            new ConcurrentHashMap<String,IMessageReceiver>();        
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
            super.activate();
            
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
            super.deactivate();
            
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
        boolean result = super.isActivated();
        
        for (IMessageReceiver receiver : itsEventReceivers.values() )
            result = result &&  receiver.isListening();
        
        return result;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasEventListeners()
    {
        return !itsEventListeners.isEmpty();
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
            IMessageReceiver receiver = 
                itsEventSession
                .createMessageReceiver( itsEventChannelId,selector )
                .setListener( this );
            
            receiver.startListening();
            itsEventReceivers.put( key,receiver );
            
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
     * @param selector
     */
    protected void
    removeMessageReceiverForEventListeners(String selector)
    {
        String key = normalize( selector );
        
        if ( !itsEventReceivers.containsKey( key ))
           return;
        
        try
        {
            itsEventReceivers.get( key ).stopListening();
            itsEventReceivers.remove( key );
        }
        catch(MixedModeException e)
        {
            throw new IllegalStateException( e );
        }
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected Collection<L>
    getEventListeners()
    {
        return itsEventListeners.values();
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
     * @param selector
     * @return
     */
    protected boolean
    hasMessageReceiverForEventListeners(String selector)
    {
        return itsEventReceivers.containsKey( normalize( selector ) );
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
