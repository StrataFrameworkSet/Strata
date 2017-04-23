// ##########################################################################
// # File Name:	SqsQueueMessagingSession.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegrator Framework.
// #
// #   			The StrataSqsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.sqsintegrator.sqsmessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import strata1.integrator.messaging.IBytesMessage;
import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IObjectMessage;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.IStringMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsQueueMessagingSession
    implements ISqsMessagingSession
{
    private final AWSCredentials        itsCredentials;
    private AmazonSQSClient             itsImp;
    private final Map<String,String>    itsQueueUrls;
    private final Map<String,ISelector> itsSelectors;
    private final AtomicBoolean         itsReceivingFlag;
    private final AcknowledgementMode   itsAcknowledgementMode;
    
    /************************************************************************
     * Creates a new SqsQueueMessagingSession. 
     *
     */
    public 
    SqsQueueMessagingSession(AWSCredentials credentials)
    {
        this( credentials,AcknowledgementMode.AUTO_ACKNOWLEDGEMENT );
    }

    /************************************************************************
     * Creates a new SqsQueueMessagingSession. 
     *
     */
    public 
    SqsQueueMessagingSession(
        AWSCredentials      credentials,
        AcknowledgementMode ackMode)
    {
        itsCredentials = credentials;
        itsImp         = new AmazonSQSClient(itsCredentials);
        itsQueueUrls = new HashMap<String,String>();
        itsSelectors = new HashMap<String,ISelector>();
        itsReceivingFlag = new AtomicBoolean(false);
        itsAcknowledgementMode = ackMode;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageSender 
    createMessageSender(String id)
    {
        return new SqsMessageSender( this,getQueueUrl(id) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id)
    {
        return new SqsMessageReceiver( this,getQueueUrl(id) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id,String selector)
    {
        return 
            new SqsMessageReceiver(this,getQueueUrl(id),selector);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    createStringMessage()
    {
        return new SqsStringMessage(this);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    createMapMessage()
    {
        return new SqsMapMessage(this);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    createObjectMessage()
    {
        return new SqsObjectMessage(this);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    createBytesMessage()
    {
        return null;
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
        if ( itsImp != null )
            itsImp.shutdown();
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
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public SqsQueueMessagingSession
    insertQueue(String queueId,String queueUrl)
    {
        itsQueueUrls.put( queueId,queueUrl );
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param expression
     * @param selector
     * @return
     */
    public SqsQueueMessagingSession
    insertSelector(String expression,ISelector selector)
    {
        itsSelectors.put( normalize(expression),selector );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    acknowledge(SqsMessage message)
    {
       if ( !message.hasQueueUrl() )
           throw 
               new IllegalStateException(
                   "SqsMessage must have a queueUrl to acknowledge.");
       
       removeMessageFromQueue( itsImp,message ); 
            
    }

    /************************************************************************
     *  
     *
     * @param id
     * @return
     */
    @Override
    public String 
    getQueueUrl(String id)
    {
        if ( !hasQueue( id ) )
            throw 
                new IllegalArgumentException( 
                    "queue id:" + id + " does not exist." );
        
        return itsQueueUrls.get( id );
    }

    /************************************************************************
     *  
     *
     * @param selector
     * @return
     */
    public ISelector
    getSelector(String selector)
    {
        return itsSelectors.get( normalize(selector) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public AcknowledgementMode 
    getAcknowledgementMode()
    {
        return itsAcknowledgementMode;
    }

    /************************************************************************
     *  
     *
     * @param queueId
     * @return
     */
    @Override
    public boolean
    hasQueue(String queueId)
    {
        return itsQueueUrls.containsKey( queueId );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasSelector(String expression)
    {
        return itsSelectors.containsKey( normalize(expression) );
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public AmazonSQSClient 
    getImp()
    {
        return itsImp;
    }
    
    /************************************************************************
     *  
     *
     * @param service 
     * @param message
     */
    private void 
    removeMessageFromQueue(AmazonSQS service,SqsMessage message)
    {
        DeleteMessageRequest request = 
            new DeleteMessageRequest()
                .withQueueUrl( 
                    message.getQueueUrl() )
                .withReceiptHandle( 
                    message.getMessageImp().getReceiptHandle() );
        
        service.deleteMessage( request );
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
