// ##########################################################################
// # File Name:	JmsMessageListener.java
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

import strata1.integrator.messaging.IMessageListener;
import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsMessageListener
    implements MessageListener
{
    private final IMessageListener itsListener;
    
    /************************************************************************
     * Creates a new JmsMessageListener. 
     *
     */
    public 
    JmsMessageListener(IMessageListener listener)
    {
        itsListener = listener;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onMessage(Message message)
    {
        if ( message instanceof TextMessage )
            itsListener.onMessage( 
                new JmsStringMessage((TextMessage)message) );
        else if ( message instanceof MapMessage )
            itsListener.onMessage( 
                new JmsMapMessage((MapMessage)message) );
        else if ( message instanceof ObjectMessage )
            itsListener.onMessage( 
                new JmsObjectMessage((ObjectMessage)message) );
        else if ( message instanceof BytesMessage )
            itsListener.onMessage( 
                new JmsBytesMessage((BytesMessage)message) );
    }

}

// ##########################################################################
