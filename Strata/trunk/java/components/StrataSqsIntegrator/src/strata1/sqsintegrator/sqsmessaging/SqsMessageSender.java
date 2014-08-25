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
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

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
    private final AmazonSQS         itsImp;
    
    /************************************************************************
     * Creates a new SqsMessageSender. 
     *
     */
    public 
    SqsMessageSender(IMessagingSession session,String queueUrl,AmazonSQS imp)
    {
        itsSession  = session;
        itsQueueUrl = queueUrl;
        itsImp      = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setTimeToLive(long milliseconds)
    {
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
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    send(IMessage message)
    {
        Message            messageImp = null;
        SendMessageRequest request    = null;
        SendMessageResult  result     = null;
        
        messageImp = ((SqsMessage)message).getMessageImp();
        request =
            new SendMessageRequest()
                .withQueueUrl( itsQueueUrl )
                .withMessageAttributes( messageImp.getMessageAttributes() )
                .withMessageBody( messageImp.getBody() );

        result = itsImp.sendMessage( request );
    }

}

// ##########################################################################
