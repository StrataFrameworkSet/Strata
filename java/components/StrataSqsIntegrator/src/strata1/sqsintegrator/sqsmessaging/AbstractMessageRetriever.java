// ##########################################################################
// # File Name:	AbstractMessageRetriever.java
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

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.ISelector;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ChangeMessageVisibilityRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractMessageRetriever
{
    private final AWSCredentials itsCredentials;
    private AmazonSQSClient      itsService;
    
    /************************************************************************
     * Creates a new AbstractMessageRetriever. 
     *
     */
    public 
    AbstractMessageRetriever(AWSCredentials credentials)
    {
        itsCredentials = credentials;
        itsService     = new AmazonSQSClient(itsCredentials);
    }

    /************************************************************************
     *  
     *
     */
    public void
    close()
    {
        itsService.shutdown();
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected AWSCredentials
    getCredentials()
    {
        return itsCredentials;
    }
    
    /************************************************************************
     *  
     *
     * @param queueUrl
     * @param selector TODO
     * @return
     */
    protected List<IMessage>
    getMessagesFromQueue(String queueUrl,ISelector selector,int waitTimeSecs)
    {
        AmazonSQSClient       service = itsService;
        ReceiveMessageRequest request = null;
        ReceiveMessageResult  result  = null;
        List<IMessage>        messages = new ArrayList<IMessage>();
                
        try
        {
            request = 
                new ReceiveMessageRequest()
                    .withQueueUrl( queueUrl )
                    .withAttributeNames( "All" )
                    .withMessageAttributeNames( "All" )
                    .withWaitTimeSeconds( waitTimeSecs )
                    .withVisibilityTimeout( 20 )
                    .withMaxNumberOfMessages( 10 );
                
            result = service.receiveMessage( request );
                    
            for (Message message : result.getMessages() )
            {
                IMessage output = null;
                
                switch ( getPayloadType(message) )
                {
                case STRING:
                    output = new SqsStringMessage(message);
                    break;
                    
                case MAP:
                    output = new SqsMapMessage(message);
                    break;
                  
                case OBJECT:
                    output = new SqsObjectMessage(message);
                    break;
                }
                
                if ( selector.evaluate( output ) )
                {
                    removeMessageFromQueue( service,queueUrl,message );
                    messages.add( output );
                }
                else
                    makeVisibleToOtherReceivers( service,queueUrl,message );
                
            }
            
            return messages;
        }
        finally
        {
            //service.shutdown();
        }
    }

    /************************************************************************
     *  
     *
     * @param message
     * @return
     */
    protected PayloadType
    getPayloadType(Message message)
    {
        if ( message.getMessageAttributes() == null )
            return PayloadType.STRING;
        
        if ( !message.getMessageAttributes().containsKey( SqsMessage.PAYLOAD_TYPE ) )
            return PayloadType.STRING;
            
        return
            PayloadType
                .valueOf( 
                    message
                        .getMessageAttributes()
                        .get( SqsMessage.PAYLOAD_TYPE )
                        .getStringValue() );
    }

    /************************************************************************
     *  
     *
     * @param service 
     * @param queueUrl 
     * @param message
     */
    private void 
    removeMessageFromQueue(
        AmazonSQS service,
        String    queueUrl,
        Message   message)
    {
        DeleteMessageRequest request = 
            new DeleteMessageRequest()
                .withQueueUrl( queueUrl )
                .withReceiptHandle( message.getReceiptHandle() );
        
        service.deleteMessage( request );
    }

    /************************************************************************
     *  
     *
     * @param service 
     * @param queueUrl 
     * @param message
     */
    private void 
    makeVisibleToOtherReceivers(
        AmazonSQS service,
        String    queueUrl,
        Message   message)
    {
        ChangeMessageVisibilityRequest request =
            new ChangeMessageVisibilityRequest()
                .withQueueUrl( queueUrl )
                .withReceiptHandle( message.getReceiptHandle() )
                .withVisibilityTimeout( 0 );
        
        service.changeMessageVisibility( request );
    }

}

// ##########################################################################
