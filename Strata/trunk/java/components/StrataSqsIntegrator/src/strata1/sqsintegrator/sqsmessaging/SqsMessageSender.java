// ##########################################################################
// # File Name:	SqsMessageSender.java
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
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsMessageSender
    implements IMessageSender
{
    private final IMessagingSession itsSession;
    private final String            itsQueueUrl;
    private final AWSCredentials    itsCredentials;
    
    /************************************************************************
     * Creates a new SqsMessageSender. 
     *
     */
    public 
    SqsMessageSender(
        IMessagingSession session,
        String            queueUrl,
        AWSCredentials    credentials)
    {
        itsSession     = session;
        itsQueueUrl    = queueUrl;
        itsCredentials = credentials;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setTimeToLive(long milliseconds)
    {
        AmazonSQSClient           service = null;
        Long                      seconds = null;
        SetQueueAttributesRequest request = null;
        
        service = new AmazonSQSClient(itsCredentials);
        seconds = new Long(milliseconds/1000);
        
        try
        {
            request = 
                new SetQueueAttributesRequest()
                    .withQueueUrl( itsQueueUrl )
                    .addAttributesEntry( 
                        "MessageRetentionPeriod",
                        seconds < 60 ? "60" : seconds.toString() );
            
            service.setQueueAttributes( request );
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
    public IMessagingSession 
    getSession()
    {
        return itsSession;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    getTimeToLive()
    {
        AmazonSQSClient           service = null;
        GetQueueAttributesRequest request = null;
        GetQueueAttributesResult  result  = null;
        Long                      seconds = null;
        
        service = new AmazonSQSClient(itsCredentials);
        
        try
        {
            request = 
                new GetQueueAttributesRequest()
                    .withQueueUrl( itsQueueUrl )
                    .withAttributeNames( "MessageRetentionPeriod" );     
            result = service.getQueueAttributes( request );
            seconds = 
                new Long(
                    result
                        .getAttributes()
                        .get( "MessageRetentionPeriod" ) );
            
            return seconds*1000;
        }
        finally
        {
            service.shutdown();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unused")
    @Override
    public void 
    send(IMessage message)
    {
        AmazonSQSClient    service    = null;
        Message            messageImp = null;
        SendMessageRequest request    = null;
        SendMessageResult  result     = null;
        
        service = new AmazonSQSClient(itsCredentials);
        
        try
        {
            messageImp = ((SqsMessage)message).getMessageImp();
            request =
                new SendMessageRequest()
                    .withQueueUrl( itsQueueUrl )
                    .withMessageAttributes( messageImp.getMessageAttributes() )
                    .withMessageBody( messageImp.getBody() );
            result = service.sendMessage( request );
        }
        finally
        {
            service.shutdown();
        }
    }

}

// ##########################################################################
