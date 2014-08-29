// ##########################################################################
// # File Name:	InMemoryMessagingSession.java
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

import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.IObjectMessage;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.IStringMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryMessagingSession
    implements IMessagingSession
{
    private final Map<String,InMemoryMessageQueue> itsQueues;
    private final Map<String,ISelector>            itsSelectors;
    private final AtomicBoolean                    itsReceivingFlag;
    private final AtomicBoolean                    itsClosedFlag;
    
    /************************************************************************
     * Creates a new {@code InMemoryMessagingSession}. 
     *
     */
    public 
    InMemoryMessagingSession()
    {
        itsQueues        = new HashMap<String,InMemoryMessageQueue>();
        itsSelectors     = new HashMap<String,ISelector>();
        itsReceivingFlag = new AtomicBoolean( false );
        itsClosedFlag    = new AtomicBoolean( false );
    }

    /************************************************************************
     * Creates a new {@code InMemoryMessagingSession}. 
     *
     */
    public 
    InMemoryMessagingSession(final Map<String,ISelector> selectors)
    {
        itsQueues        = new HashMap<String,InMemoryMessageQueue>();
        itsSelectors     = new HashMap<String,ISelector>();
        itsReceivingFlag = new AtomicBoolean( false );
        itsClosedFlag    = new AtomicBoolean( false );
       
        for (String expression : selectors.keySet())
            insertSelector( expression,selectors.get( expression ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageSender 
    createMessageSender(String id)
    {
        if ( !itsQueues.containsKey( id ) )
            itsQueues.put( id,new InMemoryMessageQueue() );
        
        return new InMemoryMessageSender( this,itsQueues.get( id ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id)
    {
        if ( !itsQueues.containsKey( id ) )
            itsQueues.put( id,new InMemoryMessageQueue() );
        
        return new InMemoryMessageReceiver( this,itsQueues.get( id ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id,String selector)
    {
        if ( !itsQueues.containsKey( id ) )
            itsQueues.put( id,new InMemoryMessageQueue() );
        
        if ( !itsSelectors.containsKey( normalize(selector) ) )
            throw 
                new IllegalArgumentException(
                    "No such selector configured for: " + selector);
        
        return 
            new InMemoryMessageReceiver( 
                this,
                itsQueues.get( id ),
                selector );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    createStringMessage()
    {
        return new InMemoryStringMessage();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    createMapMessage()
    {
        return new InMemoryMapMessage();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    createObjectMessage()
    {
        return new InMemoryObjectMessage();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startReceiving()
    {
        itsReceivingFlag.compareAndSet( false,true );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopReceiving()
    {
        itsReceivingFlag.compareAndSet( true,false );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close()
    {
        itsClosedFlag.compareAndSet( true,false );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isReceiving()
    {
        return itsReceivingFlag.get();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isClosed()
    {
        return itsClosedFlag.get();
    }

    /************************************************************************
     *  
     *
     * @param expression
     * @param selector
     * @return
     */
    public InMemoryMessagingSession
    insertSelector(String expression,ISelector selector)
    {
        itsSelectors.put( normalize(expression),selector );
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param selector
     * @return
     */
    ISelector
    getSelector(String selector)
    {
        return itsSelectors.get( normalize(selector) );
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
