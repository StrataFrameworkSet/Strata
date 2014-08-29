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
import com.amazonaws.services.sqs.AmazonSQS;
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
    private final AmazonSQS itsService;
    
    /************************************************************************
     * Creates a new AbstractMessageRetriever. 
     *
     */
    public 
    AbstractMessageRetriever(AmazonSQS service)
    {
        itsService = service;
    }

    /************************************************************************
     *  
     *
     * @param queueUrl
     * @return
     */
    protected IMessage
    getMessageFromQueue(String queueUrl)
    {
        ReceiveMessageRequest request = null;
        ReceiveMessageResult  result  = null;
        Message               message  = null;
        
        request = 
            new ReceiveMessageRequest()
                .withQueueUrl( queueUrl )
                .withMaxNumberOfMessages( 1 );
            
        result = itsService.receiveMessage( request );
        
        if ( result.getMessages().isEmpty() )
            return null;
        
        message = result.getMessages().get( 0 );
                   
        switch ( getPayloadType(message) )
        {
        case STRING:
            return new SqsStringMessage(message);
            
        case MAP:
            return new SqsMapMessage(message);
          
        case OBJECT:
            return new SqsObjectMessage(message);
        }
        
        
        return null;
    }
    
    /************************************************************************
     *  
     *
     * @param queueUrl
     * @return
     */
    protected List<IMessage>
    getMessagesFromQueue(String queueUrl)
    {
        ReceiveMessageRequest request = null;
        ReceiveMessageResult  result  = null;
        List<IMessage>        output = new ArrayList<IMessage>();
        
        request = 
            new ReceiveMessageRequest()
                .withQueueUrl( queueUrl )
                .withMaxNumberOfMessages( 10 );
            
        result = itsService.receiveMessage( request );
                
        for (Message message : result.getMessages() )
        {
            switch ( getPayloadType(message) )
            {
            case STRING:
                output.add(new SqsStringMessage(message));
                break;
                
            case MAP:
                output.add(new SqsMapMessage(message));
                break;
              
            case OBJECT:
                output.add(new SqsObjectMessage(message));
                break;
            }
        }
        
        return output;
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
        return
            PayloadType
                .valueOf( 
                    message
                        .getMessageAttributes()
                        .get( SqsMessage.PAYLOAD_TYPE )
                        .getStringValue() );
    }

}

// ##########################################################################
