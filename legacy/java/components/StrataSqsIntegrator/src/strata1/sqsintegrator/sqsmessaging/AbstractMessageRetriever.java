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

import java.util.ArrayList;
import java.util.List;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ChangeMessageVisibilityRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.ISelector;

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
    private final ISqsMessagingSession itsSession;
    private AmazonSQSClient            itsService;
    
    /************************************************************************
     * Creates a new AbstractMessageRetriever. 
     *
     */
    public 
    AbstractMessageRetriever(ISqsMessagingSession session)
    {
        itsSession = session;
        itsService = itsSession.getImp(); 
    }

    /************************************************************************
     *  
     *
     */
    public void
    close()
    {
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
        AmazonSQSClient       service  = itsService;
        ReceiveMessageRequest request  = null;
        ReceiveMessageResult  result   = null;
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
                    output = new SqsStringMessage(itsSession,message);
                    break;
                    
                case MAP:
                    output = new SqsMapMessage(itsSession,message);
                    break;
                  
                case OBJECT:
                    output = new SqsObjectMessage(itsSession,message);
                    break;
                }
                
                ((SqsMessage)output).setQueueUrl( queueUrl );
                
                if ( selector.evaluate( output ) )
                {
                    messages.add( output );

                    if ( itsSession.getAcknowledgementMode() == AcknowledgementMode.AUTO_ACKNOWLEDGEMENT )
                        output.acknowledge();                    
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
