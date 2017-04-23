// ##########################################################################
// # File Name:	JmsMessageSenderTest.java
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

import org.skyscreamer.nevado.jms.NevadoConnectionFactory;
import org.skyscreamer.nevado.jms.connector.amazonaws.AmazonAwsSQSConnectorFactory;
//import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.MessageSenderTest;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsJmsMessageSenderTest
    extends MessageSenderTest
{

    /************************************************************************
     * Creates a new {@code JmsMessageSenderTest}. 
     *
     */
    public 
    SqsJmsMessageSenderTest() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IMessagingSession 
    createMessagingSession()
    {
        /*
        SQSConnectionFactory factory = null;
         
    
        factory = 
            SQSConnectionFactory.builder()
                .withRegion(Region.getRegion(Regions.US_WEST_2))
                .withAWSCredentialsProvider(new DefaultAWSCredentialsProviderChain())
                .build();
                */
        
        AWSCredentials credentials = new DefaultAWSCredentialsProviderChain().getCredentials();
        NevadoConnectionFactory factory = new NevadoConnectionFactory();
        
        factory.setAwsAccessKey( credentials.getAWSAccessKeyId() );
        factory.setAwsSecretKey( credentials.getAWSSecretKey() );
        factory.setSqsConnectorFactory( new AmazonAwsSQSConnectorFactory() );
        return new JmsQueueMessagingSession(factory);
    }

}

// ##########################################################################
