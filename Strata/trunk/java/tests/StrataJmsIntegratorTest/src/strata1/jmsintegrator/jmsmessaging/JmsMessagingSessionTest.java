// ##########################################################################
// # File Name:	JmsMessagingSessionTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegratorTest Framework.
// #
// #   			The StrataIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.jmsintegrator.jmsmessaging;

import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.MessagingSessionTest;
import org.apache.activemq.ActiveMQConnectionFactory;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsMessagingSessionTest
    extends MessagingSessionTest
{

    /************************************************************************
     * Creates a new {@code JmsMessagingSessionTest}. 
     *
     */
    public 
    JmsMessagingSessionTest() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IMessagingSession 
    createMessagingSesssion()
    {
        ActiveMQConnectionFactory factory = 
            new ActiveMQConnectionFactory("tcp://localhost:61616");
        
        return new JmsQueueMessagingSession(factory);
    }

}

// ##########################################################################
