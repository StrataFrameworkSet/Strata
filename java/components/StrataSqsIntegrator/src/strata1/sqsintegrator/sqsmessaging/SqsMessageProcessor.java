// ##########################################################################
// # File Name:	SqsMessageProcessor.java
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

import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.IObjectMessage;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.IStringMessage;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
class SqsMessageProcessor
    extends    AbstractMessageRetriever
    implements Runnable
{
    private static final long SECOND = 1000000000;
    
    private final String           itsQueueUrl;
    private final ISelector        itsSelector;
    private final IMessageListener itsListener;
    private final AtomicBoolean    itsListeningFlag;
    
    /************************************************************************
     * Creates a new {@code SqsMessageProcessor}. 
     *
     */
    SqsMessageProcessor(
        final AWSCredentials   credentials,
        final String           queueUrl,
        final ISelector        selector,
        final IMessageListener listener,
        final AtomicBoolean    listeningFlag)
    {
        super( credentials );
        itsQueueUrl      = queueUrl;
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
            try
            {
                for (IMessage message:getNextMessages())
                {
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
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
    }

    /************************************************************************
     *  
     *
     * @param message
     * @return
     */
    private List<IMessage> 
    getNextMessages()
    {
        List<IMessage> messages = 
            super.getMessagesFromQueue( itsQueueUrl,itsSelector ); 
        
        while ( messages.isEmpty() && itsListeningFlag.get() )
        {
            messages = 
                super.getMessagesFromQueue( itsQueueUrl,itsSelector ); 
            
            if ( messages.isEmpty() )
                LockSupport.parkNanos( SECOND );
        }
        
        return messages;
    }

}

// ##########################################################################
