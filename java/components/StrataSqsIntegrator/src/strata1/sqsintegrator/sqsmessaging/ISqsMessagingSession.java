// ##########################################################################
// # File Name:	ISqsMessagingSession.java
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

import com.amazonaws.services.sqs.AmazonSQSClient;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.ISelector;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public
interface ISqsMessagingSession
    extends IMessagingSession
{
    public ISqsMessagingSession
    insertQueue(String queueId,String queueUrl);
    
    public ISqsMessagingSession
    insertSelector(String expression,ISelector selector);
    
    public String
    getQueueUrl(String queueId);
    
    public ISelector
    getSelector(String expression);
    
    public boolean
    hasQueue(String queueId);
    
    public boolean
    hasSelector(String expression);
    
    public void
    acknowledge(SqsMessage message);
    
    public AmazonSQSClient 
    getImp();
}

// ##########################################################################
