// ##########################################################################
// # File Name:	InMemoryMessageProcessor.java
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

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import strata.integration.messaging.IMapMessage;
import strata.integration.messaging.IMessage;
import strata.integration.messaging.IMessageListener;
import strata.integration.messaging.IObjectMessage;
import strata.integration.messaging.ISelector;
import strata.integration.messaging.IStringMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
class InMemoryMessageProcessor
    implements Runnable
{
    private final InMemoryMessageQueue itsQueue;
    private final ISelector            itsSelector;
    private final IMessageListener     itsListener;
    private final AtomicBoolean        itsListeningFlag;
    
    /************************************************************************
     * Creates a new {@code InMemoryMessageProcessor}. 
     *
     */
    InMemoryMessageProcessor(
        final InMemoryMessageQueue queue,
        final ISelector            selector,
        final IMessageListener     listener,
        final AtomicBoolean        listeningFlag)
    {
        itsQueue         = queue;
        itsSelector      = selector;
        itsListener      = listener;
        itsListeningFlag = listeningFlag;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run() 
    {
        while ( itsListeningFlag.get() )
        {
            IMessage message = getNextMessage();
            
            if ( message != null )
            {
                if ( message instanceof IStringMessage )
                    itsListener.onMessage( (IStringMessage)message );
                else if ( message instanceof IMapMessage )
                    itsListener.onMessage( (IMapMessage)message );
                else if ( message instanceof IObjectMessage )
                    itsListener.onMessage( (IObjectMessage)message );
            }
        }
    }

    /************************************************************************
     *  
     *
     * @param message
     * @return
     */
    private IMessage 
    getNextMessage()
    {
        IMessage message = null; 
        
        while ( message == null && itsListeningFlag.get() )
        {
            message = itsQueue.take( itsSelector );
            
            if ( message == null )
                LockSupport.parkNanos( 100 );
        }
        
        return message;
    }

}

// ##########################################################################
