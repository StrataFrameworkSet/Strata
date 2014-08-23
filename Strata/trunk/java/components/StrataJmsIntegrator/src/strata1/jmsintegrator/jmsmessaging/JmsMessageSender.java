// ##########################################################################
// # File Name:	JmsMessageSender.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataJmsIntegrator Framework.
// #
// #   			The StrataJmsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataJmsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataJmsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.jmsintegrator.jmsmessaging;

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;
import javax.jms.JMSException;
import javax.jms.MessageProducer;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsMessageSender
    implements IMessageSender
{
    private final IMessagingSession itsSession;
    private final MessageProducer   itsImp;
    
    /************************************************************************
     * Creates a new JmsMessageSender. 
     *
     */
    public 
    JmsMessageSender(IMessagingSession session,MessageProducer imp)
    {
        itsSession = session;
        itsImp     = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setTimeToLive(long milliseconds)
    {
        try
        {
            itsImp.setTimeToLive( milliseconds );
        }
        catch (JMSException e)
        {
            throw new IllegalStateException(e);
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
        try
        {
            return itsImp.getTimeToLive();
        }
        catch (JMSException e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    send(IMessage message)
    {
        JmsMessage m = (JmsMessage)message;
        
        try
        {
            itsImp.send( m.getMessageImp() );
        }
        catch (JMSException e)
        {
            throw new IllegalStateException(e);
        }
    }

}

// ##########################################################################
