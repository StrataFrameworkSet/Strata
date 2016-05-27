// ##########################################################################
// # File Name:	JmsMessageReceiverTest.java
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
import strata1.integrator.messaging.MessageReceiverTest;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsMessageReceiverTest
    extends MessageReceiverTest
{
    
    /************************************************************************
     * Creates a new {@code JmsMessageReceiverTest}. 
     *
     */
    public 
    JmsMessageReceiverTest() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IMessagingSession 
    createMessagingSession()
    {
        String host =
            "ssl://localhost:61617";
            //"ssl://ec2-54-68-247-101.us-west-2.compute.amazonaws.com:61617";
        
        ActiveMQSslConnectionFactory factory = null;
        TrustManager[]               manager = null;
        
        factory = new ActiveMQSslConnectionFactory( host );

        manager = 
            new TrustManager[] 
            { 
                new X509TrustManager()
                {
                    public X509Certificate[] 
                    getAcceptedIssuers()
                    {
                        return null;
                    }
        
                    public void 
                    checkClientTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
        
                    public void 
                    checkServerTrusted(
                        X509Certificate[] certificates,
                        String authType) {}
                } 
            };
        
        
        factory.setKeyAndTrustManagers(
            null, 
            manager, 
            new SecureRandom()); 
        
        factory.setUserName( "strata-activemq-user" );
        factory.setPassword( "Dbr6pzyX" );
        
        return new JmsQueueMessagingSession(factory);
    }

}

// ##########################################################################
