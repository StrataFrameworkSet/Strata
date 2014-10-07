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

package strata1.common.testsuite;

import strata1.common.datetime.DateTimeTest;
import strata1.common.money.CurrencyExchangerTest;
import strata1.common.money.MoneyCalculatorTest;
import strata1.common.money.MoneyTest;
import strata1.common.producerconsumer.BlockingQueueBroadcasterTest;
import strata1.common.producerconsumer.BlockingQueueRouterTest;
import strata1.common.producerconsumer.DisruptorBroadcasterTest;
import strata1.common.producerconsumer.DisruptorRouterTest;
import strata1.common.task.TaskBlockingQueueRouterTest;
import strata1.common.task.TaskDisruptorRouterTest;
import strata1.common.utility.CopyableTest;
import strata1.common.utility.SingletonProxyTest;
import strata1.common.utility.SynchronizerTest;
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
	DateTimeTest.class,
	MoneyTest.class,
	CurrencyExchangerTest.class,
	MoneyCalculatorTest.class,
	CopyableTest.class,
	SingletonProxyTest.class,
	SynchronizerTest.class,
	DisruptorRouterTest.class,
	DisruptorBroadcasterTest.class,
	BlockingQueueRouterTest.class,
	BlockingQueueBroadcasterTest.class,
	TaskDisruptorRouterTest.class,
	TaskBlockingQueueRouterTest.class})
public 
class CommitStageSuite {}

// ##########################################################################
