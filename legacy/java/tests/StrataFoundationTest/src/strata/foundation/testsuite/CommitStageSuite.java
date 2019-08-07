// ##########################################################################
// # File Name: CommitStageSuite.java
// #
// # Copyright: 2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:   This file is part of the StrataCommon Framework.
// #
// #            The StrataCommon Framework is free software: you 
// #            can redistribute it and/or modify it under the terms of 
// #            the GNU Lesser General Public License as published by
// #            the Free Software Foundation, either version 3 of the 
// #            License, or (at your option) any later version.
// #
// #            The StrataCommon Framework is distributed in the 
// #            hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #            without even the implied warranty of MERCHANTABILITY or 
// #            FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #            General Public License for more details.
// #
// #            You should have received a copy of the GNU Lesser 
// #            General Public License along with the StrataCommon
// #            Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import strata.foundation.guiceinjection.GuiceBootstrapModuleTest;
import strata.foundation.guiceinjection.GuiceContainerTest;
import strata.foundation.producerconsumer.BlockingQueueBroadcasterTest;
import strata.foundation.producerconsumer.BlockingQueueRouterTest;
import strata.foundation.producerconsumer.DisruptorBroadcasterTest;
import strata.foundation.producerconsumer.DisruptorRouterTest;
import strata.foundation.standardinjection.StandardBootstrapModuleTest;
import strata.foundation.standardinjection.StandardContainerTest;
import strata.foundation.task.TaskBlockingQueueRouterTest;
import strata.foundation.task.TaskDisruptorRouterTest;
import strata.foundation.utility.CopyableTest;
import strata.foundation.utility.SingletonProxyTest;
import strata.foundation.utility.SynchronizerTest;
import strata.foundation.value.CurrencyExchangerTest;
import strata.foundation.value.DateTimeTest;
import strata.foundation.value.MoneyCalculatorTest;
import strata.foundation.value.MoneyTest;
import strata.foundation.value.TimePointTest;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TimePointTest.class,
	DateTimeTest.class,
	MoneyTest.class,
	CurrencyExchangerTest.class,
	MoneyCalculatorTest.class,
	CopyableTest.class,
	SingletonProxyTest.class,
	SynchronizerTest.class,
	StandardBootstrapModuleTest.class,
	StandardContainerTest.class,
	GuiceBootstrapModuleTest.class,
	GuiceContainerTest.class,
	DisruptorRouterTest.class,
	DisruptorBroadcasterTest.class,
	BlockingQueueRouterTest.class,
	BlockingQueueBroadcasterTest.class,
	TaskDisruptorRouterTest.class,
	TaskBlockingQueueRouterTest.class})
public 
class CommitStageSuite {}

// ##########################################################################
