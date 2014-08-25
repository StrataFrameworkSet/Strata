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

import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.IObjectMessage;
import strata1.integrator.messaging.IStringMessage;
import com.amazonaws.services.sqs.AmazonSQS;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsQueueMessagingSession
    implements IMessagingSession
{
    private final AmazonSQS itsImp;
    
    /************************************************************************
     * Creates a new SqsQueueMessagingSession. 
     *
     */
    public 
    SqsQueueMessagingSession(AmazonSQS imp)
    {
        itsImp = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageSender 
    createMessageSender(String id)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id,String selector)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    createStringMessage()
    {
        return new SqsStringMessage();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    createMapMessage()
    {
        return new SqsMapMessage();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    createObjectMessage()
    {
        return new SqsObjectMessage();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startReceiving()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopReceiving()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close()
    {
        itsImp.shutdown();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isReceiving()
    {
        return false;
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

}

// ##########################################################################
