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

package strata1.sqsintegrator.sqsmessaging;

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageListener;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MixedModeException;
import strata1.integrator.messaging.NoMessageReceivedException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
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
    extends    AbstractMessageRetriever
    implements IReceiverState
{

    /************************************************************************
     * Creates a new {@code SynchronousReceiverState}. 
     *
     */ 
    SynchronousReceiverState(AWSCredentials credentials) 
    {
        super( credentials );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startListening(
        String           queueUrl, 
        ISelector        selector, 
        IMessageListener listener, 
        AtomicBoolean    listeningFlag)
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
    receive(String queueUrl,ISelector selector)
        throws MixedModeException
    {
        AmazonSQSClient service = null;
        IMessage        message = null;
        
        service = new AmazonSQSClient(getCredentials());
        
        try
        {
            message = getMessageFromQueue(service,queueUrl,selector,0);
            
            while ( message == null )
            {
                sleepForSeconds( 5 );
                message = getMessageFromQueue(service,queueUrl,selector,0);
            }
            
            return message;
        }
        finally
        {
            service.shutdown();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(
        String    queueUrl,
        ISelector selector, 
        long      timeOutMs)
        throws MixedModeException,NoMessageReceivedException
    {
        AmazonSQSClient service = null;
        IMessage        message = null;
        int timeOutSecs = (int)(timeOutMs/1000);
        
        service = new AmazonSQSClient(getCredentials());
        
        try
        {
            message = 
                getMessageFromQueue(
                    service,
                    queueUrl,
                    selector,
                    timeOutSecs );
            
            sleepForSeconds( 3 );
            
            if ( message == null )
                message = 
                    getMessageFromQueue(
                        service,
                        queueUrl,
                        selector,
                        0 );

            if ( message == null )
                throw new NoMessageReceivedException();
            
            return message;
        }
        finally
        {
            service.shutdown();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receiveNoWait(String queueUrl,ISelector selector)
        throws MixedModeException,NoMessageReceivedException
    {
        AmazonSQSClient service = null;
        IMessage        message = null;
        
        service = new AmazonSQSClient(getCredentials());
        
        try
        {
            for (int i=0;i<5;i++)
            {
                message = 
                    getMessageFromQueue(
                        service,
                        queueUrl,
                        selector,
                        0 );
            
                if ( message != null )
                    return message;
                
                sleepForSeconds( 3 );
            }
            
            throw new NoMessageReceivedException();
        }
        finally
        {
            service.shutdown();
        }
    }

    /************************************************************************
     *  
     *
     * @param seconds
     */
    private void 
    sleepForSeconds(int seconds)
    {
        try
        {
            Thread.sleep( seconds*1000 );
        }
        catch(InterruptedException e) {}
    }
    
}

// ##########################################################################
