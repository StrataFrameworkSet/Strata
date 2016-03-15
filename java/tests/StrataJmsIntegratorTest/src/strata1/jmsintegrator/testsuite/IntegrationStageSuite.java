// ##########################################################################
// # File Name: IntegrationStageSuite.java
// #
// # Copyright: 2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:   This file is part of the StrataEntity Framework.
// #
// #            The StrataIntegrator Framework is free software: you 
// #            can redistribute it and/or modify it under the terms of 
// #            the GNU Lesser General Public License as published by
// #            the Free Software Foundation, either version 3 of the 
// #            License, or (at your option) any later version.
// #
// #            The StrataIntegrator Framework is distributed in the 
// #            hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #            without even the implied warranty of MERCHANTABILITY or 
// #            FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #            General Public License for more details.
// #
// #            You should have received a copy of the GNU Lesser 
// #            General Public License along with the StrataIntegrator
// #            Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.jmsintegrator.testsuite;

import strata1.jmsintegrator.jmsmessaging.JmsMessageReceiverTest;
import strata1.jmsintegrator.jmsmessaging.JmsMessageSenderTest;
import strata1.jmsintegrator.jmsmessaging.JmsMessagingSessionTest;
import strata1.jmsintegrator.jmsmessaging.SqsJmsMessageReceiverTest;
import strata1.jmsintegrator.jmsmessaging.SqsJmsMessageSenderTest;
import strata1.jmsintegrator.jmsmessaging.StompJmsMessageReceiverTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    JmsMessagingSessionTest.class,
    JmsMessageSenderTest.class,
    JmsMessageReceiverTest.class,
    SqsJmsMessageSenderTest.class,
    SqsJmsMessageReceiverTest.class,
    StompJmsMessageReceiverTest.class})
public 
class IntegrationStageSuite {}

// ##########################################################################
