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

package strata1.sqsintegrator.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import strata1.sqsintegrator.sqsmessaging.SqsMapMessageTest;
import strata1.sqsintegrator.sqsmessaging.SqsMessageReceiverTest;
import strata1.sqsintegrator.sqsmessaging.SqsMessageSenderTest;
import strata1.sqsintegrator.sqsmessaging.SqsMessagingSessionTest;
import strata1.sqsintegrator.sqsmessaging.SqsObjectMessageTest;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    SqsMessagingSessionTest.class,
    SqsMessageSenderTest.class,
    SqsMessageReceiverTest.class,
    SqsMapMessageTest.class,
    SqsObjectMessageTest.class})
public 
class IntegrationStageSuite {}

// ##########################################################################
